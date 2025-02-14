package com.keniding.sanlove.domain.utils

fun String.toFormattedCoupleDate(): String {
    try {
        val parts = this.split("/")
        val day = parts[0]
        val month = parts[1]
        val year = parts[2]

        val monthNames = arrayOf(
            "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
            "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"
        )

        val monthNumber = month.toInt()
        return "#$day"
    // de ${monthNames[monthNumber - 1]} del $year"
    } catch (e: Exception) {
        return this
    }
}
