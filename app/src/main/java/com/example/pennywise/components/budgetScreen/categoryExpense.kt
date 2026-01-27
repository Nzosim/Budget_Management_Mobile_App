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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
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
            .clickable { onClick() }
            .drawWithContent {
                drawContent()

                drawRect(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Color(0xFF4B4B4B).copy(alpha = 0.8f),
                            Color.Transparent
                        ),
                        radius = size.width * 0.35f

                    ),
                    blendMode = BlendMode.Screen
                )
            },
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

                Row(modifier = Modifier.align(Alignment.End)) {
                    Text(
                        displayedText,
                        modifier = Modifier
                            .padding(top = 15.dp),
                        style = MaterialTheme.typography.labelMedium,
                        fontSize = 14.sp,
                    )

                    RichTooltipExample(
                        progress = consumedRatio,
                        budgetAmount = amount
                    )
                }
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

// Tooltip documentation Jetpack Compose
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RichTooltipExample(
    progress: Float,
    budgetAmount: Double
) {
    val expanded = remember { mutableStateOf(false) }

    val percent = (progress * 100).toInt()
    val formattedBudget = formatEuro(budgetAmount)
    val (title, advice) = when {
        progress < 0.5f -> {
            "Ça va — $percent% utilisé" to "Budget mensuel : $formattedBudget. Bonne gestion pour l'instant. Continuez à suivre vos dépenses et mettez de côté automatiquement si possible."
        }
        progress < 0.8f -> {
            "Attention — $percent% utilisé" to "Budget mensuel : $formattedBudget. Vous approchez du plafond. Réduisez les dépenses non essentielles et suivez vos transactions cette semaine."
        }
        progress < 1f -> {
            "Attention critique — $percent% utilisé" to "Budget mensuel : $formattedBudget. Presque au budget. Reportez les achats importants et vérifiez les catégories où vous pouvez réallouer du budget."
        }
        else -> {
            // dépassement
            val overPercent = ((progress - 1f) * 100).toInt()
            "Dépassement " to "Budget mensuel : $formattedBudget. Envisagez de réduire postes variables, ajustez le budget pour ce mois et planifiez un rattrapage."
        }
    }

    IconButton(onClick = { expanded.value = true }) {
        Icon(
            imageVector = Icons.Filled.Info,
            contentDescription = "Plus d'infos"
        )
    }

    DropdownMenu(
        expanded = expanded.value,
        onDismissRequest = { expanded.value = false }
    ) {
        DropdownMenuItem(
            text = {
                Column {
                    Text(text = title, style = MaterialTheme.typography.titleSmall)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = advice, style = MaterialTheme.typography.bodySmall)
                }
            },
            onClick = {}
        )
    }
}