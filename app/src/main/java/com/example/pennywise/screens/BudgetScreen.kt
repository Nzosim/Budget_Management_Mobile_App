package com.example.pennywise.screens

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.pennywise.components.PlusButton
import com.example.pennywise.components.budgetScreen.AddExpenseIncomeContent
import com.example.pennywise.components.budgetScreen.BudgetBody
import com.example.pennywise.components.budgetScreen.BudgetHeader
import org.json.JSONArray
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Suppress("UNUSED_PARAMETER", "NewApi") // navController est fourni par le NavHost mais pas encore utilisé ici
@Composable
fun BudgetScreen(navController: NavController) {
    var showBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    val remaining by remember { mutableStateOf(12500.0) }
    val spend by remember { mutableStateOf(2500.0) }
    var month by remember { mutableStateOf(LocalDate.now()) }

    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        BudgetHeader(remaining, month,
            onPrevious = {
                month = month.minusMonths(1)
            },
            onNext = {
                if (month.isBefore(LocalDate.now())) {
                    month = month.plusMonths(1)
                }
            }
        )

        BudgetBody(month, spend)

        PlusButton(onClick = { showBottomSheet = true })

        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = { showBottomSheet = false },
                sheetState = sheetState,
                scrimColor = Color.Black.copy(alpha = 0.6f),
                containerColor = MaterialTheme.colorScheme.background,
                modifier = Modifier.fillMaxWidth()
            ) {
                AddExpenseIncomeContent(
                    onClose = { showBottomSheet = false },
                    month
                )
            }
        }

        // Récupération des datas
        val context = LocalContext.current
        val prefs = context.getSharedPreferences(
            "budget_storage",
            Context.MODE_PRIVATE
        )

        var currentMonth = month.toString().split("-")
        val jsonString = prefs.getString("transactions_" + currentMonth[0] + "_" + currentMonth[1], "[]")
        val jsonArray = JSONArray(jsonString)

        for (i in 0 until jsonArray.length()) {
            val item = jsonArray.getJSONObject(i)

            val label = if (item.has("label")) item.getString("label") else item.optString("nom", "Sans nom")
            val montant = if (item.has("montant")) item.optDouble("montant", 0.0) else item.optInt("montant", 0)
            val date = item.optString("date", "?")
            val type = item.optString("type", "?")

            var monthAndYear = date.toString().split("-")
            if(monthAndYear[0] != currentMonth[0] || monthAndYear[1] != currentMonth[1]) continue
            Log.d("ledjo", "$date - $label : $montant € ($type)\n")
            Text("$date - $label : $montant € ($type)\n")
        }
    }
}
