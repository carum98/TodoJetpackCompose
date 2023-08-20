package com.example.todojetpackcompose.domain.model

import android.graphics.Color.parseColor
import androidx.compose.ui.graphics.Color

data class List(
    val id: Int,
    val name: String,
    private val colorHexadecimal: String,
) {
    val color: Color = Color(parseColor(colorHexadecimal))
}
