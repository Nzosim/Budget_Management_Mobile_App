package com.example.pennywise.components.budgetScreen

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pennywise.model.Expense
import com.example.pennywise.utils.formatEuro
import java.time.LocalDate

data class Categories(val value: Double, val color: Color)

@Composable
fun BudgetBody(date: LocalDate, spend: Double, expenseList: List<Expense>) {
//    val segments = listOf(
//        Categories(60.0, Color.Blue),
//        Categories(30.0, Color.Red),
//        Categories(10.0, Color.Green),
//    )

    val segments = expenseList
        .filter { it.categoryId != -1 }
        .map {
            Categories(
                value = it.amount,
                color = it.categoryColor
            )
        }
    Log.d("esfes", segments.toString())

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Box(
            modifier = Modifier
                .height(220.dp)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            DonutChart(segments = segments, modifier = Modifier.size(180.dp))
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("DÉPENSES", color = Color.White)
                Text(
                    formatEuro(spend),
                    color = Color.White,
                    fontSize = 25.sp
                )
            }
        }
    }
}

@Composable
fun DonutChart(
    segments: List<Categories>,
    modifier: Modifier = Modifier,
) {
    Canvas(modifier = modifier) {
        drawArc(
            color = Color.White.copy(alpha = 0.12f),
            startAngle = 0f,
            sweepAngle = 360f,
            useCenter = false,
            style = Stroke(width = 32f)
        )

        var total = 0.0
        for (segment in segments) {
            total += segment.value
        }
        if (total <= 0.0) total = 1.0
        var startAngle = -90f

        for (index in segments) {
            val sweep = (index.value / total).toFloat() * 360f
            drawArc(
                color = index.color,
                startAngle = startAngle,
                sweepAngle = sweep,
                useCenter = false,
                style = Stroke(width = 50f, cap = StrokeCap.Round)
            )
            startAngle += sweep
        }
    }
}