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
import com.example.todojetpackcompose.domain.use_case.MoveTodoUseCase
import com.example.todojetpackcompose.domain.use_case.RemoveTodoUseCase
import com.example.todojetpackcompose.domain.use_case.ToggleTodoUseCase
import com.example.todojetpackcompose.domain.use_case.UpdateTodoUseCase
import com.example.todojetpackcompose.presentation.lists.ListEvent
import com.example.todojetpackcompose.presentation.lists.ListsViewModel
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
    private val moveTodoUseCase: MoveTodoUseCase,
): ViewModel() {
    private val _state = mutableStateOf(TodoState())
    val state: State<TodoState> = _state

    private var listId: Int? = null
    private var listViewModel: ListsViewModel? = null

    fun setup(listViewModel: ListsViewModel) {
        savedStateHandle.get<Int>("listId")?.let { listId ->
            this.listId = listId
        }

        this.listViewModel = listViewModel
    }

    fun onEvent(event: TodoEvent) {
        when (event) {
            is TodoEvent.GetTodos -> {
                this.listId?.let { getTodos(it) }
            }
            is TodoEvent.ToggleTodo -> {
                toggleTodo(event.todo)
            }
            is TodoEvent.CreateTodo -> {
                this.listId?.let { createTodo(event, it) }
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
            is TodoEvent.MoveTodo -> {
                _state.value = state.value.copy(
                    todos = state.value.todos.toMutableList().apply {
                        add(event.toIndex, removeAt(event.fromIndex))
                    }
                )

                moveTodo(event)
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

                        listViewModel?.onEvent(ListEvent.UpdateCount(listId, 1))
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

            listViewModel?.onEvent(ListEvent.UpdateCount(listId!!, if (todo.isComplete) 1 else -1))
        }
    }

    private fun moveTodo(params: TodoEvent.MoveTodo) {
        val todo = state.value.todos[params.toIndex]
        moveTodoUseCase(todo.id, params.toIndex).launchIn(viewModelScope)
    }
}