package com.example.pennywise.screens

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.pennywise.components.PlusButton
import com.example.pennywise.components.budgetScreen.AddExpenseIncomeContent
import com.example.pennywise.components.budgetScreen.BudgetBody
import com.example.pennywise.components.budgetScreen.BudgetHeader
import com.example.pennywise.components.budgetScreen.CategoryExpense
import com.example.pennywise.model.Category
import com.example.pennywise.model.Expense
import org.json.JSONArray
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Suppress("UNUSED_PARAMETER", "NewApi")
@Composable
fun BudgetScreen(navController: NavController) {
    var showBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    val remaining by remember { mutableStateOf(12500.0) }
    val spend by remember { mutableStateOf(2500.0) }
    var month by remember { mutableStateOf(LocalDate.now()) }

    var refreshTrigger by remember { mutableStateOf(0) }

    val context = LocalContext.current
    val prefs = context.getSharedPreferences("budget_storage", Context.MODE_PRIVATE)

    val categories = remember {
        val list = mutableStateListOf<Category>()
        val jsonString = prefs.getString("categories5", "[]") ?: "[]"
        val jsonArray = JSONArray(jsonString)
        for (i in 0 until jsonArray.length()) {
            val item = jsonArray.getJSONObject(i)
            val id = item.optInt("id", 0)
            val label = item.optString("categoryLabel", "Sans nom")
            val amount = item.optDouble("amount", 0.0)
            val colorLong = item.optLong("color", Color.Gray.value.toLong())
            val color = Color(colorLong.toULong())
            list.add(Category(id, label, amount, color))
        }
        list
    }

    var expenseList = remember { mutableStateListOf<Expense>() }
    expenseList.clear()
    // Récupération des dépenses
    var currentMonth = month.toString().split("-")
    val jsonString = prefs.getString("transactions2_" + currentMonth[0] + "_" + currentMonth[1], "[]")
    val jsonArray = JSONArray(jsonString)

    for (i in 0 until jsonArray.length()) {
        val item = jsonArray.getJSONObject(i)

        val label = if (item.has("label")) item.getString("label") else item.optString("nom", "Sans nom")
        val montant = if (item.has("montant")) item.getDouble("montant") else item.optDouble("amount", 0.0)
        val date = item.optString("date", "?")
        val type = item.optString("type", "?")
        val categoryId = item.optInt("categoryId", -1)
        val categoryColor =
            if (item.has("categoryColor")) {
                val colorLong = item.getLong("categoryColor")
                Color(colorLong.toULong())
            } else {
                Color.Gray
            }
        var monthAndYear = date.toString().split("-")
        if(monthAndYear[0] != currentMonth[0] || monthAndYear[1] != currentMonth[1]) continue
        Log.d("ledjo", "$date - $label : $montant € ($type)\n")
        expenseList.add(Expense(label, montant, date, type, categoryId, categoryColor))
    }

    Box(modifier = Modifier.fillMaxSize()) { // Box pour superposition
        LazyColumn() {
            item {
                BudgetHeader(
                    remaining, month,
                    onPrevious = {
                        month = month.minusMonths(1)
                    },
                    onNext = {
                        if (month.isBefore(LocalDate.now())) {
                            month = month.plusMonths(1)
                        }
                    }
                )
            }

            item {
                var spend: Double = 0.0
                for (expense in expenseList) {
                    if (expense.type == "depense" && expense.categoryId != -1) {
                        spend += expense.amount
                    }
                }
                BudgetBody(month, spend, expenseList)
            }

            item {
                if (showBottomSheet) {
                    ModalBottomSheet(
                        onDismissRequest = { showBottomSheet = false },
                        sheetState = sheetState,
                        scrimColor = Color.Black.copy(alpha = 0.6f),
                        containerColor = MaterialTheme.colorScheme.background,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        AddExpenseIncomeContent(
                            onClose = { showBottomSheet = false; refreshTrigger++ },
                            month,
                            categories
                        )
                    }
                }
            }

            // Récupération et affichage des colonnes
            items(categories) { category ->
                CategoryExpense(
                    onClick = { /* TODO */ },
                    id = category.id,
                    label = category.label,
                    amount = category.amount,
                    icon = null,
                    color = category.color,
                    expenseList
                )
            }
        }
        PlusButton(
            onClick = { showBottomSheet = true },
            modifier = Modifier.align(Alignment.BottomEnd)
        )
    }
}
