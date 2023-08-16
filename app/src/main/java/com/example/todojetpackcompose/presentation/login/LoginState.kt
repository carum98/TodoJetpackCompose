package com.example.todojetpackcompose.presentation.login

data class LoginState(
    val username: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val disableLoginButton: Boolean = true
)

sealed class LoginEvent {
    data class EnteredUsername(val value: String) : LoginEvent()
    data class EnteredPassword(val value: String) : LoginEvent()
    object Login : LoginEvent()
}
