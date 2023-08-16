package com.example.todojetpackcompose.data.api.dto

import com.example.todojetpackcompose.domain.model.Auth

data class AuthDtoRequest(
    val user_name: String,
    val password: String
)

data class AuthDto(
    val token: String,
    val refreshToken: String,
    val expiredAt: Long
)

fun AuthDto.toAuth(): Auth {
    return Auth(
        token = token,
        refreshToken = refreshToken,
        expiredAt = expiredAt,
    )
}