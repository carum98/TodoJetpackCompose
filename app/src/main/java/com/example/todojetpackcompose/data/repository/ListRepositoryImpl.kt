package com.example.todojetpackcompose.data.repository

import com.example.todojetpackcompose.domain.repository.ListRepository
import com.example.todojetpackcompose.domain.model.List as ListModel

class ListRepositoryImpl : ListRepository {
    override suspend fun getLists(): List<ListModel> {
        TODO("Not yet implemented")
    }
}