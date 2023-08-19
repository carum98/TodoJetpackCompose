package com.example.todojetpackcompose.data.api.dto

import com.example.todojetpackcompose.domain.model.List as ListModel

data class ListDto(
    val id: Int,
    val name: String,
    val color: String,
)

data class ListDataDto(
    val data: List<ListDto>
)

fun ListDto.toList(): ListModel {
    return ListModel(
        id = id,
        name = name,
        color = color,
    )
}
