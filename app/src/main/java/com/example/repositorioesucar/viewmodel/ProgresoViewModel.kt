package com.example.repositorioesucar.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class ProgresoUiState(
    val completados: Int = 14,
    val total: Int = 48
)

class ProgresoViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(ProgresoUiState())
    val uiState: StateFlow<ProgresoUiState> = _uiState

    fun marcarComoEstudiado(materialId: Int) {
        val actual = _uiState.value.completados
        if (actual < _uiState.value.total) {
            _uiState.value = _uiState.value.copy(completados = actual + 1)
        }
    }
}