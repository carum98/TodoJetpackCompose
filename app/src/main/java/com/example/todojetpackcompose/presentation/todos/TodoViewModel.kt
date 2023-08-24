package com.example.todojetpackcompose.presentation.todos

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todojetpackcompose.common.Resource
import com.example.todojetpackcompose.domain.model.Todo
import com.example.todojetpackcompose.domain.use_case.AddTodoUseCase
import com.example.todojetpackcompose.domain.use_case.GetTodosUseCase
import com.example.todojetpackcompose.domain.use_case.RemoveTodoUseCase
import com.example.todojetpackcompose.domain.use_case.ToggleTodoUseCase
import com.example.todojetpackcompose.domain.use_case.UpdateTodoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor(
    private val getTodosUseCase: GetTodosUseCase,
    private val toggleTodoUseCase: ToggleTodoUseCase,
    private val savedStateHandle: SavedStateHandle,
    private val removeTodoUseCase: RemoveTodoUseCase,
    private val updateTodoUseCase: UpdateTodoUseCase,
    private val addTodoUseCase: AddTodoUseCase,
): ViewModel() {
    private val _state = mutableStateOf(TodoState())
    val state: State<TodoState> = _state

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
            is TodoEvent.CreateTodo -> {
                savedStateHandle.get<Int>("listId")?.let { listId ->
                    createTodo(event, listId)
                }
            }
            is TodoEvent.UpdateTodo -> {
                updateTodo(event)
            }
            is TodoEvent.DeleteTodo -> {
                deleteTodo(event.todo.id)
            }
            TodoEvent.OpenDialogCreateTodo -> {
                _state.value = state.value.copy(
                    showDialog = true
                )
            }
            is TodoEvent.OpenDialogDeleteTodo -> {
                _state.value = state.value.copy(
                    showAlertDialog = true,
                    todoSelected = event.todo
                )
            }
            is TodoEvent.OpenDialogUpdateTodo -> {
                _state.value = state.value.copy(
                    showDialog = true,
                    todoSelected = event.todo
                )
            }
            TodoEvent.CloseDialogs -> {
                _state.value = state.value.copy(
                    showDialog = false,
                    showAlertDialog = false,
                    todoSelected = null
                )
            }
        }
    }

    private fun getTodos(listId: Int) {
        getTodosUseCase(listId).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    result.data?.let { todos ->
                        _state.value = state.value.copy(
                            todos = todos,
                            isLoading = false
                        )
                    }
                }

                is Resource.Error -> {
                    _state.value = state.value.copy(
                        error = result.message ?: "An unexpected error occured",
                        isLoading = false
                    )
                }

                is Resource.Loading -> {
                    _state.value = state.value.copy(
                        isLoading = true
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun createTodo(params: TodoEvent.CreateTodo, listId: Int) {
        addTodoUseCase(params.title, listId).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    result.data?.let { todo ->
                        _state.value = state.value.copy(
                            todos = state.value.todos + todo,
                            isLoading = false
                        )
                    }
                }
                is Resource.Error -> {
                    _state.value = state.value.copy(
                        error = result.message ?: "An unexpected error occured",
                        isLoading = false
                    )
                }
                is Resource.Loading -> {}
            }
        }.launchIn(viewModelScope)
    }

    private fun updateTodo(params: TodoEvent.UpdateTodo) {
        updateTodoUseCase(params.title, params.id).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    result.data?.let { todo ->
                        _state.value = state.value.copy(
                            todos = state.value.todos.map { if (it.id == todo.id) todo else it },
                            isLoading = false
                        )
                    }
                }
                is Resource.Error -> {
                    _state.value = state.value.copy(
                        error = result.message ?: "An unexpected error occured",
                        isLoading = false
                    )
                }
                is Resource.Loading -> {}
            }
        }.launchIn(viewModelScope)
    }

    private fun deleteTodo(todoId: Int) {
        viewModelScope.launch {
            removeTodoUseCase(todoId = todoId)

            _state.value = state.value.copy(
                todos = state.value.todos.filter { it.id != todoId },
                isLoading = false
            )
        }
    }

    private fun toggleTodo(todo: Todo) {
        viewModelScope.launch {
            toggleTodoUseCase(todo.id)

            val newTodos = state.value.todos.map {
                if (it.id == todo.id) {
                    it.copy(isComplete = !it.isComplete)
                } else {
                    it
                }
            }

            _state.value = state.value.copy(todos = newTodos)
        }
    }
}