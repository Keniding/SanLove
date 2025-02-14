package com.keniding.sanlove.ui.register.component

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
import com.keniding.sanlove.ui.common.component.StyledButton
import com.keniding.sanlove.ui.common.component.StyledOutlinedTextField
import com.keniding.sanlove.ui.common.theme.ValentineColors
import com.keniding.sanlove.ui.valentine.component.glass.GlassCard
import com.keniding.sanlove.ui.register.screen.RegisterViewModel
import org.koin.androidx.compose.koinViewModel
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun InitialRegistrationForm(
    viewModel: RegisterViewModel = koinViewModel()
) {
    var name by remember { mutableStateOf("") }
    var selectedDate by remember { mutableStateOf<LocalDate?>(null) }
    var showDatePicker by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }

    val dateFormatter = remember { DateTimeFormatter.ofPattern("dd/MM/yyyy") }

    val registrationState by viewModel.registrationState.collectAsState()

    LaunchedEffect(registrationState.error) {
        registrationState.error?.let {
            error = it
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
                text = "Comienza tu Historia",
                style = MaterialTheme.typography.headlineLarge,
                color = ValentineColors.DeepRose
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
                        text = "Bienvenido a SanLove",
                        style = MaterialTheme.typography.displayMedium,
                        color = ValentineColors.DeepRose
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
                                    contentDescription = "Seleccionar fecha",
                                    tint = ValentineColors.DeepRose
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
                                error = null
                                viewModel.startRegistration(
                                    name = name,
                                    birthDate = selectedDate!!.format(dateFormatter)
                                )
                            } else {
                                error = "Por favor completa todos los campos"
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        enabled = name.isNotBlank() && selectedDate != null
                    ) {
                        Text(
                            "Comenzar Nuestra Historia",
                            style = MaterialTheme.typography.labelMedium
                        )
                    }
                }
            }
        }

        if (showDatePicker) {
            val datePickerState = rememberDatePickerState(
                initialSelectedDateMillis = selectedDate
                    ?.atStartOfDay()
                    ?.atZone(ZoneId.systemDefault())
                    ?.toInstant()
                    ?.toEpochMilli() ?: System.currentTimeMillis()
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
                                selectedDate = Instant.ofEpochMilli(millis)
                                    .atZone(ZoneId.systemDefault())
                                    .toLocalDate()
                            }
                            showDatePicker = false
                        }
                    ) {
                        Text("OK", color = ValentineColors.DeepRose)
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDatePicker = false }) {
                        Text("Cancelar", color = ValentineColors.DeepRose)
                    }
                }
            ) {
                DatePicker(
                    state = datePickerState,
                    title = {
                        Text(
                            text = "Selecciona tu fecha de nacimiento",
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            color = ValentineColors.DeepRose
                        )
                    }
                )
            }
        }
    }
}
