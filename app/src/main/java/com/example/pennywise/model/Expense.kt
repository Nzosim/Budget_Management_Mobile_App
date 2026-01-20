package com.example.pennywise.model

data class Expense(
    val label: String,
    val amount: Double,
    val date: String,
    val type: String,
    val categoryId: Int
)