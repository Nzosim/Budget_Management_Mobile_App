package com.example.pennywise.utils

import java.text.NumberFormat
import java.util.Locale

fun formatEuro(amount: Double): String {
    val formatter = NumberFormat.getCurrencyInstance(Locale.FRANCE)
    return formatter.format(amount)
}