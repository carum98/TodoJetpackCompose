package com.example.todojetpackcompose.domain.use_case

import androidx.compose.ui.graphics.Color
import com.example.todojetpackcompose.common.Resource
import com.example.todojetpackcompose.domain.repository.ListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

import com.example.todojetpackcompose.domain.model.List as ListModel

class AddListUseCase @Inject constructor(
    private val listRepository: ListRepository
) {
    operator fun invoke(name: String, color: Color): Flow<Resource<ListModel>> = flow {
        try {
            emit(Resource.Loading())
            val response = listRepository.createList(name, color)
            emit(Resource.Success(response))
        } catch(e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occured"))
        }
    }
}