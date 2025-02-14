package com.keniding.sanlove.ui.common.navigation

sealed class NavRoutes(val route: String) {
    object Register : NavRoutes("register")
    object ConnectPartner : NavRoutes("connect/{code}") {
        fun createRoute(code: String) = "connect/$code"
    }
    object Valentine : NavRoutes("valentine")
    object Messages : NavRoutes("messages")
    object Profile : NavRoutes("profile")
}
