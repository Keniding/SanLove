package com.keniding.sanlove.ui.valentine.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.keniding.sanlove.domain.usecase.GetValentineMessageUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ValentineViewModel(
    private val getValentineMessageUseCase: GetValentineMessageUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(ValentineState())
    val uiState = _uiState.asStateFlow()

    fun onEvent(event: ValentineEvent) {
        when (event) {
            is ValentineEvent.ShowMessage -> showMessage()
            is ValentineEvent.ResetMessage -> resetMessage()
        }
    }

    private fun showMessage() {
        viewModelScope.launch {
            val message = getValentineMessageUseCase()
            _uiState.update { it.copy(
                currentMessage = message,
                isMessageVisible = true
            ) }
        }
    }

    private fun resetMessage() {
        _uiState.update { it.copy(
            currentMessage = null,
            isMessageVisible = false
        ) }
    }
}