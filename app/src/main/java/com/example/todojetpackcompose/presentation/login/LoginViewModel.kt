package com.example.todojetpackcompose.presentation.login

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todojetpackcompose.common.Resource
import com.example.todojetpackcompose.data.datastore.AuthenticationService
import com.example.todojetpackcompose.domain.use_case.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val authenticationService: AuthenticationService
) : ViewModel() {
    private val _state = mutableStateOf(LoginState())
    val state get() = _state.value

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.EnteredUsername -> {
                _state.value = state.copy(username = event.value)
                _state.value = state.copy(disableLoginButton = disableLoginButton())
            }
            is LoginEvent.EnteredPassword -> {
                _state.value = state.copy(password = event.value)
                _state.value = state.copy(disableLoginButton = disableLoginButton())
            }
            is LoginEvent.Login -> {
                login()
            }
        }
    }

    private fun login() {
        loginUseCase(state.username, state.password).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = state.copy(isLoading = false)
                    Log.d("LoginViewModel", "Successfully logged in: ${result.data}")

                    result.data?.let {
                        authenticationService.store(it.token)
                    }
                }
                is Resource.Error -> {
                    _state.value = state.copy(isLoading = false)
                    Log.d("LoginViewModel", "Failed to log in: ${result.message}")
                }
                is Resource.Loading -> {
                    _state.value = state.copy(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun disableLoginButton() : Boolean {
        return state.username.isBlank() || state.password.isBlank()
    }
}