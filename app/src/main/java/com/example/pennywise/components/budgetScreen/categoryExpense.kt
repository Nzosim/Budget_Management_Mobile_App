package com.example.pennywise.components.budgetScreen

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pennywise.model.Expense
import com.example.pennywise.utils.formatEuro

@Composable
fun CategoryExpense(
    onClick: () -> Unit,
    id: Int,
    label: String,
    amount: Double,
    icon: Int?,
    color: Color = Color(0xFFDC4D00),
    displayed: String,
    expenseList : List<Expense>,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp) // Espacement entre les cartes
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = color)
    ) {
        Column {
            var totalExpenses = 0.0
            for (expense in expenseList) {
                if (expense.categoryId == id) totalExpenses += expense.amount
            }
            var consumedRatio = if (amount > 0) (totalExpenses / amount).toFloat().coerceIn(0f, 1f) else 0f
            if(amount < totalExpenses) consumedRatio = 2.2f
            var remainingPercent = ((1f - consumedRatio) * 100).toInt()
            if(remainingPercent < 0) {
                remainingPercent = 0 - ((totalExpenses - amount) / amount * 100).toInt()
            }
            val displayedText: String = if(remainingPercent < 0) {
                "dépassé de " + (-remainingPercent).toString() + "%"
            } else {
                remainingPercent.toString() + "% restant"
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp), // C'est ce padding qui donne la "taille" et l'épaisseur à la box
                horizontalArrangement = Arrangement.SpaceBetween, // Pousse les éléments aux bords
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Nom
                Text(
                    text = label,
                    style = MaterialTheme.typography.bodyLarge
                )

                // Prix
                Text(
                    text = formatEuro(totalExpenses),
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            if(displayed == "EXPENSE") {
                CategoryProgressBar(
                    progress = consumedRatio
                )

                Text(displayedText,
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .align(Alignment.End),
                    style = MaterialTheme.typography.labelMedium,
                    fontSize = 14.sp,
                )
            }
        }
    }
}

@Composable
fun CategoryProgressBar(
    progress: Float, // entre 0f et 1f sauf si dépassé
) {
    val backgroundColor: Color = when {
        progress < 0.5f -> Color(0xFF4CAF50) // Vert
        progress < 0.8f -> Color(0xFFFFC107) // Jaune
        else -> Color(0xFFAD0F00) // Rouge
    }

    val infiniteTransition = rememberInfiniteTransition(label = "")
    val blinkingColor by infiniteTransition.animateColor(
        initialValue = backgroundColor.copy(alpha = 0.3f),
        targetValue = backgroundColor,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 500,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Reverse
        ),
        label = ""
    )

    Box(
        modifier = Modifier
            .height(8.dp)
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(50))
            .background(if(progress > 2f) Color.Red else Color.White)
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(progress)
                .clip(RoundedCornerShape(50))
                .background(
                    if (progress > 0.5f) blinkingColor else backgroundColor
                )
        )
    }
}