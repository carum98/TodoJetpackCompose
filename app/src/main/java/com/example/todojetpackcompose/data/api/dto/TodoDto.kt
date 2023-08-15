package com.example.todojetpackcompose.data.api.dto

import com.example.todojetpackcompose.domain.model.Todo

data class TodoDto(
    val id: Int,
    val title: String,
    val isComplete: Boolean,
)

fun TodoDto.toTodo(): Todo {
    return Todo(
        id = id,
        title = title,
        isComplete = isComplete,
    )
}
