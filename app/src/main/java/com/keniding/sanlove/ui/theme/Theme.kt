package com.keniding.sanlove.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

@Composable
fun ValentineTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = darkColorScheme(
            primary = ValentineColors.DeepRose,
            secondary = ValentineColors.SoftPink,
            surface = ValentineColors.GlassEffect,
            background = ValentineColors.Rose,
            onPrimary = ValentineColors.WarmWhite,
            onSecondary = ValentineColors.DeepRose,
            onSurface = ValentineColors.WarmWhite,
            onBackground = ValentineColors.TransparentWhite
        ),
        typography = Typography,
        content = content
    )
}
