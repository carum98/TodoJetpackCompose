package com.example.todojetpackcompose.data.api.dto

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.example.todojetpackcompose.domain.model.List as ListModel

data class ListDtoRequest(
    val name: String,
    private val colorInstance: Color,
)  {
    val color: String = String.format("#%06X", 0xFFFFFF and colorInstance.toArgb())
}

data class ListDto(
    val id: Int,
    val name: String,
    val color: String,
    val count: CountDto,
)

data class ListDataDto(
    val data: List<ListDto>
)

data class CountDto(
    val completed: Int,
    val pending: Int,
    val total: Int,
)

fun ListDto.toList(): ListModel {
    return ListModel(
        id = id,
        name = name,
        colorHexadecimal = color,
        count = count.pending,
    )
}
