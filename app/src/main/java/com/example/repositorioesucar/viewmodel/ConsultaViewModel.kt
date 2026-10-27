package com.example.repositorioesucar.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.repositorioesucar.data.local.AppDatabase
import com.example.repositorioesucar.data.local.ConsultaEntity
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class ConsultaUiState(
    val asunto: String = "",
    val mensaje: String = "",
    val asuntoError: String? = null,
    val mensajeError: String? = null,
    val enviado: Boolean = false
)

class ConsultaViewModel(application: Application) : AndroidViewModel(application) {
    private val db = AppDatabase.getDatabase(application)
    private val consultaDao = db.consultaDao()
    private val userDao = db.userDao()

    private val _uiState = MutableStateFlow(ConsultaUiState())
    val uiState: StateFlow<ConsultaUiState> = _uiState

    private val _misConsultas = MutableStateFlow<List<ConsultaEntity>>(emptyList())
    val misConsultas: StateFlow<List<ConsultaEntity>> = _misConsultas

    fun onAsuntoChange(valor: String) = _uiState.update {
        it.copy(asunto = valor, asuntoError = null, enviado = false)
    }

    fun onMensajeChange(valor: String) = _uiState.update {
        it.copy(mensaje = valor, mensajeError = null, enviado = false)
    }

    fun cargarConsultasAlumno(correo: String) {
        viewModelScope.launch {
            consultaDao.obtenerPorAlumno(correo.trim().lowercase())
                .collect { lista ->
                    _misConsultas.value = lista
                }
        }
    }

    fun enviarConsulta(correoAlumno: String, onSuccess: (String, String) -> Unit): Boolean {
        val asuntoActual = _uiState.value.asunto.trim()
        val mensajeActual = _uiState.value.mensaje.trim()

        var hayError = false
        var errorAsunto: String? = null
        var errorMensaje: String? = null

        if (asuntoActual.isEmpty()) {
            errorAsunto = "El asunto es obligatorio"
            hayError = true
        }

        if (mensajeActual.isEmpty()) {
            errorMensaje = "El detalle de la duda es obligatorio"
            hayError = true
        } else if (mensajeActual.length < 5) {
            errorMensaje = "Debe tener al menos 5 caracteres"
            hayError = true
        }

        if (hayError) {
            _uiState.update { it.copy(asuntoError = errorAsunto, mensajeError = errorMensaje) }
            return false
        }

        viewModelScope.launch {
            val formatoFecha = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
            val fechaActual = formatoFecha.format(Date())

            // Buscar nombre del alumno en Room
            val usuarios = userDao.obtenerTodosLosUsuarios()
            val alumno = usuarios.find { it.correo.equals(correoAlumno.trim(), ignoreCase = true) }
            val nombreAlumno = alumno?.nombre ?: "Alumno ESUCAR"

            // Guardar en la base de datos Room
            val nuevaConsulta = ConsultaEntity(
                alumnoNombre = nombreAlumno,
                correoAlumno = correoAlumno.trim().lowercase(),
                asunto = asuntoActual,
                mensaje = mensajeActual,
                fecha = fechaActual
            )
            consultaDao.insertarConsulta(nuevaConsulta)

            _uiState.update {
                it.copy(
                    asunto = "",
                    mensaje = "",
                    asuntoError = null,
                    mensajeError = null,
                    enviado = true
                )
            }
            onSuccess(asuntoActual, mensajeActual)
        }

        return true
    }
}
