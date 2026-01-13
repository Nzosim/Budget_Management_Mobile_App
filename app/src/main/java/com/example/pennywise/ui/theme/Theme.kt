package com.example.pennywise.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

//private val DarkColorScheme = darkColorScheme(
//    primary = Color(0x00FFFFFF),
//    secondary = Color(0x00FFFFFF),
//    onPrimary = Color(0x00FFFFFF),
//    tertiary = Color(0x00FFFFFF),
//    background = Color(0xFF1E1E1E)
//)

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF282828), // Couleur des éléments primaires
    onPrimary = Color.White, // Texte éléments primaires,
    background = Color(0xFF1E1E1E) // Fond
)

@Composable
fun PennyWiseTheme(
    darkTheme: Boolean = true,
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = DarkColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}