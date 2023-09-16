package com.example.todojetpackcompose.navigation

sealed class AppScreen(val route: String) {
    object Login : AppScreen("login")
    object Lists : AppScreen("lists")
    object Todo : AppScreen("todo")
    object Register : AppScreen("register")
}