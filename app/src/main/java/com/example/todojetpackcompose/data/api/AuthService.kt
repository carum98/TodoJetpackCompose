package com.example.todojetpackcompose.data.api

import com.example.todojetpackcompose.data.api.dto.AuthDto
import com.example.todojetpackcompose.data.api.dto.AuthDtoRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("login")
    suspend fun login(@Body loginRequest: AuthDtoRequest): AuthDto
}