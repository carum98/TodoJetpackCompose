package com.example.todojetpackcompose.domain.repository

import com.example.todojetpackcompose.domain.model.Todo

interface TodoRepository {
    suspend fun getTodos(listId: Int): List<Todo>
    suspend fun toggleTodoComplete(todoId: Int)
    suspend fun createTodo(title: String, listId: Int): Todo
    suspend fun updateTodo(todoId: Int, title: String): Todo
    suspend fun deleteTodo(todoId: Int)
    suspend fun moveTodo(todoId: Int, toIndex: Int): List<Todo>
}