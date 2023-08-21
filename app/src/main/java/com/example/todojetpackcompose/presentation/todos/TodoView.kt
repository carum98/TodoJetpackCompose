package com.example.todojetpackcompose.presentation.todos

import androidx.compose.foundation.clickable
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
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.todojetpackcompose.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoView(
    todoViewModel: TodoViewModel = hiltViewModel(),
    onBack: () -> Unit,
    onAddTodo: () -> Unit
) {
    val state = todoViewModel.state

    LaunchedEffect(null) {
        todoViewModel.onEvent(TodoEvent.GetTodos)
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
                onAddTodo()
            }) {
                Icon(Icons.Filled.Add, contentDescription = "Add list")
            }
        }
    ) {
        Box(modifier = Modifier
            .padding(it)
            .padding(15.dp, 0.dp)
        ) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(state.todos) { todo ->
                    Column {
                        ListItem(
                            headlineContent = {
                                Text(text = todo.title)
                            },
                            leadingContent = {
                                Icon(
                                    painter = painterResource(if (todo.isComplete) R.drawable.baseline_radio_button_checked_24 else R.drawable.baseline_radio_button_unchecked_24),
                                    contentDescription = "Complete"
                                )
                            },
                            modifier = Modifier
                                .clickable {
                                    todoViewModel.onEvent(TodoEvent.ToggleTodo(todo))
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
