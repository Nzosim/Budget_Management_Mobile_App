package com.example.pennywise.components.budgetScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pennywise.utils.formatEuro

@Composable
fun budgetHeader(remaining: Double) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            "Budget",
            color = Color.White,
            fontSize = 30.sp,
            modifier = Modifier
                .padding(bottom = 10.dp),
            fontWeight = FontWeight.Bold
        )
        Text(formatEuro(remaining), color = Color.White, fontSize = 24.sp)
        Text("Restant", color = Color.LightGray, fontSize = 14.sp)
    }
}