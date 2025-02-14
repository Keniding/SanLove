package com.keniding.sanlove.ui.common.navigation

sealed class NavRoutes(val route: String) {
    data object Valentine : NavRoutes("valentine")
    data object Messages : NavRoutes("messages")
    data object Profile : NavRoutes("profile")
}
