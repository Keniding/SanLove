package com.keniding.sanlove.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.keniding.sanlove.ui.screens.valentine.ValentineScreen
import com.keniding.sanlove.ui.theme.ValentineColors
import com.keniding.sanlove.ui.component.other.FloatingHearts

@Composable
fun NavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
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

        NavHost(
            navController = navController,
            startDestination = NavRoutes.Valentine.route,
            modifier = modifier
        ) {
            composable(NavRoutes.Valentine.route) {
                ValentineScreen()
            }
        }
    }
}
