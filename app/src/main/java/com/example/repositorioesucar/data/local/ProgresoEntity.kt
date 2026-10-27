package com.example.repositorioesucar.data.local

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "progreso_estudio",
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["id"],
            childColumns = ["usuarioId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    // AGREGADO: Índice requerido por Room para llaves foráneas
    indices = [Index(value = ["usuarioId"])]
)
data class ProgresoEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val usuarioId: Int,
    val materialId: Int,
    val completado: Boolean
)