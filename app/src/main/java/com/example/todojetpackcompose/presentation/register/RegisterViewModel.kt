package com.example.todojetpackcompose.presentation.register

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todojetpackcompose.common.Resource
import com.example.todojetpackcompose.data.datastore.AuthenticationService
import com.example.todojetpackcompose.domain.use_case.RegisterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase,
    private val authenticationService: AuthenticationService
) : ViewModel() {
    private val _state = mutableStateOf(RegisterState())
    val state get() = _state.value

    fun onEvent(event: RegisterEvent) {
        when (event) {
            is RegisterEvent.EnteredName -> {
                _state.value = state.copy(name = event.value)
                _state.value = state.copy(disableLoginButton = disableRegisterButton())
            }
            is RegisterEvent.EnteredUsername -> {
                _state.value = state.copy(username = event.value)
                _state.value = state.copy(disableLoginButton = disableRegisterButton())
            }
            is RegisterEvent.EnteredPassword -> {
                _state.value = state.copy(password = event.value)
                _state.value = state.copy(disableLoginButton = disableRegisterButton())
            }
            is RegisterEvent.Register -> {
                register()
            }
        }
    }

    private fun register() {
        registerUseCase(state.name, state.username, state.password).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = state.copy(isLoading = false)
                    Log.d("RegisterViewModel", "Successfully logged in: ${result.data}")

                    result.data?.let {
                        authenticationService.store(it.token)
                    }
                }
                is Resource.Error -> {
                    _state.value = state.copy(isLoading = false)
                    Log.d("RegisterViewModel", "Failed to log in: ${result.message}")
                }
                is Resource.Loading -> {
                    _state.value = state.copy(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun disableRegisterButton() : Boolean {
        return state.name.isBlank() || state.username.isBlank() || state.password.isBlank()
    }
}