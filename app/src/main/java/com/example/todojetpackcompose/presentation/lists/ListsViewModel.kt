package com.example.todojetpackcompose.presentation.lists

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todojetpackcompose.domain.use_case.GetListsUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import com.example.todojetpackcompose.common.Resource

class ListsViewModel(
    private val getListUseCase: GetListsUseCase
): ViewModel() {
    private val _state = mutableStateOf(ListState())
    val state: State<ListState> = _state

    init {
        onEvent(ListEvent.GetLists)
    }

    fun onEvent(event: ListEvent) {
        when (event) {
            is ListEvent.GetLists -> {
                getLists()
            }
        }
    }

    private fun getLists() {
        getListUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    println("Successfully got lists: ${result.data}")
//                    _state.value = state.value.copy(isLoading = false)
//
//                    result.data?.let { lists ->
//                        _state.value = state.value.copy(lists = lists)
//                    }
                }
                is Resource.Error -> {
                    println("Failed to get lists: ${result.message}")
//                    _state.value = state.value.copy(isLoading = false)
//                    _state.value = state.value.copy(error = result.message ?: "An unexpected error occured")
                }
                is Resource.Loading -> {
                    println("Loading...")
//                    _state.value = state.value.copy(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}