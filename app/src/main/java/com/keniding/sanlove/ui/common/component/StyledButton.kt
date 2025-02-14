package com.keniding.sanlove.ui.common.component

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import com.keniding.sanlove.ui.common.theme.ValentineColors

@Composable
fun StyledButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    content: @Composable () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = ValentineColors.SoftPink,
            contentColor = ValentineColors.DeepRose,
            disabledContainerColor = ValentineColors.glassCard.copy(alpha = 0.5f),
            disabledContentColor = ValentineColors.DeepRose.copy(alpha = 0.5f)
        ),
        border = ButtonDefaults.outlinedButtonBorder.copy(
            brush = SolidColor(ValentineColors.glassCardBorder)
        ),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 0.dp,
            pressedElevation = 0.dp,
            hoveredElevation = 2.dp
        )
    ) {
        content()
    }
}
