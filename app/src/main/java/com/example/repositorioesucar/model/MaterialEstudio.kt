package com.example.repositorioesucar.model

data class MaterialEstudio(
    val id: Int,
    val asignaturaId: Int,
    val titulo: String,
    val tipo: String, // "PDF", "GUIA", "VIDEO"
    val tamaño: String = "2.4 MB",
    val urlRecurso: String = ""
)