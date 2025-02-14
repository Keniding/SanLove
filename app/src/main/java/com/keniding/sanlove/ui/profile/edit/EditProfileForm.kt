package com.keniding.sanlove.ui.profile.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.keniding.sanlove.data.model.profile.CoupleProfile
import com.keniding.sanlove.ui.common.component.StyledButton
import com.keniding.sanlove.ui.common.component.StyledOutlinedTextField
import com.keniding.sanlove.ui.common.theme.ValentineColors
import com.keniding.sanlove.ui.valentine.component.glass.GlassCard

@Composable
fun EditProfileForm(
    profile: CoupleProfile,
    onSave: (CoupleProfile) -> Unit,
    modifier: Modifier = Modifier
) {
    var editedProfile by remember { mutableStateOf(profile) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Editar Perfil",
            style = MaterialTheme.typography.headlineMedium,
            color = ValentineColors.DeepRose
        )

        GlassCard(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Información de ${profile.partner1.name}",
                    style = MaterialTheme.typography.titleMedium,
                    color = ValentineColors.DeepRose
                )

                StyledOutlinedTextField(
                    value = editedProfile.partner1.nickname,
                    onValueChange = {
                        editedProfile = editedProfile.copy(
                            partner1 = editedProfile.partner1.copy(nickname = it)
                        )
                    },
                    label = "Apodo",
                    modifier = Modifier.fillMaxWidth()
                )

                StyledOutlinedTextField(
                    value = editedProfile.partner1.avatar,
                    onValueChange = {
                        editedProfile = editedProfile.copy(
                            partner1 = editedProfile.partner1.copy(avatar = it)
                        )
                    },
                    label = "URL de Avatar",
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        GlassCard(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Información de ${profile.partner2.name}",
                    style = MaterialTheme.typography.titleMedium,
                    color = ValentineColors.DeepRose
                )

                StyledOutlinedTextField(
                    value = editedProfile.partner2.nickname,
                    onValueChange = {
                        editedProfile = editedProfile.copy(
                            partner2 = editedProfile.partner2.copy(nickname = it)
                        )
                    },
                    label = "Apodo",
                    modifier = Modifier.fillMaxWidth()
                )

                StyledOutlinedTextField(
                    value = editedProfile.partner2.avatar,
                    onValueChange = {
                        editedProfile = editedProfile.copy(
                            partner2 = editedProfile.partner2.copy(avatar = it)
                        )
                    },
                    label = "URL de Avatar",
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        GlassCard(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Información de la Relación",
                    style = MaterialTheme.typography.titleMedium,
                    color = ValentineColors.DeepRose
                )

                StyledOutlinedTextField(
                    value = editedProfile.relationship.meetingStory,
                    onValueChange = {
                        editedProfile = editedProfile.copy(
                            relationship = editedProfile.relationship.copy(meetingStory = it)
                        )
                    },
                    label = "Historia de cómo se conocieron",
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 3
                )

                StyledOutlinedTextField(
                    value = editedProfile.relationship.song.title,
                    onValueChange = {
                        editedProfile = editedProfile.copy(
                            relationship = editedProfile.relationship.copy(
                                song = editedProfile.relationship.song.copy(title = it)
                            )
                        )
                    },
                    label = "Título de la canción",
                    modifier = Modifier.fillMaxWidth()
                )

                StyledOutlinedTextField(
                    value = editedProfile.relationship.song.artist,
                    onValueChange = {
                        editedProfile = editedProfile.copy(
                            relationship = editedProfile.relationship.copy(
                                song = editedProfile.relationship.song.copy(artist = it)
                            )
                        )
                    },
                    label = "Artista",
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        StyledButton(
            onClick = { onSave(editedProfile) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        ) {
            Text("Guardar Cambios")
        }
    }
}
