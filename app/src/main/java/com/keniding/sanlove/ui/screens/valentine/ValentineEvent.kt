package com.keniding.sanlove.ui.screens.valentine

sealed class ValentineEvent {
    data object ShowMessage : ValentineEvent()
    data object ResetMessage : ValentineEvent()
}