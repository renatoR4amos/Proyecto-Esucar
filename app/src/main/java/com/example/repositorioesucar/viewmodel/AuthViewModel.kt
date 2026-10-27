package com.example.repositorioesucar.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.repositorioesucar.data.local.AppDatabase
import com.example.repositorioesucar.data.local.UserEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class AuthUiState(
    val correo: String = "",
    val clave: String = "",
    val nombre: String = "",
    val rol: String = "ESTUDIANTE", // Rol seleccionado actualmente en la UI
    val usuarioLogueado: UserEntity? = null,
    val mensajeError: String? = null,
    val estaCargando: Boolean = false
)

class AuthViewModel(application: Application) : AndroidViewModel(application) {
    private val userDao = AppDatabase.getDatabase(application).userDao()

    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState

    fun onCorreoChange(valor: String) = _uiState.update { it.copy(correo = valor, mensajeError = null) }
    fun onClaveChange(valor: String) = _uiState.update { it.copy(clave = valor, mensajeError = null) }
    fun onNombreChange(valor: String) = _uiState.update { it.copy(nombre = valor, mensajeError = null) }
    fun onRolChange(valor: String) = _uiState.update { it.copy(rol = valor) }

    fun limpiarCampos() {
        _uiState.update { AuthUiState() }
    }

    fun login(onSuccess: (String, String) -> Unit) {
        val correo = _uiState.value.correo.trim().lowercase()
        val clave = _uiState.value.clave.trim()
        val rolSeleccionado = _uiState.value.rol

        if (correo.isEmpty() || clave.isEmpty()) {
            _uiState.update { it.copy(mensajeError = "Por favor completa todos los campos") }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(estaCargando = true) }

            // Buscamos al usuario por correo insensible a mayúsculas
            val user = userDao.login(correo, clave)

            if (user != null) {
                // VALIDACIÓN ESTRICTA DE ROL:
                if (user.rol.equals(rolSeleccionado, ignoreCase = true)) {
                    _uiState.update { it.copy(usuarioLogueado = user, estaCargando = false, mensajeError = null) }
                    onSuccess(user.correo, user.rol)
                } else {
                    val perfilReal = if (user.rol.equals("DOCENTE", ignoreCase = true)) "Docente" else "Alumno"
                    _uiState.update {
                        it.copy(
                            mensajeError = "Esta cuenta pertenece a un perfil de $perfilReal",
                            estaCargando = false
                        )
                    }
                }
            } else {
                _uiState.update { it.copy(mensajeError = "Correo o contraseña incorrectos", estaCargando = false) }
            }
        }
    }

    fun registrar(onSuccess: () -> Unit) {
        val nombre = _uiState.value.nombre.trim()
        val correo = _uiState.value.correo.trim().lowercase()
        val clave = _uiState.value.clave.trim()
        val rolSeleccionado = _uiState.value.rol

        if (nombre.isEmpty() || correo.isEmpty() || clave.isEmpty()) {
            _uiState.update { it.copy(mensajeError = "Todos los campos son obligatorios") }
            return
        }

        if (!correo.contains("@")) {
            _uiState.update { it.copy(mensajeError = "El correo debe incluir un '@'") }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(estaCargando = true) }
            val nuevo = UserEntity(
                nombre = nombre,
                correo = correo,
                clave = clave,
                rol = rolSeleccionado
            )
            userDao.registrarUsuario(nuevo)
            _uiState.update { AuthUiState() }
            onSuccess()
        }
    }
}
