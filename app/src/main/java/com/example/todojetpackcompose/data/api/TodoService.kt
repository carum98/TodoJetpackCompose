package com.example.todojetpackcompose.data.api

import com.example.todojetpackcompose.data.api.dto.TodoDataDto
import com.example.todojetpackcompose.data.api.dto.TodoDto
import com.example.todojetpackcompose.data.api.dto.TodoDtoRequest
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface TodoService {
    @GET("lists/{listId}/todos")
    suspend fun getTodos(@Path("listId") listId: Int): TodoDataDto

    @POST("/todos")
    suspend fun createTodo(@Body todoDtoRequest: TodoDtoRequest): TodoDto

    @PUT("todos/{todoId}")
    suspend fun updateTodoTitle(@Path("todoId") todoId: Int, @Body todoDtoRequest: TodoDtoRequest): TodoDto

    @DELETE("todos/{todoId}")
    suspend fun deleteTodos(@Path("todoId") todoId: Int)

    @POST("todos/{todoId}/toggle")
    suspend fun toggleTodoComplete(@Path("todoId") todoId: Int)

    @POST("todos/{todoId}/move")
    suspend fun moveTodo(
        @Path("todoId") todoId: Int,
        @Query("position") toIndex: Int
    ): TodoDataDto
}