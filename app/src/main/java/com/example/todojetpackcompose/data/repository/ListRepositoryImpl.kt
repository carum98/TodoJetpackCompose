package com.example.todojetpackcompose.data.repository

import com.example.todojetpackcompose.data.api.ListService
import com.example.todojetpackcompose.data.api.dto.toList
import com.example.todojetpackcompose.domain.repository.ListRepository
import javax.inject.Inject
import com.example.todojetpackcompose.domain.model.List as ListModel

class ListRepositoryImpl @Inject constructor(
    private val listService: ListService
) : ListRepository {
    override suspend fun getLists(): List<ListModel> {
        val listDto = listService.getLists()
        return listDto.data.map { it.toList() }
    }
}