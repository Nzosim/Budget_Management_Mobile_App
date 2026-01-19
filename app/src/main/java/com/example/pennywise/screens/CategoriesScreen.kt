package com.example.pennywise.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.pennywise.components.NavBar
import com.example.pennywise.components.PlusButton

@Composable
fun CategoriesScreen(navController: NavController) {
    Text("Test")
    Column() {
        PlusButton(onClick = { /* TODO: Handle click */ })
    }
}