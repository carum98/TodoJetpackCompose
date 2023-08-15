package com.example.todojetpackcompose.data.repository

import com.example.todojetpackcompose.domain.model.Todo
import com.example.todojetpackcompose.domain.repository.TodoRepository

class TodoRepositoryImpl: TodoRepository {
    override suspend fun getTodos(listId: Int): List<Todo> {
        TODO("Not yet implemented")
    }
}