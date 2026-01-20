package com.example.pennywise.model

import androidx.compose.ui.graphics.Color

data class Expense(
    val label: String,
    val amount: Double,
    val date: String,
    val type: String,
    val categoryId: Int,
    val categoryColor: Color
)