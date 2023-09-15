package com.example.todojetpackcompose.presentation.lists

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.todojetpackcompose.common.TheConfirmDialog
import com.example.todojetpackcompose.common.TheDialog
import com.example.todojetpackcompose.presentation.lists.components.ListFormView
import com.example.todojetpackcompose.presentation.lists.components.ListTile

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListsView(
    listsViewModel: ListsViewModel,
    onOpenList: (Int) -> Unit
) {
    val state = listsViewModel.state.value

    LaunchedEffect(null) {
        if (state.lists.isEmpty()) {
            println("Get lists")
            listsViewModel.onEvent(ListEvent.GetLists)
        }
    }

    if (state.showDialog) {
        TheDialog(
            onDismissRequest = {
                listsViewModel.onEvent(ListEvent.CloseDialogs)
            }
        ) { onDismiss ->
            ListFormView(
                initialName = state.listSelected?.name,
                initialColor = state.listSelected?.color,
                buttonText = if (state.listSelected != null) "Update" else "Create",
                onConfirm = { name, color ->
                    listsViewModel.onEvent(
                        if (state.listSelected != null) {
                            ListEvent.UpdateList(state.listSelected.id, name, color)
                        } else {
                            ListEvent.CreateList(name, color)
                        }
                    )

                    onDismiss()
                }
            )
        }
    }

    if (state.showAlertDialog) {
        TheConfirmDialog(
            title = "Delete list",
            description = "Are you sure you want to delete this list?",
            onDismiss = {
                listsViewModel.onEvent(ListEvent.CloseDialogs)
            },
            onConfirm = {
                listsViewModel.onEvent(ListEvent.DeleteList(state.listSelected!!.id))
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
                listsViewModel.onEvent(ListEvent.OpenDialogCreateList)
            }) {
                Icon(Icons.Filled.Add, contentDescription = "Add list")
            }
        }
    ) { contentPadding ->
        Box(modifier = Modifier.padding(contentPadding)) {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(15.dp, 0.dp),
                verticalArrangement = Arrangement.spacedBy(15.dp)
            ) {
                items(state.lists) { list ->
                    ListTile(
                        list = list,
                        onOpenList = onOpenList,
                        onDelete = {
                            listsViewModel.onEvent(ListEvent.OpenDialogDeleteList(it))
                        },
                        onUpdate = {
                            listsViewModel.onEvent(ListEvent.OpenDialogUpdateList(it))
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