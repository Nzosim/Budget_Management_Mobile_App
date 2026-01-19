package com.example.pennywise.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import com.example.pennywise.components.budgetScreen.BudgetBody
import com.example.pennywise.components.budgetScreen.BudgetHeader
import java.time.LocalDate

@Composable
fun BudgetScreen(navController: NavController) {
    val remaining by remember { mutableStateOf( 12500.0) };
    val spend by remember { mutableStateOf( 2500.0) };
    var month by remember { mutableStateOf(LocalDate.now()) }

    Column {
        BudgetHeader(remaining, month,
            onPrevious = {
                month = month.minusMonths(1)
            },
            onNext = {
                if(month.isBefore(LocalDate.now())) {
                    month = month.plusMonths(1)
                }
            }
        )

        BudgetBody(month, spend)
    }
}
