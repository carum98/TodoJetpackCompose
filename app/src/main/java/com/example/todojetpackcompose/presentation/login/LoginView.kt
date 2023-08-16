package com.example.todojetpackcompose.presentation.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginView(
    loginViewModel: LoginViewModel
) {
    val state = loginViewModel.state

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = state.username,
            onValueChange = { loginViewModel.onEvent(LoginEvent.EnteredUsername(it)) },
            label = { Text("Username") },
            singleLine = true
        )
        OutlinedTextField(
            value = state.password,
            onValueChange = { loginViewModel.onEvent(LoginEvent.EnteredPassword(it)) },
            label = { Text("Password") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            visualTransformation = PasswordVisualTransformation()
        )
        Button(
            onClick = { loginViewModel.onEvent(LoginEvent.Login) },
            enabled = !state.disableLoginButton,
        ) {
            Text("Login")
        }
    }
}