package com.example.todojetpackcompose.data.api

import com.example.todojetpackcompose.data.api.dto.TodoDto
import retrofit2.http.GET
import retrofit2.http.Path

interface TodoService {
    @GET("lists/{listId}/todos")
    suspend fun getTodos(@Path("listId") listId: Int): List<TodoDto>
}