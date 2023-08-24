package com.example.todojetpackcompose.presentation.lists

import androidx.compose.ui.graphics.Color
import com.example.todojetpackcompose.domain.model.List as ListModel

data class ListState(
    val isLoading: Boolean = false,
    val lists: List<ListModel> = emptyList(),
    val error: String = "",
    val showDialog: Boolean = false,
    val showAlertDialog: Boolean = false,
    val listSelected: ListModel? = null
)

sealed class ListEvent {
    object GetLists: ListEvent()
    data class DeleteList(val id: Int): ListEvent()
    data class UpdateList(val id: Int, val name: String, val color: Color): ListEvent()
    data class CreateList(val name: String, val color: Color): ListEvent()
    object OpenDialogCreateList: ListEvent()
    data class OpenDialogUpdateList(val list: ListModel): ListEvent()
    data class OpenDialogDeleteList(val list: ListModel): ListEvent()
    object CloseDialogs: ListEvent()
}
