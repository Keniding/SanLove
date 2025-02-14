package com.keniding.sanlove.domain.repository

import com.keniding.sanlove.domain.model.ValentineMessage

interface IMessageRepository {
    suspend fun getMessages(): List<ValentineMessage>
    suspend fun saveMessage(message: ValentineMessage)
}