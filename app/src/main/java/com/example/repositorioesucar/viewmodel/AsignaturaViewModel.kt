package com.example.repositorioesucar.viewmodel

import androidx.lifecycle.ViewModel
import com.example.repositorioesucar.data.repository.AsignaturaRepository
import com.example.repositorioesucar.model.Asignatura
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class AsignaturaUiState(
    val asignaturas: List<Asignatura> = emptyList(),
    val semestreActual: Int = 1
)

class AsignaturaViewModel(
    private val repository: AsignaturaRepository = AsignaturaRepository()
) : ViewModel() {
    private val _uiState = MutableStateFlow(AsignaturaUiState())
    val uiState: StateFlow<AsignaturaUiState> = _uiState

    init {
        cargarPorSemestre(1)
    }

    fun cargarPorSemestre(semestre: Int) {
        val lista = repository.obtenerPorSemestre(semestre)
        _uiState.value = AsignaturaUiState(asignaturas = lista, semestreActual = semestre)
    }
}