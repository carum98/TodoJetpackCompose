package com.example.todojetpackcompose.domain.repository

import com.example.todojetpackcompose.domain.model.Todo

interface TodoRepository {
    suspend fun getTodos(listId: Int): List<Todo>
}