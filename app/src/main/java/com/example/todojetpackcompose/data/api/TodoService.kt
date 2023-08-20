package com.example.todojetpackcompose.data.api

import com.example.todojetpackcompose.data.api.dto.TodoDataDto
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface TodoService {
    @GET("lists/{listId}/todos")
    suspend fun getTodos(@Path("listId") listId: Int): TodoDataDto

    @POST("todos/{todoId}/toggle")
    suspend fun toggleTodoComplete(@Path("todoId") todoId: Int)
}