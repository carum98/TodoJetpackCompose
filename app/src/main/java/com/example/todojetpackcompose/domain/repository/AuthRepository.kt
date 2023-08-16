package com.example.todojetpackcompose.domain.repository

import com.example.todojetpackcompose.domain.model.Auth

interface AuthRepository {
    suspend fun login(username: String, password: String): Auth
    suspend fun register(name: String, username: String, password: String): Auth
}