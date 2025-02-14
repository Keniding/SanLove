package com.keniding.sanlove.data.datasource

import com.keniding.sanlove.data.model.Message

class LocalMessageDataSource {
    private val messages = mutableListOf<Message>()

    init {
        messages.addAll(
            listOf(
                Message("1", "Cada día a tu lado es una nueva aventura llena de amor y felicidad", "ROMANTIC"),
                Message("2", "Te amo más que ayer y menos que mañana", "ROMANTIC"),
                Message("3", "Eres mi persona favorita", "ROMANTIC"),
                Message("4", "¡Feliz San Valentín, amig@!", "FRIENDLY"),
                Message("5", "¿Qué le dijo un café a otro café? ¡Eres mi media taza!", "FUNNY")
            )
        )
    }

    fun getMessages(): List<Message> = messages
    fun saveMessage(message: Message) {
        messages.add(message)
    }
}
