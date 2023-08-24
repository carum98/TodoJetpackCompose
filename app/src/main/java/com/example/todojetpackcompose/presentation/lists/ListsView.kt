package com.example.todojetpackcompose.presentation.lists

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.todojetpackcompose.common.TheConfirmDialog
import com.example.todojetpackcompose.common.TheDialog
import com.example.todojetpackcompose.presentation.lists.components.ListFormView
import com.example.todojetpackcompose.presentation.lists.components.ListTile
import com.example.todojetpackcompose.domain.model.List as ListModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListsView(
    listsViewModel: ListsViewModel = hiltViewModel(),
    onOpenList: (Int) -> Unit
) {
    val state = listsViewModel.state.value

    val showDialog = remember { mutableStateOf(false) }
    val showAlertDialog = remember { mutableStateOf(false) }
    val selectedId = remember { mutableStateOf<ListModel?>(null) }

    LaunchedEffect(null) {
        if (state.lists.isEmpty()) {
            println("Get lists")
            listsViewModel.onEvent(ListEvent.GetLists)
        }
    }

    if (showDialog.value) {
        TheDialog(
            onDismissRequest = {
                showDialog.value = false
                selectedId.value = null
            }
        ) { onDismiss ->
            ListFormView(
                initialName = selectedId.value?.name,
                initialColor = selectedId.value?.color,
                buttonText = if (selectedId.value != null) "Update" else "Create",
                onConfirm = { name, color ->
                    if (selectedId.value != null) {
                        listsViewModel.onEvent(ListEvent.UpdateList(selectedId.value!!.id, name, color))
                    } else {
                        listsViewModel.onEvent(ListEvent.CreateList(name, color))
                    }

                    onDismiss()
                }
            )
        }
    }

    if (showAlertDialog.value) {
        TheConfirmDialog(
            title = "Delete list",
            description = "Are you sure you want to delete this list?",
            onDismiss = {
                showAlertDialog.value = false
                selectedId.value = null
            },
            onConfirm = {
                showAlertDialog.value = false
                if (selectedId.value != null) {
                    listsViewModel.onEvent(ListEvent.DeleteList(selectedId.value!!.id))
                }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("ToDo App")
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                showDialog.value = true
            }) {
                Icon(Icons.Filled.Add, contentDescription = "Add list")
            }
        }
    ) { contentPadding ->
        Box(modifier = Modifier.padding(contentPadding)) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(state.lists) { list ->
                    ListTile(
                        list = list,
                        onOpenList = onOpenList,
                        onDelete = {
                            selectedId.value = it
                            showAlertDialog.value = true
                        },
                        onUpdate = {
                            selectedId.value = it
                            showDialog.value = true
                        }
                    )
                }
            }
            if (state.error.isNotBlank()) {
                Text(text = state.error)
            }
            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}