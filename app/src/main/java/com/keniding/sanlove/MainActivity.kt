package com.keniding.sanlove

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.background
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Brush
import androidx.compose.foundation.Canvas
import androidx.compose.animation.core.*
import androidx.compose.foundation.border
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import kotlin.random.Random
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.platform.LocalConfiguration
import com.keniding.sanlove.data.model.ParticleState

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ValentineTheme {
                ValentineApp()
            }
        }
    }
}

@Composable
fun ValentineApp() {
    var showMessage by remember { mutableStateOf(false) }
    val density = LocalDensity.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = ValentineColors.backgroundGradient
                )
            )
    ) {
        FloatingHearts()

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
                .align(Alignment.Center)
                .clip(RoundedCornerShape(30.dp))
                .background(ValentineColors.BlushPink.copy(alpha = 0.15f))
                .blur(0.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AnimatedContent(
                targetState = showMessage,
                transitionSpec = {
                    (fadeIn() + slideInVertically { with(density) { 40.dp.roundToPx() } }).togetherWith(
                        fadeOut() + slideOutVertically { with(density) { -40.dp.roundToPx() } })
                }, label = ""
            ) { shown ->
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Para Ti ❤️",
                        style = MaterialTheme.typography.headlineLarge,
                        color = ValentineColors.DeepRose,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    GlassCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        Text(
                            text = if (shown)
                                "Cada día a tu lado es una nueva aventura llena de amor y felicidad"
                            else "Toca para ver un mensaje especial",
                            style = MaterialTheme.typography.bodyLarge,
                            color = ValentineColors.DeepRose,
                            modifier = Modifier.padding(16.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = { showMessage = !showMessage },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = ValentineColors.BlushPink.copy(alpha = 0.8f)
                        ),
                        modifier = Modifier
                            .clip(RoundedCornerShape(50))
                    ) {
                        Text(
                            text = if (shown) "Reiniciar" else "Mostrar Mensaje",
                            color = ValentineColors.DeepRose
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun GlassCard(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .background(ValentineColors.glassCard)
            .padding(1.dp)
            .border(
                width = 1.dp,
                color = ValentineColors.glassCardBorder,
                shape = RoundedCornerShape(20.dp)
            )
    ) {
        content()
    }
}

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

private fun DrawScope.drawHeart(color: Color, size: Float) {
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

object ValentineColors {
    val Rose = Color(0xFFFF6B6B)
    val DeepRose = Color(0xFFE63946)
    val SoftPink = Color(0xFFFFB5B5)
    val BlushPink = Color(0xFFFFF0F3)
    val WarmWhite = Color(0xFFFFF5F5)
    val TransparentWhite = Color.White.copy(alpha = 0.85f)
    val GlassEffect = Color.White.copy(alpha = 0.12f)

    val backgroundGradient = listOf(
        DeepRose,
        SoftPink
    )

    val glassCard = BlushPink.copy(alpha = 0.15f)
    val glassCardBorder = BlushPink.copy(alpha = 0.25f)
}

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
        content = content
    )
}
