package com.example.todojetpackcompose.data.repository

import com.example.todojetpackcompose.data.api.TodoService
import com.example.todojetpackcompose.data.api.dto.toTodo
import com.example.todojetpackcompose.domain.model.Todo
import com.example.todojetpackcompose.domain.repository.TodoRepository
import javax.inject.Inject

class TodoRepositoryImpl @Inject constructor(
    private val todoService: TodoService
): TodoRepository {
    override suspend fun getTodos(listId: Int): List<Todo> {
        val todoDto = todoService.getTodos(listId)
        return todoDto.data.map { it.toTodo() }
    }

    override suspend fun toggleTodoComplete(todoId: Int) {
        println("todoIt is $todoId")
        todoService.toggleTodoComplete(todoId)
    }
}