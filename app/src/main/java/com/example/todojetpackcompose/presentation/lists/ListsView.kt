package com.example.todojetpackcompose.presentation.lists

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.todojetpackcompose.common.TheConfirmDialog
import com.example.todojetpackcompose.common.TheDialog
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListsView(
    listsViewModel: ListsViewModel = hiltViewModel(),
    onOpenList: (Int) -> Unit
) {
    val scope = rememberCoroutineScope()
    val state = listsViewModel.state.value

    val showDialog = remember { mutableStateOf(false) }
    val showAlertDialog = remember { mutableStateOf(false) }
    val selectedId = remember { mutableStateOf<Int?>(null) }

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
                onConfirm = { name, color ->
                    if (selectedId.value != null) {
                        listsViewModel.onEvent(ListEvent.UpdateList(selectedId.value!!, name, color))
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
                    listsViewModel.onEvent(ListEvent.DeleteList(selectedId.value!!))
                }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("ToDo App")
                },
                actions = {
                    IconButton(onClick = {
                        scope.launch {
                            listsViewModel.logout()
                        }
                    }) {
                        Icon(imageVector = Icons.Filled.ExitToApp, contentDescription = "Más")
                    }
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