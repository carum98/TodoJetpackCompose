package com.example.todojetpackcompose.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ColorPicker(
    value: Color,
    onValueChange: (Color) -> Unit,
) {
    val colors = listOf(
        Color(0xFFf43b30),
        Color(0xFFe71f56),
        Color(0xFF9c27b0),
        Color(0xFF673ab7),
        Color(0xFF3f51b5),
        Color(0xFF5677fc),
        Color(0xFF03a9f4),
        Color(0xFF00bcd4),
        Color(0xFF009688),
        Color(0xFF259b24),
        Color(0xFF8bc34a),
        Color(0xFFcddc39),
        Color(0xFFffeb3b),
        Color(0xFFffc107),
        Color(0xFFff9800),
        Color(0xFFff5722),
        Color(0xFF795548),
        Color(0xFF607d8b),
        Color(0xFF9e9e9e),
    )

    val rows = colors.chunked(6)

    Column(
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        rows.forEach { row ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                row.forEach { color ->
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(35.dp)
                            .background(
                                color = color,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .clickable {
                                onValueChange(color)
                            }
                    ) {
                        if (color == value) {
                            Icon(
                                Icons.Filled.Check,
                                contentDescription = "Selected color",
                                tint = Color.White,
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun ColorPickerPreview() {
    ColorPicker(
        value = Color(0xFFe71f56),
        onValueChange = {}
    )
}