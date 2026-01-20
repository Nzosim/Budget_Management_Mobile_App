package com.example.pennywise.model

import androidx.compose.ui.graphics.Color

data class Category(
    val id: Int,
    val label: String,
    val amount: Double,
    val color: Color
)