package com.example.pennywise.components.budgetScreen

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.pennywise.utils.formatEuro

@Composable
fun CategoryExpense(
    onClick: () -> Unit,
    label: String,
    amount: Double,
    icon: Int?,
    color: Color = Color(0xFFDC4D00)
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp) // Espacement entre les cartes
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = color)
    ) {
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
            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = formatEuro(amount),
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = "/ mois",
                    style = MaterialTheme.typography.labelMedium
                )
            }
        }
    }
}