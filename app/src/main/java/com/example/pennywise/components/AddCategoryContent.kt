package com.example.pennywise.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pennywise.utils.ColorHuePicker

@Composable
fun AddCategoryContent(onClose: () -> Unit) {
    var selectedColor by remember { mutableStateOf(Color(0xFF282828)) }
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Ajouter une catégorie",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .padding(bottom = 24.dp)
        )
        OutlinedTextField(
            value = "",
            onValueChange = {},
            label = { Text("Nom", style = MaterialTheme.typography.labelMedium) },
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )
        OutlinedTextField(
            value = "",
            onValueChange = {},
            label = { Text("Montant", style = MaterialTheme.typography.labelMedium) },
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            trailingIcon = {
                Text(
                    text = "€ / mois",
                    modifier = Modifier.padding(end=8.dp),
                    style = MaterialTheme.typography.labelMedium
                )
            }
        )
        ColorHuePicker { color ->
            selectedColor = color
        }
        Button(
            onClick = onClose,
            modifier = Modifier
                .padding(top = 24.dp)
                .size(width = 200.dp, height = 50.dp),
            shape = RoundedCornerShape(25.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = selectedColor,
                contentColor = Color.White // Texte toujours blanc
            )

        ) {
            Text(
                text = "Ajouter",
                fontSize = 16.sp,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}