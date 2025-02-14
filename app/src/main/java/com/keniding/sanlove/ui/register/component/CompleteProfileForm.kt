package com.keniding.sanlove.ui.register.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.keniding.sanlove.ui.common.navigation.NavRoutes
import com.keniding.sanlove.ui.register.screen.RegisterViewModel

@Composable
fun CompleteProfileForm(
    viewModel: RegisterViewModel,
    navController: NavController
) {
    var petName1 by remember { mutableStateOf("") }
    var petName2 by remember { mutableStateOf("") }
    var songTitle by remember { mutableStateOf("") }
    var songArtist by remember { mutableStateOf("") }
    var meetingStory by remember { mutableStateOf("") }
    var anniversaryDate by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "¡Completen juntos su historia de amor!",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Apodos cariñosos
        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Sus apodos cariñosos",
                    style = MaterialTheme.typography.titleMedium
                )
                OutlinedTextField(
                    value = petName1,
                    onValueChange = { petName1 = it },
                    label = { Text("Apodo para ${viewModel.getPartner1Name()}") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = petName2,
                    onValueChange = { petName2 = it },
                    label = { Text("Apodo para ${viewModel.getPartner2Name()}") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        // Canción especial
        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Su canción especial",
                    style = MaterialTheme.typography.titleMedium
                )
                OutlinedTextField(
                    value = songTitle,
                    onValueChange = { songTitle = it },
                    label = { Text("Título de la canción") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = songArtist,
                    onValueChange = { songArtist = it },
                    label = { Text("Artista") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        // Historia de cómo se conocieron
        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Su historia de amor",
                    style = MaterialTheme.typography.titleMedium
                )
                OutlinedTextField(
                    value = meetingStory,
                    onValueChange = { meetingStory = it },
                    label = { Text("¿Cómo se conocieron?") },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 3,
                    maxLines = 5
                )
            }
        }

        // Fecha de aniversario
        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Fecha de aniversario",
                    style = MaterialTheme.typography.titleMedium
                )
                OutlinedTextField(
                    value = anniversaryDate,
                    onValueChange = { anniversaryDate = it },
                    label = { Text("DD/MM/AAAA") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        Button(
            onClick = {
                viewModel.completeProfile(
                    petName1 = petName1,
                    petName2 = petName2,
                    songTitle = songTitle,
                    songArtist = songArtist,
                    meetingStory = meetingStory,
                    anniversaryDate = anniversaryDate
                )
                navController.navigate(NavRoutes.Profile.route) {
                    popUpTo(NavRoutes.Register.route) { inclusive = true }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 24.dp)
        ) {
            Text("Guardar nuestra historia")
        }
    }
}
