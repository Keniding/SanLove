package com.keniding.sanlove.domain.utils

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope

fun DrawScope.drawHeart(color: Color, size: Float) {
    val path = Path().apply {
        moveTo(size/2, size/5)

        cubicTo(
            size/5, 0f,
            -size/10, size/3,
            size/2, size
        )

        cubicTo(
            size + size/10, size/3,
            size - size/5, 0f,
            size/2, size/5
        )
    }

    drawPath(
        path = path,
        color = color
    )
}