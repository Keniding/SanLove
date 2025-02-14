package com.keniding.sanlove.domain.model

import com.keniding.sanlove.domain.enums.MessageType

data class ValentineMessage(
    val id: String,
    val content: String,
    val type: MessageType
)
