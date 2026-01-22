package com.example.pennywise.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.pennywise.R

// Set of Material typography styles to start with
//val Typography = Typography(
//    bodyLarge = TextStyle(
//        fontFamily = FontFamily.Default,
//        fontWeight = FontWeight.Normal,
//        fontSize = 16.sp,
//        lineHeight = 24.sp,
//        letterSpacing = 0.5.sp
//    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */

val CustomFontFamily = FontFamily(
    Font(R.font.inter_regular, FontWeight.Normal),
    Font(R.font.inter_bold, FontWeight.Bold),
    Font(R.font.inter_semibold, FontWeight.SemiBold)
)

val Typography = Typography(
    // titres
    titleLarge = TextStyle(
        fontFamily = CustomFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 30.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp,
        shadow = Shadow(
            color = Color.Black.copy(alpha = 0.5f),
            offset = Offset(4f, 4f),
            blurRadius = 8f
        )
    ),
    // nom des catégories
    bodyLarge = TextStyle(
        fontFamily = CustomFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp,
        shadow = Shadow(
            color = Color.Black.copy(alpha = 0.5f),
            offset = Offset(4f, 4f),
            blurRadius = 8f
        )
    ),
    // textes moyens
    labelMedium = TextStyle(
        fontFamily = CustomFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp,
        shadow = Shadow(
            color = Color.Black.copy(alpha = 0.5f),
            offset = Offset(4f, 4f),
            blurRadius = 8f)
    ),
    // petits textes
    labelSmall = TextStyle(
        fontFamily = CustomFontFamily,
        fontWeight = FontWeight.ExtraLight,
        fontSize = 14.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp,
        shadow = Shadow(
            color = Color.Black.copy(alpha = 0.5f),
            offset = Offset(4f, 4f),
            blurRadius = 8f)
    )
)
