package com.keniding.sanlove.ui.screens.valentine

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.koin.androidx.compose.koinViewModel

@Composable
fun ValentineScreen(
    viewModel: ValentineViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        ValentineContent(
            state = uiState,
            onShowMessage = { viewModel.onEvent(ValentineEvent.ShowMessage) },
            onResetMessage = { viewModel.onEvent(ValentineEvent.ResetMessage) }
        )
    }
}
