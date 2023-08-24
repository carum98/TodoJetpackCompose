package com.example.todojetpackcompose.data.repository

import com.example.todojetpackcompose.data.api.TodoService
import com.example.todojetpackcompose.data.api.dto.TodoDtoRequest
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
        todoService.toggleTodoComplete(todoId)
    }

    override suspend fun createTodo(title: String, listId: Int): Todo {
        val todoDto = todoService.createTodo(
            TodoDtoRequest(title, listId)
        )

        return todoDto.toTodo()
    }

    override suspend fun updateTodo(todoId: Int, title: String): Todo {
        val todoDto = todoService.updateTodoTitle(
            todoId,
            TodoDtoRequest(title, null)
        )

        return todoDto.toTodo()
    }

    override suspend fun deleteTodo(todoId: Int) {
        todoService.deleteTodos(todoId)
    }
}