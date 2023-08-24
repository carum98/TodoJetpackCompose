package com.example.todojetpackcompose.presentation.todos.components

import androidx.compose.foundation.clickable
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.todojetpackcompose.R
import com.example.todojetpackcompose.domain.model.Todo
import com.example.todojetpackcompose.presentation.lists.components.SwipeActions

@Composable
fun TodoTile(
    todo: Todo,
    onToggleTodo: (Todo) -> Unit,
    onDelete: (Todo) -> Unit,
    onUpdate: (Todo) -> Unit
) {
    SwipeActions(
        onDelete = {
            onDelete(todo)
        },
        onUpdate = {
            onUpdate(todo)
        }
    ) {
        ListItem(
            headlineContent = {
                Text(text = todo.title)
            },
            leadingContent = {
                Icon(
                    painter = painterResource(
                        if (todo.isComplete) R.drawable.baseline_radio_button_checked_24
                        else R.drawable.baseline_radio_button_unchecked_24
                    ),
                    contentDescription = "Complete"
                )
            },
            modifier = Modifier
                .clickable {
                    onToggleTodo(todo)
                }
        )
    }
}