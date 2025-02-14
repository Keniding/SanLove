package com.keniding.sanlove.ui.common.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.keniding.sanlove.ui.valentine.component.other.FloatingHearts
import com.keniding.sanlove.ui.messages.MessagesScreen
import com.keniding.sanlove.ui.profile.screens.ProfileScreen
import com.keniding.sanlove.ui.valentine.screens.ValentineScreen
import com.keniding.sanlove.ui.common.theme.ValentineColors
import com.keniding.sanlove.ui.register.screen.ConnectPartnerScreen
import com.keniding.sanlove.ui.register.screen.RegisterScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavGraph(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

    LaunchedEffect(Unit) {
        DeepLinkHandler.consumePendingCode()?.let { code ->
            navController.navigateToConnectPartner(code)
        }
    }

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
                    startDestination = NavRoutes.Register.route,
                    modifier = modifier
                ) {
                    composable(NavRoutes.Register.route) {
                        RegisterScreen(navController = navController)
                    }

                    composable(
                        route = NavRoutes.ConnectPartner.route,
                        arguments = listOf(
                            navArgument("code") { type = NavType.StringType }
                        ),
                        deepLinks = listOf(
                            navDeepLink {
                                uriPattern = "sanlove://connect/{code}"
                            }
                        )
                    ) { backStackEntry ->
                        val code = backStackEntry.arguments?.getString("code")
                        ConnectPartnerScreen(code = code, navController = navController)
                    }

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
