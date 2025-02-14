package com.keniding.sanlove.ui.profile.screens

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.keniding.sanlove.ui.profile.component.CouplePhotosSection
import com.keniding.sanlove.ui.profile.component.RelationshipInfoCard
import com.keniding.sanlove.ui.profile.component.SpecialMomentCard
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        Log.d("ProfileScreen", "Profile: ${uiState.profile}")
    }

    if (uiState.isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    uiState.error?.let { error ->
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = "Error: $error", color = MaterialTheme.colorScheme.error)
        }
        return
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Text(
                text = "Nuestro Amor",
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.padding(vertical = 16.dp)
            )
        }

        uiState.profile?.let { profile ->
            item {
                CouplePhotosSection(
                    partner1 = profile.partner1,
                    partner2 = profile.partner2
                )
            }

            item {
                RelationshipInfoCard(relationshipInfo = profile.relationship)
            }

            item {
                Text(
                    text = "Nuestros Momentos",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(vertical = 16.dp)
                )
            }

            items(profile.relationship.specialMoments) { moment ->
                SpecialMomentCard(moment = moment)
            }
        }
    }
}
