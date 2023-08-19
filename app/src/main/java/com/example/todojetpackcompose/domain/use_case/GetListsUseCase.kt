package com.example.todojetpackcompose.domain.use_case

import com.example.todojetpackcompose.common.Resource
import com.example.todojetpackcompose.domain.repository.ListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import com.example.todojetpackcompose.domain.model.List as ListModel

class GetListsUseCase(
    private val listRepository: ListRepository
) {
    operator fun invoke(): Flow<Resource<List<ListModel>>> = flow {
        try {
            emit(Resource.Loading())
            val lists = listRepository.getLists()
            emit(Resource.Success(lists))
        } catch(e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occured"))
        }
    }
}