package com.example.todojetpackcompose.domain.use_case

import com.example.todojetpackcompose.domain.repository.TodoRepository

class GetTodos(
    private val todoRepository: TodoRepository
) {
    suspend operator fun invoke(listId: Int) = todoRepository.getTodos(listId)
}