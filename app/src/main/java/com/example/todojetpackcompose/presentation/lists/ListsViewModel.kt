package com.example.todojetpackcompose.presentation.lists

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todojetpackcompose.domain.use_case.GetListsUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import com.example.todojetpackcompose.common.Resource
import com.example.todojetpackcompose.data.datastore.AuthenticationService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ListsViewModel @Inject constructor(
    private val getListUseCase: GetListsUseCase,
    private val authenticationService: AuthenticationService
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
        }
    }

    suspend fun logout() {
        authenticationService.onLogout()
    }

    private fun getLists() {
        getListUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    result.data?.let { lists ->
                        _state.value = state.value.copy(
                            lists = lists,
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

    private fun addList() {
        TODO()
    }

    private fun deleteList(listId: Int) {
        println("Delete list $listId")
    }

    private fun updateList(params: ListEvent.UpdateList) {
        println("Update list $params")
    }

    private fun createList(params: ListEvent.CreateList) {
        println("Create list $params")
    }
}