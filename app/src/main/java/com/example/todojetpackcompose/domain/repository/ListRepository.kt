package com.example.todojetpackcompose.domain.repository

import com.example.todojetpackcompose.domain.model.List as ListModel

interface ListRepository {
    suspend fun getLists(): List<ListModel>
}