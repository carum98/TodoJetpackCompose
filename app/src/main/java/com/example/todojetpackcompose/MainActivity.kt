package com.example.todojetpackcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.todojetpackcompose.common.ApiClient
import com.example.todojetpackcompose.data.api.AuthService
import com.example.todojetpackcompose.data.api.ListService
import com.example.todojetpackcompose.data.datastore.AuthenticationService
import com.example.todojetpackcompose.data.repository.AuthRepositoryImpl
import com.example.todojetpackcompose.data.repository.ListRepositoryImpl
import com.example.todojetpackcompose.domain.use_case.GetListsUseCase
import com.example.todojetpackcompose.domain.use_case.LoginUseCase
import com.example.todojetpackcompose.navigation.AppNavigation
import com.example.todojetpackcompose.presentation.lists.ListsViewModel
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
                    println("Build Surface")
                    val context = LocalContext.current
                    val authenticationService = AuthenticationService(context)

                    ApiClient.create(authenticationService)

                    val loginViewModel = LoginViewModel(
                        authenticationService = authenticationService,
                        loginUseCase = LoginUseCase(
                            repository = AuthRepositoryImpl(
                                authService = AuthService.create()!!
                            )
                        )
                    )

                    val listsViewModel = ListsViewModel(
                        getListUseCase = GetListsUseCase(
                            listRepository = ListRepositoryImpl(
                                listService = ListService.create()!!
                            )
                        )
                    )

                    AppNavigation(
                        loginViewModel = loginViewModel,
                        listsViewModel = listsViewModel
                    )
                }
            }
        }
    }
}
