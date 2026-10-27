package com.example.repositorioesucar.data.repository

import com.example.repositorioesucar.model.Asignatura
import com.example.repositorioesucar.model.AsignaturaMock

class AsignaturaRepository {
    fun obtenerTodas(): List<Asignatura> = AsignaturaMock.listaAsignaturas
    fun obtenerPorSemestre(semestre: Int): List<Asignatura> =
        AsignaturaMock.listaAsignaturas.filter { it.semestre == semestre }
    fun obtenerPorId(id: Int): Asignatura? =
        AsignaturaMock.listaAsignaturas.find { it.id == id }
}