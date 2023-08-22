package com.example.todojetpackcompose.presentation.lists.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.todojetpackcompose.R
import com.example.todojetpackcompose.domain.model.List as ListModel

@Composable
fun ListTile(
    list: ListModel,
    onOpenList: (Int) -> Unit,
    onDelete: (Int) -> Unit,
    onUpdate: (Int) -> Unit
) {
    SwipeActions(
        onDelete = {
            onDelete(list.id)
        },
        onUpdate = {
            onUpdate(list.id)
        }
    ) {
        ListItem(
            headlineContent = {
                Text(text = list.name)
            },
            leadingContent = {
                Icon(
                    painter = painterResource(R.drawable.baseline_circle_24),
                    contentDescription = "List icon",
                    tint = list.color
                )
            },
            modifier = Modifier
                .clickable {
                    onOpenList(list.id)
                }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwipeActions(
    onDelete: () -> Unit,
    onUpdate: () -> Unit,
    content: @Composable () -> Unit
) {
    val dismissState = rememberDismissState(
        confirmValueChange = {
            if (it == DismissValue.DismissedToEnd) {
                onUpdate()
            } else if (it == DismissValue.DismissedToStart) {
                onDelete()
            }
            false
        },
        positionalThreshold =  { _ -> 56.dp.toPx() }
    )

    SwipeToDismiss(
        state = dismissState,
        background = {
            val direction = dismissState.targetValue

            val color by animateColorAsState(
                when (direction) {
                    DismissValue.DismissedToEnd -> Color.Blue
                    DismissValue.DismissedToStart -> Color.Red
                    else -> Color.Transparent
                }, label = ""
            )

            val alignment = when (direction) {
                DismissValue.DismissedToStart  -> Alignment.CenterEnd
                DismissValue.DismissedToEnd -> Alignment.CenterStart
                else -> Alignment.CenterEnd
            }

            val icon = when (direction) {
                DismissValue.DismissedToEnd  -> Icons.Default.Edit
                DismissValue.DismissedToStart -> Icons.Default.Delete
                else -> Icons.Default.Delete
            }

            val scale by animateFloatAsState(
                if (dismissState.targetValue == DismissValue.Default) 0.75f else 1f, label = ""
            )

            Box(
                Modifier
                    .fillMaxSize()
                    .background(color)
                    .padding(horizontal = 20.dp),
                contentAlignment = alignment
            ) {
                Icon(
                    icon,
                    contentDescription = "Localized description",
                    modifier = Modifier.scale(scale)
                )
            }
        },
        dismissContent = {
            content()
        }
    )
}