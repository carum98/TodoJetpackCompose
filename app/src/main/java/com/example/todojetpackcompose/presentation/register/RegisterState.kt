package com.example.todojetpackcompose.presentation.register

data class RegisterState(
    val name: String = "",
    val username: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val disableLoginButton: Boolean = true
)

sealed class RegisterEvent {
    data class EnteredName(val value: String) : RegisterEvent()
    data class EnteredUsername(val value: String) : RegisterEvent()
    data class EnteredPassword(val value: String) : RegisterEvent()
    object Register : RegisterEvent()
}
