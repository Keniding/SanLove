package com.keniding.sanlove.ui.valentine.screens

sealed class ValentineEvent {
    data object ShowMessage : ValentineEvent()
    data object ResetMessage : ValentineEvent()
}