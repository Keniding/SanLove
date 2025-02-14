package com.keniding.sanlove.ui.valentine.screens

import com.keniding.sanlove.domain.model.ValentineMessage

data class ValentineState(
    val currentMessage: ValentineMessage? = null,
    val isMessageVisible: Boolean = false,
    val isLoading: Boolean = false,
    val staticMessage: String = "Toca para ver un mensaje especial"
)
