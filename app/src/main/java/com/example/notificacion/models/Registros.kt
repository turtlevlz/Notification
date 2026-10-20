package com.example.notificacion.models

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class Registro(
    val id: Int,
    val fecha: Date = Date(),
    val tomoAgua: Boolean? = null
) {
    fun dia(): String {
        return SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(fecha)
    }
    fun hora(): String {
        return SimpleDateFormat("HH:mm", Locale.getDefault()).format(fecha)
    }
}