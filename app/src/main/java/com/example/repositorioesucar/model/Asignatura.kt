package com.example.repositorioesucar.model

import kotlin.collections.emptyList

data class UnidadTematica(
    val id: Int,
    val nombre: String,
    val descripcion: String,
    val materiales: List<MaterialEstudio> = emptyList()
)

data class Asignatura(
    val id: Int,
    val codigo: String,
    val nombre: String,
    val semestre: Int,
    val area: String,
    val unidades: List<UnidadTematica> = emptyList()
)