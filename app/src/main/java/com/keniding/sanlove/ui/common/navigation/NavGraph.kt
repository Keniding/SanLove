package com.keniding.sanlove.ui.common.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.keniding.sanlove.ui.valentine.component.navigation.BottomBar
import com.keniding.sanlove.ui.valentine.component.other.FloatingHearts
import com.keniding.sanlove.ui.messages.MessagesScreen
import com.keniding.sanlove.ui.profile.screens.ProfileScreen
import com.keniding.sanlove.ui.valentine.screens.ValentineScreen
import com.keniding.sanlove.ui.common.theme.ValentineColors

@Composable
fun NavGraph(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

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
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier.weight(1f)
            ) {
                NavHost(
                    navController = navController,
                    startDestination = NavRoutes.Valentine.route,
                    modifier = modifier
                ) {
                    composable(NavRoutes.Valentine.route) {
                        ValentineScreen()
                    }
                    composable(NavRoutes.Messages.route) {
                        MessagesScreen()
                    }
                    composable(NavRoutes.Profile.route) {
                        ProfileScreen()
                    }
                }
            }

            BottomBar(navController = navController)
        }
    }
}
