package com.example.todojetpackcompose.data.api

import com.example.todojetpackcompose.data.api.dto.ListDataDto
import com.example.todojetpackcompose.data.api.dto.ListDto
import com.example.todojetpackcompose.data.api.dto.ListDtoRequest
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ListService {
    @GET("lists")
    suspend fun getLists(): ListDataDto

    @PUT("lists/{listId}")
    suspend fun updateList(@Path("listId") todoId: Int, @Body listDtoRequest: ListDtoRequest): ListDto

    @POST("lists")
    suspend fun createList(@Body listDtoRequest: ListDtoRequest): ListDto

    @DELETE("lists/{listId}")
    suspend fun deleteList(@Path("listId") todoId: Int)
}