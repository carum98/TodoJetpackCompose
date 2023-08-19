package com.example.todojetpackcompose.presentation.lists

import com.example.todojetpackcompose.domain.model.List as ListModel

data class ListState(
    val isLoading: Boolean = false,
    val lists: List<ListModel> = emptyList(),
    val error: String = ""
)

sealed class ListEvent {
    object GetLists: ListEvent()
}
