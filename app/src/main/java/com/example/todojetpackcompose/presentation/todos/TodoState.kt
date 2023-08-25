package com.example.todojetpackcompose.presentation.todos

import com.example.todojetpackcompose.domain.model.Todo

data class TodoState(
    val isLoading: Boolean = false,
    val error: String = "",
    val todos: List<Todo> = emptyList(),
    val showDialog: Boolean = false,
    val showAlertDialog: Boolean = false,
    val todoSelected: Todo? = null
)

sealed class TodoEvent {
    object GetTodos: TodoEvent()
    data class ToggleTodo(val todo: Todo): TodoEvent()
    data class DeleteTodo(val todo: Todo): TodoEvent()
    data class UpdateTodo(val id: Int, val title: String): TodoEvent()
    data class CreateTodo(val title: String): TodoEvent()
    object OpenDialogCreateTodo: TodoEvent()
    data class OpenDialogUpdateTodo(val todo: Todo): TodoEvent()
    data class OpenDialogDeleteTodo(val todo: Todo): TodoEvent()
    object CloseDialogs: TodoEvent()
    data class MoveTodo(val fromIndex: Int, val toIndex: Int): TodoEvent()
}