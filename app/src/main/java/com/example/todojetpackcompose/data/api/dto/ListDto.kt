package com.example.todojetpackcompose.data.api.dto

import com.example.todojetpackcompose.domain.model.List

data class ListDto(
    val id: Int,
    val name: String,
    val color: String,
)

fun ListDto.toList(): List {
    return List(
        id = id,
        name = name,
        color = color,
    )
}
