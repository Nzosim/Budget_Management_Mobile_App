package com.example.pennywise.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color // IMPORT IMPORTANT
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun ColorHuePicker(
    onColorSelected: (Color) -> Unit
) {
    // Création d'un dégradé arc-en-ciel avec les couleurs Compose
    val gradient = Brush.horizontalGradient(
        colors = listOf(
            Color.Red, Color.Yellow, Color.Green,
            Color.Cyan, Color.Blue, Color.Magenta, Color.Red
        )
    )

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Choisir une teinte", style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(8.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(gradient)
                .pointerInput(Unit) {
                    detectTapGestures { offset ->
                        // Calcul de la teinte entre 0 et 360
                        val hue = (offset.x / size.width) * 360f
                        // Utilisation de la fonction hsv de Compose
                        onColorSelected(Color.hsv(hue = hue, saturation = 0.8f, value = 0.9f))
                    }
                }
        )
    }
}