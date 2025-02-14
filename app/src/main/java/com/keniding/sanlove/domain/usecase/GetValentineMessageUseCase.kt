package com.keniding.sanlove.domain.usecase

import com.keniding.sanlove.domain.model.ValentineMessage
import com.keniding.sanlove.domain.repository.IMessageRepository

class GetValentineMessageUseCase(
    private val messageRepository: IMessageRepository
) {
    suspend operator fun invoke(): ValentineMessage {
        val messages = messageRepository.getMessages()
        return messages.random()
    }
}
