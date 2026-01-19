package com.example.pennywise.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.pennywise.utils.formatEuro

@Composable
fun CategoryCard(
    onClick: () -> Unit,
    label: String,
    amount: Double,
    icon: Int?,
    color: String?
){
    Card(
        modifier = Modifier
            .clickable { onClick() }
            .fillMaxWidth()
            .size(120.dp)
            .fillMaxSize()
            .padding(top = 32.dp, bottom = 32.dp, start = 16.dp, end = 16.dp)
    ) {
        Row () {
            Column() {
                Text(label)
            }
            Column() {
                val formattedAmount = formatEuro(amount)
                Text(formattedAmount)
            }
        }
    }
}