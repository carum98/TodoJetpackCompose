package com.example.todojetpackcompose.domain.repository

import com.example.todojetpackcompose.domain.model.List as ListModel

interface ListRepository {
    suspend fun getLists(): List<ListModel>
    suspend fun createList(name: String, color: String): ListModel
    suspend fun updateList(listId: Int, name: String, color: String): ListModel
    suspend fun deleteList(listId: Int)
}