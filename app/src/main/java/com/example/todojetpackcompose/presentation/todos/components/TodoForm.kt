package com.example.todojetpackcompose.presentation.todos.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.todojetpackcompose.common.TextFormField

@Composable
fun TodoForm(
    initialTitle: String?,
    buttonText: String,
    onConfirm: (String) -> Unit
) {
    val title = remember { mutableStateOf(initialTitle ?: "") }

    val isEnable = title.value.isNotEmpty()

    Column {
        TextFormField(
            value = title.value,
            onValueChange = { title.value = it },
            label = "Name"
        )

        Spacer(modifier = Modifier.height(20.dp))

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                onConfirm(title.value)
            },
            enabled = isEnable,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(buttonText)
        }
    }
}