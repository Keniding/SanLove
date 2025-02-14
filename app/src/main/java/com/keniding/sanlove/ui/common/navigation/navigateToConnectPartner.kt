package com.keniding.sanlove.ui.common.navigation

import androidx.navigation.NavController

fun NavController.navigateToConnectPartner(code: String) {
    this.navigate(NavRoutes.ConnectPartner.createRoute(code))
}
