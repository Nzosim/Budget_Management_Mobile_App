package com.example.pennywise.screens

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.json.JSONArray

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoriesDetailsScreen(navController: NavController, categoryId: Int?) {
    val context = LocalContext.current
    val prefs = context.getSharedPreferences("budget_storage", Context.MODE_PRIVATE)
    val jsonString = prefs.getString("categories5", "[]") ?: "[]"
    val jsonArray = JSONArray(jsonString)
    val item = jsonArray.getJSONObject(categoryId ?: 0)
    val label = item.optString("categoryLabel", "Sans nom")
    val amount = item.optDouble("amount", 0.0)
    val colorLong = item.optLong("color", Color.Gray.value.toLong())
    val color = Color(colorLong.toULong())
    Text("$label")
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            //Bouton retour

        }
    }

}