package com.example.todojetpackcompose.presentation.todos

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todojetpackcompose.common.Resource
import com.example.todojetpackcompose.domain.model.Todo
import com.example.todojetpackcompose.domain.use_case.GetTodosUseCase
import com.example.todojetpackcompose.domain.use_case.ToggleTodoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor(
    private val getTodosUseCase: GetTodosUseCase,
    private val toggleTodoUseCase: ToggleTodoUseCase,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {
    private val _state = mutableStateOf(TodoState())
    val state get() = _state.value

    fun onEvent(event: TodoEvent) {
        when (event) {
            is TodoEvent.GetTodos -> {
                savedStateHandle.get<Int>("listId")?.let { listId ->
                    getTodos(listId)
                }
            }
            is TodoEvent.ToggleTodo -> {
                toggleTodo(event.todo)
            }
        }
    }

    private fun getTodos(listId: Int) {
        getTodosUseCase(listId).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    result.data?.let { todos ->
                        _state.value = state.copy(
                            todos = todos,
                            isLoading = false
                        )
                    }
                }

                is Resource.Error -> {
                    _state.value = state.copy(
                        error = result.message ?: "An unexpected error occured",
                        isLoading = false
                    )
                }

                is Resource.Loading -> {
                    _state.value = state.copy(
                        isLoading = true
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun toggleTodo(todo: Todo) {
        viewModelScope.launch {
            toggleTodoUseCase(todo.id)

            val newTodos = state.todos.map {
                if (it.id == todo.id) {
                    it.copy(isComplete = !it.isComplete)
                } else {
                    it
                }
            }

            _state.value = state.copy(todos = newTodos)
        }
    }
}