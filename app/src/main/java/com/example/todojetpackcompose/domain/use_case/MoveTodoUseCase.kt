package com.example.todojetpackcompose.domain.use_case

import com.example.todojetpackcompose.common.Resource
import com.example.todojetpackcompose.domain.model.Todo
import com.example.todojetpackcompose.domain.repository.TodoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MoveTodoUseCase @Inject constructor(
    private val todoRepository: TodoRepository,
) {
    operator fun invoke(todoId: Int, toIndex: Int): Flow<Resource<List<Todo>>> = flow {
        println("$todoId, $toIndex")
        try {
            emit(Resource.Loading())
            val response = todoRepository.moveTodo(
                todoId = todoId,
                toIndex = toIndex,
            )
            println(response)
            emit(Resource.Success(response))
        } catch(e: Exception) {
            println(e.localizedMessage ?: "An unexpected error occured")
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occured"))
        }
    }
}