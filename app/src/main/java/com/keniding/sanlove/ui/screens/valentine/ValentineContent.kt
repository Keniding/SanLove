package com.keniding.sanlove.ui.screens.valentine

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.keniding.sanlove.ValentineColors
import com.keniding.sanlove.ui.component.animated.AnimatedMessage

@Composable
fun ValentineContent(
    state: ValentineState,
    onShowMessage: () -> Unit,
    onResetMessage: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Para Ti ❤️",
            style = MaterialTheme.typography.headlineLarge,
            color = ValentineColors.DeepRose,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        AnimatedMessage(
            message = state.currentMessage,
            isVisible = state.isMessageVisible
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = if (state.isMessageVisible) onResetMessage else onShowMessage,
            colors = ButtonDefaults.buttonColors(
                containerColor = ValentineColors.BlushPink.copy(alpha = 0.8f)
            ),
            modifier = Modifier.clip(MaterialTheme.shapes.large)
        ) {
            Text(
                text = if (state.isMessageVisible) "Reiniciar" else "Mostrar Mensaje",
                color = ValentineColors.DeepRose
            )
        }
    }
}
