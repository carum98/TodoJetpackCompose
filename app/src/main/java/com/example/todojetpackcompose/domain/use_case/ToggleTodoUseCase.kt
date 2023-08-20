package com.example.todojetpackcompose.domain.use_case

import com.example.todojetpackcompose.domain.repository.TodoRepository
import javax.inject.Inject

class ToggleTodoUseCase @Inject constructor(
    private val todoRepository: TodoRepository
) {
    suspend operator fun invoke(todoId: Int) {
        todoRepository.toggleTodoComplete(todoId)
    }
}