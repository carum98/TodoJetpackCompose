package com.example.todojetpackcompose.presentation.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.todojetpackcompose.common.TextFormField

@Composable
fun RegisterView(
    loginViewModel: RegisterViewModel = hiltViewModel(),
    onLogin: () -> Unit
) {
    val state = loginViewModel.state

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Register",
            style = TextStyle(
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold
            )
        )

        Spacer(modifier = Modifier.height(20.dp))

        TextFormField(
            value = state.name,
            onValueChange = { loginViewModel.onEvent(RegisterEvent.EnteredName(it)) },
            label = "Name"
        )

        Spacer(modifier = Modifier.height(20.dp))

        TextFormField(
            value = state.username,
            onValueChange = { loginViewModel.onEvent(RegisterEvent.EnteredUsername(it)) },
            label = "Username"
        )

        Spacer(modifier = Modifier.height(20.dp))

        TextFormField(
            value = state.password,
            onValueChange = { loginViewModel.onEvent(RegisterEvent.EnteredPassword(it)) },
            label = "Password",
            isPassword = true
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = { loginViewModel.onEvent(RegisterEvent.Register) },
            enabled = !state.disableLoginButton,
            modifier = Modifier.fillMaxWidth(0.6f)
        ) {
            Text("Register")
        }

        TextButton(onClick = onLogin) {
            Text(text = "Login")
        }
    }
}