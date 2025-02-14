package com.keniding.sanlove.ui.register.screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.keniding.sanlove.ui.common.navigation.NavRoutes
import com.keniding.sanlove.ui.common.component.StyledButton
import com.keniding.sanlove.ui.common.component.StyledOutlinedTextField
import com.keniding.sanlove.ui.common.theme.ValentineColors
import com.keniding.sanlove.ui.valentine.component.glass.GlassCard
import org.koin.androidx.compose.koinViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ConnectPartnerScreen(
    code: String?,
    navController: NavController,
    viewModel: RegisterViewModel = koinViewModel()
) {
    var name by remember { mutableStateOf("") }
    var selectedDate by remember { mutableStateOf<LocalDate?>(null) }
    var showDatePicker by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }

    val dateFormatter = remember { DateTimeFormatter.ofPattern("dd/MM/yyyy") }

    LaunchedEffect(code) {
        if (code == null) {
            navController.navigate(NavRoutes.Register.route) {
                popUpTo(NavRoutes.Register.route) { inclusive = true }
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Conectar con tu pareja",
                style = MaterialTheme.typography.headlineLarge
            )

            GlassCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Código de conexión: $code",
                        style = MaterialTheme.typography.displayMedium
                    )

                    StyledOutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = "Tu nombre",
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = selectedDate?.format(dateFormatter) ?: "",
                        onValueChange = { },
                        label = { Text("Fecha de nacimiento") },
                        readOnly = true,
                        trailingIcon = {
                            IconButton(onClick = { showDatePicker = true }) {
                                Icon(
                                    imageVector = Icons.Default.DateRange,
                                    contentDescription = "Seleccionar fecha"
                                )
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = ValentineColors.DeepRose,
                            unfocusedBorderColor = ValentineColors.Rose.copy(alpha = 0.6f),
                            focusedLabelColor = ValentineColors.DeepRose,
                            unfocusedLabelColor = ValentineColors.Rose.copy(alpha = 0.8f),
                            focusedContainerColor = ValentineColors.TransparentWhite,
                            unfocusedContainerColor = ValentineColors.TransparentWhite
                        )
                    )

                    if (error != null) {
                        Text(
                            text = error!!,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }

                    StyledButton(
                        onClick = {
                            if (name.isNotBlank() && selectedDate != null) {
                                isLoading = true
                                error = null
                                viewModel.connectWithPartner(
                                    code = code!!,
                                    name = name,
                                    birthDate = selectedDate!!.format(dateFormatter),
                                    onSuccess = {
                                        navController.navigate(NavRoutes.Profile.route) {
                                            popUpTo(NavRoutes.Register.route) { inclusive = true }
                                        }
                                    },
                                    onError = { errorMessage ->
                                        error = errorMessage
                                        isLoading = false
                                    }
                                )
                            } else {
                                error = "Por favor completa todos los campos"
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        enabled = !isLoading && name.isNotBlank() && selectedDate != null
                    ) {
                        if (isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(24.dp),
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        } else {
                            Text(
                                "Conectar",
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }
                }
            }
        }

        if (showDatePicker) {
            val datePickerState = rememberDatePickerState(
                initialSelectedDateMillis = System.currentTimeMillis()
            )

            DatePickerDialog(
                onDismissRequest = { showDatePicker = false },
                colors = DatePickerDefaults.colors(
                    containerColor = ValentineColors.WarmWhite,
                    selectedDayContainerColor = ValentineColors.DeepRose,
                    selectedDayContentColor = ValentineColors.WarmWhite,
                    todayDateBorderColor = ValentineColors.Rose,
                    todayContentColor = ValentineColors.DeepRose,
                    weekdayContentColor = ValentineColors.DeepRose,
                    dayContentColor = ValentineColors.DeepRose,
                    titleContentColor = ValentineColors.DeepRose,
                    headlineContentColor = ValentineColors.DeepRose,
                ),
                confirmButton = {
                    TextButton(
                        onClick = {
                            datePickerState.selectedDateMillis?.let { millis ->
                                selectedDate = LocalDate.ofEpochDay(millis / (24 * 60 * 60 * 1000))
                            }
                            showDatePicker = false
                        }
                    ) {
                        Text("OK")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDatePicker = false }) {
                        Text("Cancelar")
                    }
                }
            ) {
                DatePicker(
                    state = datePickerState,
                    title = {
                        Text(
                            text = "Selecciona tu fecha de nacimiento",
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                    },
                    headline = {
                        Text(
                            text = "Fecha de nacimiento",
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                    }
                )
            }
        }
    }
}
