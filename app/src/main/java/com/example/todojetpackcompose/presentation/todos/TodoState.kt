package com.example.todojetpackcompose.presentation.todos

import com.example.todojetpackcompose.domain.model.Todo

data class TodoState(
    val isLoading: Boolean = false,
    val error: String = "",
    val todos: List<Todo> = emptyList()
)

sealed class TodoEvent {
    object GetTodos: TodoEvent()
    data class ToggleTodo(val todo: Todo): TodoEvent()
}