package com.keniding.sanlove.ui.component.other

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.keniding.sanlove.ValentineColors
import com.keniding.sanlove.data.model.ParticleState
import com.keniding.sanlove.domain.utils.drawHeart
import kotlin.random.Random

@Composable
fun FloatingHearts() {
    val configuration = LocalConfiguration.current
    val screenWidth = with(LocalDensity.current) { configuration.screenWidthDp.dp.toPx() }
    val screenHeight = with(LocalDensity.current) { configuration.screenHeightDp.dp.toPx() }
    val heartSize = with(LocalDensity.current) { 80.dp.toPx() }

    val particles = remember {
        List(50) {
            ParticleState(
                x = Random.nextFloat() * (screenWidth + heartSize) - heartSize/2,
                baseSpeed = Random.nextFloat() * 1.5f + 0.5f,
                scale = Random.nextFloat() * 0.6f + 0.8f
            )
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        particles.forEach { particle ->
            val infiniteTransition = rememberInfiniteTransition(label = "")

            val initialY = remember { Random.nextFloat() * -screenHeight * 2 }

            val yPosition by infiniteTransition.animateFloat(
                initialValue = initialY,
                targetValue = screenHeight + 100f,
                animationSpec = infiniteRepeatable(
                    animation = tween(
                        durationMillis = (6000 / particle.baseSpeed).toInt(),
                        easing = LinearEasing
                    ),
                    repeatMode = RepeatMode.Restart
                ),
                label = ""
            )

            val xOffset by infiniteTransition.animateFloat(
                initialValue = -50f,
                targetValue = 50f,
                animationSpec = infiniteRepeatable(
                    animation = tween(
                        durationMillis = 3000,
                        easing = LinearEasing
                    ),
                    repeatMode = RepeatMode.Reverse
                ),
                label = ""
            )

            val rotation by infiniteTransition.animateFloat(
                initialValue = -30f,
                targetValue = 30f,
                animationSpec = infiniteRepeatable(
                    animation = tween(
                        durationMillis = 4000,
                        easing = LinearEasing
                    ),
                    repeatMode = RepeatMode.Reverse
                ),
                label = ""
            )

            Canvas(
                modifier = Modifier
                    .size(80.dp)
                    .graphicsLayer {
                        translationX = particle.x + xOffset * particle.scale
                        translationY = yPosition
                        rotationZ = rotation
                        alpha = 0.9f
                    }
            ) {
                drawHeart(
                    color = ValentineColors.DeepRose.copy(alpha = 0.9f),
                    size = 60f * particle.scale
                )
            }
        }
    }
}
