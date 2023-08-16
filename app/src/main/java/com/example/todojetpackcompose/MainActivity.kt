package com.example.todojetpackcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.todojetpackcompose.data.api.AuthService
import com.example.todojetpackcompose.data.repository.AuthRepositoryImpl
import com.example.todojetpackcompose.domain.use_case.LoginUseCase
import com.example.todojetpackcompose.presentation.login.LoginView
import com.example.todojetpackcompose.presentation.login.LoginViewModel
import com.example.todojetpackcompose.presentation.theme.TodoJetpackComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TodoJetpackComposeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val loginViewModel = LoginViewModel(
                        loginUseCase = LoginUseCase(
                            repository = AuthRepositoryImpl(
                                authService = AuthService.create()!!
                            )
                        )
                    )

                    LoginView(loginViewModel)
                }
            }
        }
    }
}
