package com.example.pennywise.utils

import java.text.NumberFormat
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale
import java.time.format.DateTimeFormatter

fun formatEuro(amount: Double): String {
    val formatter = NumberFormat.getCurrencyInstance(Locale.FRANCE)
    return formatter.format(amount)
}

fun formatDate(date: LocalDate): String {
    val formatter = DateTimeFormatter.ofPattern(
        "MMMM yyyy",
        Locale.FRANCE
    )
    return date.format(formatter)
}