package com.example.pennywise.components.budgetScreen

import android.content.Context.MODE_PRIVATE
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pennywise.model.Category
import org.json.JSONArray
import org.json.JSONObject
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Suppress("UNUSED_PARAMETER", "NewApi")
@Composable
fun AddExpenseIncomeContent(
    onClose: () -> Unit,
    date: LocalDate,
    categories: List<Category>,
    displayed: String
) {
    val context = LocalContext.current
    var nom by remember { mutableStateOf("") }
    var montantText by remember { mutableStateOf("") }

    Column (
        modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Ajouter " + if(displayed == "EXPENSE") "une dépense" else "un revenu",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier
                .padding(bottom = 24.dp)
        )
        OutlinedTextField(
            value = nom,
            onValueChange = { nom = it },
            label = { Text("Nom") },
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )

        val datePickerState = rememberDatePickerState(
            initialDisplayMode = DisplayMode.Input // Force le mode texte
        )

        OutlinedTextField(
            value = montantText,
            onValueChange = { montantText = it },
            label = { Text("Montant") },
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            trailingIcon = {
                Text(
                    text = "€",
                    modifier = Modifier.padding(end=8.dp)
                )
            }
        )

        var onCategorySelected: Category by remember {
            mutableStateOf(Category(-1, "Sans catégorie", 0.0, Color.Gray))
        }
        CategorySelector(
            categories = categories,
            onCategorySelected = { category -> onCategorySelected = category }
        )

        Log.d("t232est", onCategorySelected.id.toString())

        Button(
            onClick = {
                val prefs = context.getSharedPreferences("budget_storage", MODE_PRIVATE)

                var currentMonth = date.toString().split("-")
                val jsonString = prefs.getString("transactions2_" + currentMonth[0] + "_" + currentMonth[1], "[]") ?: "[]"
                Log.d("test", jsonString.toString())

                val nouvelleDepense = JSONObject()
                nouvelleDepense.put("label", nom.ifBlank { "Sans nom" })
                val montantValue = montantText.replace(',', '.').toDoubleOrNull() ?: 0.0
                nouvelleDepense.put("montant", montantValue)
                nouvelleDepense.put("date", date.toString())
                nouvelleDepense.put("type", "depense")
                nouvelleDepense.put("categoryId", onCategorySelected.id)
                nouvelleDepense.put("categoryColor", onCategorySelected.color.value.toLong())

                val jsonArray = JSONArray(jsonString)
                jsonArray.put(nouvelleDepense)

                if(montantValue <= 0.0 || onCategorySelected.id == -1) {
                    Toast.makeText(context, "Vous ne pouvez pas ajouter un montant ou une catégorie vide", Toast.LENGTH_SHORT).show()
                }else {
                    Toast.makeText(context, "Dépense ajoutée", Toast.LENGTH_SHORT).show()
                    prefs.edit()
                        .putString("transactions2_" + currentMonth[0] + "_" + currentMonth[1], jsonArray.toString())
                        .apply()
                }

                nom = ""
                montantText = ""

                onClose()
            },
            modifier = Modifier
                .padding(top = 24.dp)
                .size(width = 200.dp, height = 50.dp),
            shape = RoundedCornerShape(25.dp),
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.White // Texte toujours blanc
            )
        ) {
            Text(
                text = "Ajouter",
                fontSize = 16.sp,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategorySelector(
    categories: List<Category>,
    onCategorySelected: (Category) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedCategory by remember { mutableStateOf<Category?>(null) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {

        OutlinedTextField(
            value = selectedCategory?.label ?: "",
            onValueChange = {},
            readOnly = true,
            shape = RoundedCornerShape(12.dp),
            label = { Text("Catégorie") },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            modifier = Modifier
                .menuAnchor() // Menu déroulant
                .padding(top = 10.dp)
                .fillMaxWidth()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            categories.forEach { category ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = category.label
                        )
                    },
                    onClick = {
                        selectedCategory = category
                        expanded = false
                        onCategorySelected(category)
                    }
                )
            }
        }
    }
}