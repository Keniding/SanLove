package com.keniding.sanlove.ui.valentine.component.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.keniding.sanlove.ui.common.navigation.NavRoutes
import com.keniding.sanlove.ui.common.theme.ValentineColors

data class BottomNavItem(
    val route: String,
    val icon: ImageVector,
    val label: String
)

@Composable
fun BottomBar(navController: NavController) {
    val items = listOf(
        BottomNavItem(NavRoutes.Valentine.route, Icons.Default.Favorite, "Inicio"),
        BottomNavItem(NavRoutes.Messages.route, Icons.Default.Email, "Mensajes"),
        BottomNavItem(NavRoutes.Profile.route, Icons.Default.Person, "Perfil")
    )

    NavigationBar(
        containerColor = ValentineColors.WarmWhite.copy(alpha = 0.85f),
        contentColor = ValentineColors.DeepRose
    ) {
        val navBackStackEntry = navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry.value?.destination?.route

        items.forEach { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        item.icon,
                        contentDescription = item.label,
                        tint = if (currentRoute == item.route)
                            ValentineColors.DeepRose
                        else
                            ValentineColors.Rose
                    )
                },
                label = {
                    Text(
                        item.label,
                        color = if (currentRoute == item.route)
                            ValentineColors.DeepRose
                        else
                            ValentineColors.Rose
                    )
                },
                selected = currentRoute == item.route,
                onClick = {
                    if (currentRoute != item.route) {
                        navController.navigate(item.route) {
                            popUpTo(NavRoutes.Valentine.route) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = ValentineColors.DeepRose,
                    unselectedIconColor = ValentineColors.Rose,
                    selectedTextColor = ValentineColors.DeepRose,
                    unselectedTextColor = ValentineColors.Rose,
                    indicatorColor = ValentineColors.SoftPink
                )
            )
        }
    }
}
