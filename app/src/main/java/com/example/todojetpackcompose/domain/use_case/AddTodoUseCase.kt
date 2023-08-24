package com.example.todojetpackcompose.domain.use_case

import com.example.todojetpackcompose.common.Resource
import com.example.todojetpackcompose.domain.model.Todo
import com.example.todojetpackcompose.domain.repository.TodoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AddTodoUseCase @Inject constructor(
    private val todoRepository: TodoRepository,
) {
    operator fun invoke(title: String, listId: Int): Flow<Resource<Todo>> = flow {
        try {
            emit(Resource.Loading())
            val response = todoRepository.createTodo(
                title = title,
                listId = listId
            )
            emit(Resource.Success(response))
        } catch(e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occured"))
        }
    }
}