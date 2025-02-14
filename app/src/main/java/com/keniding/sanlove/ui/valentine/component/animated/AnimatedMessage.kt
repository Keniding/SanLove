package com.keniding.sanlove.ui.valentine.component.animated

import androidx.compose.animation.*
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.keniding.sanlove.domain.model.ValentineMessage
import com.keniding.sanlove.ui.valentine.component.glass.GlassCard

@Composable
fun AnimatedMessage(
    message: ValentineMessage?,
    isVisible: Boolean,
    modifier: Modifier = Modifier
) {
    val density = LocalDensity.current

    AnimatedContent(
        targetState = Pair(message, isVisible),
        transitionSpec = {
            if (targetState.second) {
                (fadeIn() + slideInVertically {
                    with(density) { 40.dp.roundToPx() }
                }).togetherWith(
                    fadeOut() + slideOutVertically {
                        with(density) { -40.dp.roundToPx() }
                    }
                )
            } else {
                (fadeIn() + slideInVertically {
                    with(density) { -40.dp.roundToPx() }
                }).togetherWith(
                    fadeOut() + slideOutVertically {
                        with(density) { 40.dp.roundToPx() }
                    }
                )
            }.using(
                SizeTransform(clip = false)
            )
        },
        modifier = modifier,
        label = ""
    ) { (currentMessage, shown) ->
        if (shown && currentMessage != null) {
            GlassCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text(
                    text = currentMessage.content,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}
