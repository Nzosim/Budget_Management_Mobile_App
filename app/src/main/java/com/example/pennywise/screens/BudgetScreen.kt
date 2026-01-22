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
import com.example.pennywise.components.budgetScreen.IncomeBody
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

    var displayed by remember { mutableStateOf("EXPENSE") }
    var month by remember { mutableStateOf(LocalDate.now()) }

    var refreshTrigger by remember { mutableStateOf(0) }

    val context = LocalContext.current
    val prefs = context.getSharedPreferences("budget_storage", Context.MODE_PRIVATE)

    val incomeCategories = listOf<Category>(
        Category(1000, "Salaire", 0.0, Color(0xFF4CAF50)),
        Category(1001, "Investissements", 0.0, Color(0xFF2196F3)),
        Category(1004, "Aides", 0.0, Color(0xFF9C27B0)),
        Category(1003, "Cadeaux", 0.0, Color(0xFFFF5722)),
        Category(1002, "Autres revenus", 0.0, Color(0xFFFFC107))
    )

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
    var incomeList = remember { mutableStateListOf<Expense>() }
    var totalIncome by remember { mutableStateOf(0.0) }

    totalIncome = 0.0
    for (income in incomeList) {
        totalIncome += income.amount
    }

    expenseList.clear()
    incomeList.clear()
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
        if(type == "revenu") {
            incomeList.add(Expense(label, montant, date, type, categoryId, categoryColor))
            continue
        }else if(type == "depense") {
            expenseList.add(Expense(label, montant, date, type, categoryId, categoryColor))
        }
    }

    Box(modifier = Modifier.fillMaxSize()) { // Box pour superposition
        LazyColumn() {
            item {
                BudgetHeader(
                    totalIncome,
                    month,
                    onPrevious = {
                        month = month.minusMonths(1)
                    },
                    onNext = {
                        if (month.isBefore(LocalDate.now())) {
                            month = month.plusMonths(1)
                        }
                    },
                    displayed,
                    onClick = { value ->
                        displayed = value
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
                if(displayed == "EXPENSE") {
                    BudgetBody(month, spend, expenseList)
                }else {
                    IncomeBody(month, totalIncome, incomeList)
                }
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
                            if(displayed == "EXPENSE") categories else incomeCategories,
                            displayed
                        )
                    }
                }
            }

            // Récupération et affichage des colonnes
            items(if(displayed == "EXPENSE") categories else incomeCategories) { category ->
                CategoryExpense(
                    onClick = { /* TODO */ },
                    id = category.id,
                    label = category.label,
                    amount = category.amount,
                    icon = null,
                    color = category.color,
                    displayed,
                    if(displayed == "EXPENSE")  expenseList else incomeList
                )
            }
        }
        PlusButton(
            onClick = { showBottomSheet = true },
            modifier = Modifier.align(Alignment.BottomEnd)
        )
    }
}
