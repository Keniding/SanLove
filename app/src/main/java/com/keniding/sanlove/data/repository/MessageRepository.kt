package com.keniding.sanlove.data.repository

import com.keniding.sanlove.data.datasource.LocalMessageDataSource
import com.keniding.sanlove.data.model.Message
import com.keniding.sanlove.domain.enums.MessageType
import com.keniding.sanlove.domain.model.ValentineMessage
import com.keniding.sanlove.domain.repository.IMessageRepository

class MessageRepository(
    private val localDataSource: LocalMessageDataSource
) : IMessageRepository {

    override suspend fun getMessages(): List<ValentineMessage> {
        return localDataSource.getMessages().map { message ->
            ValentineMessage(
                id = message.id,
                content = message.content,
                type = MessageType.valueOf(message.type)
            )
        }
    }

    override suspend fun saveMessage(message: ValentineMessage) {
        localDataSource.saveMessage(
            Message(
                id = message.id,
                content = message.content,
                type = message.type.name
            )
        )
    }
}
