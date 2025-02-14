package com.keniding.sanlove.ui.valentine.component.glass

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class GlassCardStyle(
    val backgroundColor: Color,
    val borderColor: Color,
    val cornerRadius: Dp = 20.dp,
    val borderWidth: Dp = 1.dp,
    val alpha: Float = 0.15f
)