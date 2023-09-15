package com.example.todojetpackcompose.presentation.todos

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.todojetpackcompose.common.TheConfirmDialog
import com.example.todojetpackcompose.common.TheDialog
import com.example.todojetpackcompose.presentation.lists.ListsViewModel
import com.example.todojetpackcompose.presentation.todos.components.TodoForm
import com.example.todojetpackcompose.presentation.todos.components.TodoTile
import org.burnoutcrew.reorderable.ReorderableItem
import org.burnoutcrew.reorderable.detectReorderAfterLongPress
import org.burnoutcrew.reorderable.rememberReorderableLazyListState
import org.burnoutcrew.reorderable.reorderable

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun TodoView(
    todoViewModel: TodoViewModel = hiltViewModel(),
    listsViewModel: ListsViewModel,
    onBack: () -> Unit,
) {
    val state = todoViewModel.state.value

    val stateLazyColumn = rememberReorderableLazyListState(onMove = { from, to ->
        todoViewModel.onEvent(TodoEvent.MoveTodo(
            fromIndex = from.index,
            toIndex = to.index,
        ))
    })

    LaunchedEffect(null) {
        todoViewModel.setup(listsViewModel)
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
            LazyColumn(
                state = stateLazyColumn.listState,
                modifier = Modifier
                    .fillMaxSize()
                    .reorderable(stateLazyColumn)
            ) {
                items(state.todos, { it.id }) { todo ->
                    ReorderableItem(stateLazyColumn, key = todo.id) { isDragging ->
                        val elevation = animateDpAsState(if (isDragging) 16.dp else 0.dp,
                            label = ""
                        )

                        Column(
                            modifier = Modifier
                                .shadow(elevation.value)
                                .background(Color.Red)
                                .detectReorderAfterLongPress(stateLazyColumn)
                                .animateItemPlacement( animationSpec = tween( durationMillis = 100, easing = FastOutSlowInEasing ) )
                        ) {
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
