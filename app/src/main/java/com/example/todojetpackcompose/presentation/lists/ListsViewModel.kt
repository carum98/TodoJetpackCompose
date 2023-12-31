package com.example.todojetpackcompose.presentation.lists

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todojetpackcompose.domain.use_case.GetListsUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import com.example.todojetpackcompose.common.Resource
import com.example.todojetpackcompose.domain.use_case.AddListUseCase
import com.example.todojetpackcompose.domain.use_case.RemoveListUseCase
import com.example.todojetpackcompose.domain.use_case.UpdateListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListsViewModel @Inject constructor(
    private val getListUseCase: GetListsUseCase,
    private val addListUseCase: AddListUseCase,
    private val updateListUseCase: UpdateListUseCase,
    private val removeListUseCase: RemoveListUseCase,
): ViewModel() {
    private val _state = mutableStateOf(ListState())
    val state: State<ListState> = _state

    fun onEvent(event: ListEvent) {
        when (event) {
            is ListEvent.GetLists -> {
                getLists()
            }
            is ListEvent.DeleteList -> {
                deleteList(event.id)
            }
            is ListEvent.UpdateList -> {
                updateList(event)
            }
            is ListEvent.CreateList -> {
                createList(event)
            }
            is ListEvent.UpdateCount -> {
                updateCount(event)
            }
            ListEvent.CloseDialogs -> {
                _state.value = state.value.copy(
                    showDialog = false,
                    showAlertDialog = false,
                    listSelected = null
                )
            }
            ListEvent.OpenDialogCreateList -> {
                _state.value = state.value.copy(
                    showDialog = true
                )
            }
            is ListEvent.OpenDialogDeleteList -> {
                _state.value = state.value.copy(
                    showAlertDialog = true,
                    listSelected = event.list
                )
            }
            is ListEvent.OpenDialogUpdateList -> {
                _state.value = state.value.copy(
                    showDialog = true,
                    listSelected = event.list
                )
            }
        }
    }

    private fun getLists() {
        getListUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    result.data?.let { lists ->
                        _state.value = state.value.copy(
                            lists = lists,
                            isLoading = false,
                            error = ""
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

    private fun deleteList(listId: Int) {
        viewModelScope.launch {
            removeListUseCase(listId = listId)

            _state.value = state.value.copy(
                lists = state.value.lists.filter { it.id != listId },
                isLoading = false
            )
        }
    }

    private fun updateList(params: ListEvent.UpdateList) {
        updateListUseCase(listId = params.id, name = params.name, color = params.color).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    result.data?.let { list ->
                        _state.value = state.value.copy(
                            lists = state.value.lists.map { if (it.id == list.id) list else it },
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

    private fun createList(params: ListEvent.CreateList) {
        addListUseCase(name = params.name, color = params.color).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    result.data?.let { list ->
                        _state.value = state.value.copy(
                            lists = listOf(list) + state.value.lists,
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

    private fun updateCount(params: ListEvent.UpdateCount) {
        _state.value = state.value.copy(
            lists = state.value.lists.map { if (it.id == params.id) it.copy(count = it.count + params.value) else it },
            isLoading = false
        )
    }
}