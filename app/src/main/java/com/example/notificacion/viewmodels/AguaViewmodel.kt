package com.example.notificacion.viewmodels

import com.example.notificacion.models.Registro
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

class AguaViewModel : ViewModel() {
    private val _registros = mutableStateListOf<Registro>()
    val registros: List<Registro> = _registros
    private var siguienteId = 1
    fun agregarRegistro() {
        _registros.add(0, Registro(id = siguienteId))
        siguienteId++
    }
    fun responder(id: Int, tomoAgua: Boolean) {
        val i = _registros.indexOfFirst { it.id == id }
        if (i != -1) {
            _registros[i] = _registros[i].copy(tomoAgua = tomoAgua)
        }
    }
}