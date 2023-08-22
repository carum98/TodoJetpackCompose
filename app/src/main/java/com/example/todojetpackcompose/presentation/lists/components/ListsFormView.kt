package com.example.todojetpackcompose.presentation.lists.components

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.todojetpackcompose.common.ColorPicker
import com.example.todojetpackcompose.common.TextFormField

@Composable
fun ListFormView(
    onConfirm: (String, Color) -> Unit
) {
    val name = remember { mutableStateOf("") }
    val color = remember { mutableStateOf(Color(0xFFf43b30)) }

    val isEnable = name.value.isNotEmpty()

    Column {
        TextFormField(
            value = name.value,
            onValueChange = { name.value = it },
            label = "Name"
        )

        Spacer(modifier = Modifier.height(20.dp))

        ColorPicker(
            value = color.value,
            onValueChange = { color.value = it }
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                onConfirm(name.value, color.value)
            },
            enabled = isEnable,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Create")
        }
    }
}