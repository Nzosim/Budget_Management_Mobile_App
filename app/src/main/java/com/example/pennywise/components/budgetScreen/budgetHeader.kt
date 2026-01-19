package com.example.pennywise.components.budgetScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pennywise.utils.formatEuro
import java.util.Date
import com.example.pennywise.utils.formatDate
import java.time.LocalDate

@Composable
fun BudgetHeader(remaining: Double, date: LocalDate) {
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

        MonthSelector(date)
    }
}

@Composable
fun MonthSelector(date: LocalDate) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(Icons.Default.ArrowBack, null, tint = Color.White)
        Spacer(Modifier.width(16.dp))
        Text(formatDate(date), color = Color.White, fontSize = 18.sp)
        Spacer(Modifier.width(16.dp))
        Icon(Icons.Default.ArrowForward, null, tint = Color.White)
    }
}