package com.example.todojetpackcompose.presentation.todos

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.todojetpackcompose.common.TheConfirmDialog
import com.example.todojetpackcompose.common.TheDialog
import com.example.todojetpackcompose.presentation.todos.components.TodoForm
import com.example.todojetpackcompose.presentation.todos.components.TodoTile

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoView(
    todoViewModel: TodoViewModel = hiltViewModel(),
    onBack: () -> Unit,
) {
    val state = todoViewModel.state.value

    LaunchedEffect(null) {
        todoViewModel.onEvent(TodoEvent.GetTodos)
    }

    if (state.showDialog) {
        TheDialog(
            onDismissRequest = {
                todoViewModel.onEvent(TodoEvent.CloseDialogs)
            }
        ) { onDismiss ->
            TodoForm(
                initialTitle = state.todoSelected?.title,
                buttonText = if (state.todoSelected != null) "Update" else "Create",
                onConfirm = { title ->
                    todoViewModel.onEvent(
                        if (state.todoSelected != null) {
                            TodoEvent.UpdateTodo(state.todoSelected.id, title)
                        } else {
                            TodoEvent.CreateTodo(title)
                        }
                    )
                    onDismiss()
                }
            )
        }
    }

    if (state.showAlertDialog) {
        TheConfirmDialog(
            title = "Delete todo",
            description = "Are you sure you want to delete this todo?",
            onDismiss = {
                todoViewModel.onEvent(TodoEvent.CloseDialogs)
            },
            onConfirm = {
                todoViewModel.onEvent(TodoEvent.DeleteTodo(state.todoSelected!!))
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Todo App")
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                todoViewModel.onEvent(TodoEvent.OpenDialogCreateTodo)
            }) {
                Icon(Icons.Filled.Add, contentDescription = "Add list")
            }
        }
    ) { contentPadding ->
        Box(modifier = Modifier
            .padding(contentPadding)
            .padding(15.dp, 0.dp)
        ) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(state.todos) { todo ->
                    Column {
                        TodoTile(
                            todo = todo,
                            onToggleTodo = {
                                todoViewModel.onEvent(TodoEvent.ToggleTodo(it))
                            },
                            onDelete = {
                                todoViewModel.onEvent(TodoEvent.OpenDialogDeleteTodo(it))
                            },
                            onUpdate = {
                                todoViewModel.onEvent(TodoEvent.OpenDialogUpdateTodo(it))
                            }
                        )

                        if (state.todos.last() != todo) {
                            Divider()
                        }
                    }
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
