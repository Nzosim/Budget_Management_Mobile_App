package com.example.pennywise.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
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
import androidx.navigation.NavController
import com.example.pennywise.components.AddCategoryContent
import com.example.pennywise.components.CategoryCard
import com.example.pennywise.components.NavBar
import com.example.pennywise.components.PlusButton
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.ui.platform.LocalContext
import android.content.Context
import android.util.Log
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.platform.LocalContext
import org.json.JSONArray
import org.json.JSONObject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoriesScreen(navController: NavController) {
    var showBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    // Exemple de données
//    val categories = listOf(
//        Triple("Alimentation", 250.0, Color(0xFFDC4D00)),
//        Triple("Crédits", 800.0, Color(0xFF7200EF)),
//        Triple("Loisirs", 150.0, Color(0xFF00BFA5)),
//        )

    val context = LocalContext.current
    val prefs = context.getSharedPreferences("budget_storage", Context.MODE_PRIVATE)

    val categories = remember {
        val list = mutableStateListOf<Triple<String, Double, Color>>()
        val jsonString = prefs.getString("categories5", "[]") ?: "[]"
        val jsonArray = JSONArray(jsonString)
        for (i in 0 until jsonArray.length()) {
            val item = jsonArray.getJSONObject(i)
            val label = item.optString("categoryLabel", "Sans nom")
            val amount = item.optDouble("amount", 0.0)
            val colorLong = item.optLong("color", Color.Gray.value.toLong())
            val color = Color(colorLong.toULong())
            list.add(Triple(label, amount, color))
        }
        list
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Catégories",
            fontWeight = FontWeight.Bold,
            color = Color.White,
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(categories) { category ->
                CategoryCard(
                    onClick = { /* TODO */ },
                    label = category.first,
                    amount = category.second,
                    icon = null,
                    color = category.third
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        PlusButton(onClick = { showBottomSheet = true })
    }

    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet = false },
            sheetState = sheetState,
            scrimColor = Color.Black.copy(alpha = 0.6f),
            containerColor = MaterialTheme.colorScheme.background,
            modifier = Modifier.fillMaxWidth()
        ) {
            AddCategoryContent(
                onClose = {
                    showBottomSheet = false

                    categories.clear()
                    val jsonString = prefs.getString("categories5", "[]") ?: "[]"
                    val jsonArray = JSONArray(jsonString)
                    for (i in 0 until jsonArray.length()) {
                        val item = jsonArray.getJSONObject(i)
                        val label = item.optString("categoryLabel", "Sans nom")
                        val amount = item.optDouble("amount", 0.0)
                        val colorLong = item.optLong("color", Color.Gray.value.toLong())
                        val color = Color(colorLong.toULong())
                        categories.add(Triple(label, amount, color))                    }
                }
            )
        }
    }


}
