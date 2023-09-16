package com.example.todojetpackcompose.data.repository

import com.example.todojetpackcompose.data.api.AuthService
import com.example.todojetpackcompose.data.api.dto.AuthDtoRequest
import com.example.todojetpackcompose.data.api.dto.AuthDtoRequestRegister
import com.example.todojetpackcompose.data.api.dto.toAuth
import com.example.todojetpackcompose.domain.model.Auth
import com.example.todojetpackcompose.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authService: AuthService
) : AuthRepository {
    override suspend fun login(username: String, password: String): Auth {
        val authDto = authService.login(AuthDtoRequest(username, password))
        return authDto.toAuth()
    }

    override suspend fun register(name: String, username: String, password: String): Auth {
        val authDto = authService.register(AuthDtoRequestRegister(name, username, password))
        return authDto.toAuth()
    }
}