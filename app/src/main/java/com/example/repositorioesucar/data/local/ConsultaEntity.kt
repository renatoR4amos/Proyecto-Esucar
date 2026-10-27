package com.example.repositorioesucar.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "consultas")
data class ConsultaEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val alumnoNombre: String,
    val correoAlumno: String,
    val asunto: String,
    val mensaje: String,
    val fecha: String
)
