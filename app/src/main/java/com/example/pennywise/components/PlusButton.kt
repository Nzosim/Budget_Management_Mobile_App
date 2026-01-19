package com.example.pennywise.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun PlusButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    padding: Dp = 16.dp,
    size: Dp = 46.dp,
    contentDescription: String? = "Ajouter"
) {
    Box(modifier = modifier.fillMaxSize()) {
        FloatingActionButton(
            onClick = onClick,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(padding)
                .size(size),
            shape = CircleShape,
            containerColor = Color.White,
            contentColor = Color.Black
        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = contentDescription,
                modifier = Modifier.size(size * 0.6f),
                tint = Color.Black
            )
        }
    }
}