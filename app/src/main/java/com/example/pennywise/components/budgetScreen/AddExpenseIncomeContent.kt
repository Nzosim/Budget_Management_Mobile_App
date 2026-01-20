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
import androidx.compose.material3.ExperimentalMaterial3Api
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
import org.json.JSONArray
import org.json.JSONObject
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Suppress("UNUSED_PARAMETER", "NewApi")
@Composable
fun AddExpenseIncomeContent(onClose: () -> Unit, date: LocalDate) {
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
            text = "Ajouter une dépense",
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

        Button(
            onClick = {
                val prefs = context.getSharedPreferences("budget_storage", MODE_PRIVATE)

                var currentMonth = date.toString().split("-")
                val jsonString = prefs.getString("transactions_" + currentMonth[0] + "_" + currentMonth[1], "[]") ?: "[]"
                Log.d("test", jsonString.toString())

                val nouvelleDepense = JSONObject()
                nouvelleDepense.put("label", nom.ifBlank { "Sans nom" })
                val montantValue = montantText.replace(',', '.').toDoubleOrNull() ?: 0.0
                nouvelleDepense.put("montant", montantValue)
                nouvelleDepense.put("date", date.toString())
                nouvelleDepense.put("type", "depense")

                val jsonArray = JSONArray(jsonString)
                jsonArray.put(nouvelleDepense)

                if(montantValue <= 0.0) {
                    Toast.makeText(context, "Vous ne pouvez pas ajouter un montant vide", Toast.LENGTH_SHORT).show()
                }else {
                    Toast.makeText(context, "Dépense ajoutée", Toast.LENGTH_SHORT).show()
                    prefs.edit()
                        .putString("transactions_" + currentMonth[0] + "_" + currentMonth[1], jsonArray.toString())
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