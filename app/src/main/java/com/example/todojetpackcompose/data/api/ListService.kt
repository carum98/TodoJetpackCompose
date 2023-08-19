package com.example.todojetpackcompose.data.api

import com.example.todojetpackcompose.data.api.dto.ListDataDto
import retrofit2.http.GET

interface ListService {
    @GET("lists")
    suspend fun getLists(): ListDataDto
}