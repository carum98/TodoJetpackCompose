package com.example.todojetpackcompose.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.todojetpackcompose.data.api.AuthService
import com.example.todojetpackcompose.data.datastore.AuthenticationService
import com.example.todojetpackcompose.data.datastore.AuthenticationService.Companion.datastore
import com.example.todojetpackcompose.data.repository.AuthRepositoryImpl
import com.example.todojetpackcompose.domain.use_case.LoginUseCase
import com.example.todojetpackcompose.presentation.lists.ListsView
import com.example.todojetpackcompose.presentation.lists.ListsViewModel
import com.example.todojetpackcompose.presentation.login.LoginView
import com.example.todojetpackcompose.presentation.login.LoginViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val authenticationService = AuthenticationService(context)

    LaunchedEffect(null) {
        context.datastore.data
            .map { it.contains(AuthenticationService.KEY_TOKEN) }
            .onEach { isAuthenticated ->
                when(isAuthenticated) {
                    true -> navController.navigate(AppScreen.Lists.route)
                    false -> navController.navigate(AppScreen.Login.route)
                }
            }.launchIn(scope)
    }

    val loginViewModel = LoginViewModel(
        authenticationService = authenticationService,
        loginUseCase = LoginUseCase(
            repository = AuthRepositoryImpl(
                authService = AuthService.create()!!
            )
        )
    )

    val listsViewModel = ListsViewModel()

    NavHost(navController = navController, startDestination = AppScreen.Login.route) {
        composable(AppScreen.Login.route) {
            LoginView(
                loginViewModel = loginViewModel
            )
        }

        composable(AppScreen.Lists.route) {
            ListsView(
                listsViewModel = listsViewModel,
                authenticationService = authenticationService
            )
        }
    }
}