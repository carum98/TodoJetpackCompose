package com.example.todojetpackcompose.data.repository

import com.example.todojetpackcompose.data.api.AuthService
import com.example.todojetpackcompose.data.api.dto.AuthDtoRequest
import com.example.todojetpackcompose.data.api.dto.toAuth
import com.example.todojetpackcompose.domain.model.Auth
import com.example.todojetpackcompose.domain.repository.AuthRepository

class AuthRepositoryImpl(
    private val authService: AuthService
) : AuthRepository {
    override suspend fun login(username: String, password: String): Auth {
        val authDto = authService.login(AuthDtoRequest(username, password))
        return authDto.toAuth()
    }

    override suspend fun register(name: String, username: String, password: String): Auth {
        TODO("Not yet implemented")
    }
}