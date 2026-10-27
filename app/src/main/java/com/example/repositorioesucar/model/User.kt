package com.example.repositorioesucar.model

data class User(
    val id: Int = 0,
    val nombre: String,
    val correo: String,
    val rol: String // "ESTUDIANTE" | "DOCENTE"
)