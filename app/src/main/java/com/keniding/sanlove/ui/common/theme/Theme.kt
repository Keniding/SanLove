package com.keniding.sanlove.ui.common.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = ValentineColors.DeepRose,
    secondary = ValentineColors.SoftPink,
    tertiary = ValentineColors.Rose,
    background = ValentineColors.BlushPink,
    surface = ValentineColors.WarmWhite,
    onPrimary = ValentineColors.WarmWhite,
    onSecondary = ValentineColors.DeepRose,
    onTertiary = ValentineColors.WarmWhite,
    onBackground = ValentineColors.DeepRose,
    onSurface = ValentineColors.DeepRose,
)

@Composable
fun ValentineTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography.copy(
            displayLarge = Typography.displayLarge.copy(color = ValentineColors.DeepRose),
            displayMedium = Typography.displayMedium.copy(color = ValentineColors.DeepRose),
            headlineLarge = Typography.headlineLarge.copy(color = ValentineColors.DeepRose),
            bodyLarge = Typography.bodyLarge.copy(color = ValentineColors.DeepRose),
            bodyMedium = Typography.bodyMedium.copy(color = ValentineColors.DeepRose),
            labelMedium = Typography.labelMedium.copy(color = ValentineColors.DeepRose)
        ),
        content = content
    )
}
