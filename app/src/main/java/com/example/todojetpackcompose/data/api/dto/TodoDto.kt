package com.example.todojetpackcompose.data.api.dto

import com.example.todojetpackcompose.domain.model.Todo
import com.google.gson.annotations.SerializedName

data class TodoDtoRequest(
    val title: String,
    @SerializedName("list_id")
    val listId: Int?,
)

data class TodoDto(
    val id: Int,
    val title: String,
    @SerializedName("is_complete")
    val isComplete: Boolean,
)

data class TodoDataDto(
    val data: List<TodoDto>
)

fun TodoDto.toTodo(): Todo {
    return Todo(
        id = id,
        title = title,
        isComplete = isComplete,
    )
}
