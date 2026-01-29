package com.example.pennywise.components

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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pennywise.utils.ColorHuePicker
import org.json.JSONArray
import org.json.JSONObject

@OptIn(ExperimentalMaterial3Api::class)
@Suppress("UNUSED_PARAMETER", "NewApi")
@Composable
fun AddCategoryContent(onClose: () -> Unit) {
    var selectedColor by remember { mutableStateOf(Color(0xFF282828)) }
    val context = LocalContext.current
    var categoryLabel by remember { mutableStateOf("") }
    var categoryAmount by remember { mutableStateOf("") }
    var categoryId by remember { mutableStateOf(0) }
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Ajouter une catégorie",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .padding(bottom = 24.dp)
        )
        OutlinedTextField(
            value = categoryLabel,
            onValueChange = { categoryLabel = it},
            label = { Text("Nom", style = MaterialTheme.typography.labelMedium) },
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )
        OutlinedTextField(
            value = categoryAmount,
            onValueChange = { categoryAmount = it},
            label = { Text("Montant", style = MaterialTheme.typography.labelMedium) },
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            trailingIcon = {
                Text(
                    text = "€ / mois",
                    modifier = Modifier.padding(end=8.dp),
                    style = MaterialTheme.typography.labelMedium
                )
            }
        )
        ColorHuePicker { color ->
            selectedColor = color
        }
        Button(
            onClick = {
                val prefs = context.getSharedPreferences("budget_storage1", MODE_PRIVATE)

                val jsonString = prefs.getString("categories5", "[]") ?: "[]"
                Log.d("test", jsonString.toString())

                val jsonArray1 = JSONArray(jsonString)
                val categoryId = jsonArray1.length()

                val newCategory = JSONObject()
                newCategory.put("id", categoryId)
                newCategory.put("categoryLabel", categoryLabel.ifBlank { "Sans nom" })
                val amountValue = categoryAmount.replace(',', '.').toDoubleOrNull() ?: 0.0
                newCategory.put("amount", amountValue)
                newCategory.put("color", selectedColor.value.toLong())
                val jsonArray = JSONArray(jsonString)
                jsonArray.put(newCategory)

                prefs.edit()
                    .putString("categories5", jsonArray.toString())
                    .apply()

                categoryLabel = ""
                categoryAmount = ""
                Toast.makeText(context, "Catégorie créée !", Toast.LENGTH_SHORT).show()
                onClose()
            },
            modifier = Modifier
                .padding(top = 24.dp)
                .size(width = 200.dp, height = 50.dp),
            shape = RoundedCornerShape(25.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = selectedColor,
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