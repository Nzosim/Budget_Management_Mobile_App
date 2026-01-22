package com.example.pennywise.components


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.pennywise.utils.formatEuro

@Composable
fun ExpenseCard(
    onClick: () -> Unit,
    label: String,
    amount: Double,
    date: String,
    type: String,
    categoryId: Int,
    categoryColor: Color
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .padding(horizontal = 16.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF4B4B4B)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .drawWithContent {
                    drawContent()
                    drawRect(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                Color(0xFF4B4B4B).copy(alpha = 0.25f),
                                Color.Transparent
                            ),
//                            radius = size.minDimension * 0.8f
                            radius = size.width * 0.35f

                        ),
                        blendMode = BlendMode.Screen
                    )
                }
                .padding(24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White
            )
            Text(
                text = " • $date",
                style = MaterialTheme.typography.labelSmall,
                color = Color.LightGray,
                modifier = Modifier.padding(start = 8.dp).weight(1f),
            )
            Text(
                text = formatEuro(amount),
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White
            )
        }
    }
}