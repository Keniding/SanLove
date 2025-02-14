package com.keniding.sanlove.ui.component.glass

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.keniding.sanlove.ui.theme.ValentineColors

@Composable
fun GlassCard(
    modifier: Modifier = Modifier,
    style: GlassCardStyle = GlassCardStyle(
        backgroundColor = ValentineColors.glassCard,
        borderColor = ValentineColors.glassCardBorder
    ),
    onClick: (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    val cardModifier = modifier
        .clip(RoundedCornerShape(style.cornerRadius))
        .background(style.backgroundColor)
        .padding(1.dp)
        .border(
            width = style.borderWidth,
            color = style.borderColor,
            shape = RoundedCornerShape(style.cornerRadius)
        )
        .then(
            if (onClick != null) {
                Modifier.clickable(onClick = onClick)
            } else Modifier
        )

    Box(modifier = cardModifier) {
        content()
    }
}
