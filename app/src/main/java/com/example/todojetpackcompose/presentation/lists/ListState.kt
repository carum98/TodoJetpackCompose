package com.example.todojetpackcompose.presentation.lists

import androidx.compose.ui.graphics.Color
import com.example.todojetpackcompose.domain.model.List as ListModel

data class ListState(
    val isLoading: Boolean = false,
    val lists: List<ListModel> = emptyList(),
    val error: String = ""
)

sealed class ListEvent {
    object GetLists: ListEvent()
    data class DeleteList(val id: Int): ListEvent()
    data class UpdateList(val id: Int, val name: String, val color: Color): ListEvent()
    data class CreateList(val name: String, val color: Color): ListEvent()
}
