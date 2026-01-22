package com.example.pennywise.components

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pennywise.utils.ColorHuePicker
import org.json.JSONArray
import org.json.JSONObject
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditCategoryContent(
    categoryId: Int,
    onClose: () -> Unit,
    color: Color
) {
    val context = LocalContext.current
    val prefs = remember { context.getSharedPreferences("budget_storage", MODE_PRIVATE) }

    var categoryLabel by remember { mutableStateOf("") }
    var categoryAmount by remember { mutableStateOf("") }
    var selectedColor by remember { mutableStateOf(Color(0xFF282828)) }

    // Chargement des données existantes
    LaunchedEffect(categoryId) {
        val jsonString = prefs.getString("categories5", "[]") ?: "[]"
        val jsonArray = JSONArray(jsonString)

        for (i in 0 until jsonArray.length()) {
            val obj = jsonArray.getJSONObject(i)
            if (obj.getInt("id") == categoryId) {
                categoryLabel = obj.getString("categoryLabel")
                categoryAmount = obj.getDouble("amount").toString()
                selectedColor = Color(obj.getLong("color"))
                break
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Modifier $categoryLabel",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        OutlinedTextField(
            value = categoryLabel,
            onValueChange = { categoryLabel = it },
            label = { Text("Nom") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )

        OutlinedTextField(
            value = categoryAmount,
            onValueChange = { categoryAmount = it },
            label = { Text("Montant") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            trailingIcon = {
                Text(
                    "€ / mois",
                    modifier = Modifier.padding(end=8.dp),
                    style = MaterialTheme.typography.labelMedium
                )
            }
        )

        // Bouton modifier
        Button(
            onClick = {
                val jsonString = prefs.getString("categories5", "[]") ?: "[]"
                val jsonArray = JSONArray(jsonString)

                for (i in 0 until jsonArray.length()) {
                    val obj = jsonArray.getJSONObject(i)
                    if (obj.getInt("id") == categoryId) {
                        obj.put("categoryLabel", categoryLabel.ifBlank { "Sans nom" })
                        obj.put("amount", categoryAmount.replace(',', '.').toDoubleOrNull() ?: 0.0)
                        obj.put("color", color.value.toLong())
                    }
                }

                prefs.edit().putString("categories5", jsonArray.toString()).apply()
                onClose()
            },
            modifier = Modifier.padding(top = 24.dp).size(200.dp, 50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Text("Enregistrer")
        }

        // Bouton supprimer
        IconButton(
            onClick = {
                try {
                    val jsonString = prefs.getString("categories5", "[]") ?: "[]"
                    val oldArray = JSONArray(jsonString)
                    val newArray = JSONArray()

                    for (i in 0 until oldArray.length()) {
                        val obj = oldArray.getJSONObject(i)
                        // On ne garde que ceux qui n'ont pas l'ID actuel
                        if (obj.getInt("id") != categoryId) {
                            newArray.put(obj)
                        }
                    }

                    prefs.edit().putString("categories5", newArray.toString()).apply()

                    onClose()
                } catch (e: Exception) {
                    Log.e("EditCategory", "Erreur lors de la suppression", e)
                }
            }
        ) {
            Icon(Icons.Default.Delete, contentDescription = "Supprimer", tint = Color.Red)
        }
    }
}