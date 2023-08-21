package com.example.todojetpackcompose.presentation.lists

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

@Composable
fun ListFormView(
    onConfirm: (String, String) -> Unit
) {
    val name = remember { mutableStateOf("") }

    Column {
        OutlinedTextField(
            value = name.value,
            onValueChange = { name.value = it },
            maxLines = 1
        )

        Button(onClick = {
            onConfirm(name.value, "color")
        }) {
            Text("Confirm")
        }
    }
}