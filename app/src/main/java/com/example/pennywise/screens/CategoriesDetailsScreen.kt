package com.example.pennywise.screens

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.pennywise.components.AddCategoryContent
import com.example.pennywise.components.CategoryCard
import com.example.pennywise.components.EditCategoryContent
import com.example.pennywise.components.ExpenseCard
import org.json.JSONArray

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoriesDetailsScreen(navController: NavController, categoryId: Int?) {
    val context = LocalContext.current
    val prefs = context.getSharedPreferences("budget_storage", Context.MODE_PRIVATE)
    val jsonString = prefs.getString("categories5", "[]") ?: "[]"
    val jsonArray = JSONArray(jsonString)
    var item: org.json.JSONObject? = null
    for (i in 0 until jsonArray.length()) {
        val obj = jsonArray.getJSONObject(i)
        if (obj.optInt("id") == categoryId) {
            item = obj
            break
        }
    }
    if (item == null) {
        androidx.compose.runtime.LaunchedEffect(Unit) {
            navController.popBackStack()
        }
        return
    }
    val label = item.optString("categoryLabel", "Sans nom")
    val amount = item.optDouble("amount", 0.0)
    val colorLong = item.optLong("color", Color.Gray.value.toLong())
    val color = Color(colorLong.toULong())
    var showBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    Column(
        modifier = Modifier
            .fillMaxSize(),
//            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = label,
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White
                )
            },
            navigationIcon = {
                IconButton(
                    onClick = { navController.popBackStack() }
                ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBackIosNew,
                        contentDescription = Icons.Filled.ArrowBackIosNew.name,
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            },
            actions = {
                IconButton(
                    onClick = {  showBottomSheet = !showBottomSheet }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Edit,
                        contentDescription = Icons.Filled.Edit.name,
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            },
            colors = topAppBarColors(
                containerColor = MaterialTheme.colorScheme.background
            )
        )
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            CategoryCard(
                onClick = {},
                label = label,
                amount = amount,
                color = color,
                icon = null,
            )
            HorizontalDivider(
                thickness = 2.dp,
                modifier = Modifier.padding(8.dp)
            )
            ExpenseCard(
                label = "KFC",
                amount = 11.9,
                date = "2026-01-20",
                type = "depense",
                categoryId = categoryId ?: 0,
                categoryColor = color,
                onClick = {}
            )
            ExpenseCard(
                label = "McDo",
                amount = 10.0,
                date = "2026-01-21",
                type = "depense",
                categoryId = categoryId ?: 0,
                categoryColor = color,
                onClick = {}
            )
        }


        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = { showBottomSheet = false },
                sheetState = sheetState,
                scrimColor = Color.Black.copy(alpha = 0.6f),
                containerColor = MaterialTheme.colorScheme.background,
                modifier = Modifier.fillMaxWidth()
            ) {
                EditCategoryContent (
                    categoryId = categoryId ?: 0,
                    onClose = {showBottomSheet = false},
                    color = color ?: Color.Gray,
                )
            }
        }

    }

}