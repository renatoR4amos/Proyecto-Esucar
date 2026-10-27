package com.example.repositorioesucar.model

data class ConsultaDocente(
    val id: Int = 0,
    val alumnoNombre: String,
    val asignaturaNombre: String,
    val asunto: String,
    val mensaje: String,
    val imagenUri: String? = null,
    val fecha: String = ""
)