package com.keniding.sanlove.ui.register.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.keniding.sanlove.domain.enums.RegistrationStep
import com.keniding.sanlove.ui.common.navigation.NavRoutes
import com.keniding.sanlove.ui.common.theme.ValentineColors
import com.keniding.sanlove.ui.register.component.*
import com.keniding.sanlove.ui.valentine.component.other.FloatingHearts
import org.koin.androidx.compose.koinViewModel

@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel = koinViewModel(),
    navController: NavController
) {

    val state by viewModel.registrationState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(ValentineColors.backgroundGradient)
            )
    ) {

        FloatingHearts()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Historia de Amor",
                style = MaterialTheme.typography.displayLarge,
                modifier = Modifier.padding(vertical = 32.dp)
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = ValentineColors.TransparentWhite
                )
            ) {
                when (state.registrationStep) {
                    RegistrationStep.INITIAL -> InitialRegistrationForm(viewModel)
                    RegistrationStep.WAITING_PARTNER -> WaitingForPartner(state.temporaryCode)
                    RegistrationStep.COMPLETING_PROFILE -> CompleteProfileForm(viewModel, navController)
                    RegistrationStep.COMPLETED -> {
                        LaunchedEffect(Unit) {
                            navController.navigate(NavRoutes.Profile.route) {
                                popUpTo(NavRoutes.Register.route) { inclusive = true }
                            }
                        }
                    }
                }
            }
        }
    }
}