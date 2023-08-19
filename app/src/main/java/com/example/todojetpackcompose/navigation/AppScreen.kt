package com.example.todojetpackcompose.navigation

sealed class AppScreen(val route: String) {
    object Login : AppScreen("login")
    object Lists : AppScreen("lists")
}