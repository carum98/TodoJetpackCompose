package com.example.todojetpackcompose.data.repository

import androidx.compose.ui.graphics.Color
import com.example.todojetpackcompose.data.api.ListService
import com.example.todojetpackcompose.data.api.dto.ListDtoRequest
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

    override suspend fun createList(name: String, color: Color): ListModel {
        val listDto = listService.createList(ListDtoRequest(name, color))

        return listDto.toList()
    }

    override suspend fun updateList(listId: Int, name: String, color: Color): ListModel {
        val listDto = listService.updateList(listId, ListDtoRequest(name, color))

        return listDto.toList()
    }

    override suspend fun deleteList(listId: Int) {
        listService.deleteList(listId)
    }
}