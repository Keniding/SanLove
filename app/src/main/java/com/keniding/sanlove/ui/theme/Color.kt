package com.keniding.sanlove.ui.theme

import androidx.compose.ui.graphics.Color

object ValentineColors {
    val Rose = Color(0xFFFF6B6B)
    val DeepRose = Color(0xFFE63946)
    val SoftPink = Color(0xFFFFB5B5)
    val BlushPink = Color(0xFFFFF0F3)
    val WarmWhite = Color(0xFFFFF5F5)
    val TransparentWhite = Color.White.copy(alpha = 0.85f)
    val GlassEffect = Color.White.copy(alpha = 0.12f)

    val backgroundGradient = listOf(
        SoftPink,
        DeepRose
    )

    val glassCard = BlushPink.copy(alpha = 0.15f)
    val glassCardBorder = BlushPink.copy(alpha = 0.25f)
}