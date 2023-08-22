package com.example.todojetpackcompose.domain.repository

import androidx.compose.ui.graphics.Color
import com.example.todojetpackcompose.domain.model.List as ListModel

interface ListRepository {
    suspend fun getLists(): List<ListModel>
    suspend fun createList(name: String, color: Color): ListModel
    suspend fun updateList(listId: Int, name: String, color: Color): ListModel
    suspend fun deleteList(listId: Int)
}