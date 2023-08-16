package com.example.todojetpackcompose.domain.model

data class Auth(
    val token: String,
    val refreshToken: String,
    val expiredAt: Long
)