package com.example.todojetpackcompose.data.api

import com.example.todojetpackcompose.data.api.dto.AuthDto
import com.example.todojetpackcompose.data.api.dto.AuthDtoRequest
import com.example.todojetpackcompose.data.api.dto.AuthDtoRequestRegister
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("login")
    suspend fun login(@Body loginRequest: AuthDtoRequest): AuthDto

    @POST("register")
    suspend fun register(@Body registerRequest: AuthDtoRequestRegister): AuthDto
}