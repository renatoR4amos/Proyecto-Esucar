package com.example.repositorioesucar.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class ProfileUiState(
    val nombre: String = "Suboficial Alumno",
    val correo: String = "alumno.esucar@carabineros.cl",
    val fotoUri: Uri? = null
)

class ProfileViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState

    fun actualizarFoto(uri: Uri?) {
        _uiState.value = _uiState.value.copy(fotoUri = uri)
    }
}