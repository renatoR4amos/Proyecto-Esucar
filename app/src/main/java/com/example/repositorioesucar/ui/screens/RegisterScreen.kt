package com.example.repositorioesucar.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.repositorioesucar.viewmodel.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    onNavigateBack: () -> Unit,
    onRegisterSuccess: () -> Unit,
    viewModel: AuthViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var passwordVisible by remember { mutableStateOf(false) }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Registro de Usuario") }) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = uiState.nombre,
                onValueChange = { viewModel.onNombreChange(it) },
                label = { Text("Nombre Completo") },
                isError = uiState.mensajeError != null && uiState.nombre.isEmpty(),
                supportingText = {
                    if (uiState.mensajeError != null && uiState.nombre.isEmpty()) {
                        Text("El nombre es obligatorio", color = MaterialTheme.colorScheme.error)
                    }
                },
                trailingIcon = {
                    if (uiState.mensajeError != null && uiState.nombre.isEmpty()) {
                        Icon(Icons.Default.Error, "Error", tint = MaterialTheme.colorScheme.error)
                    }
                },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = uiState.correo,
                onValueChange = { viewModel.onCorreoChange(it) },
                label = { Text("Correo Electrónico") },
                isError = uiState.mensajeError != null && (uiState.correo.isEmpty() || !uiState.correo.contains("@")),
                supportingText = {
                    if (uiState.mensajeError != null && (uiState.correo.isEmpty() || !uiState.correo.contains("@"))) {
                        Text("Correo inválido o sin '@'", color = MaterialTheme.colorScheme.error)
                    }
                },
                trailingIcon = {
                    if (uiState.mensajeError != null && (uiState.correo.isEmpty() || !uiState.correo.contains("@"))) {
                        Icon(Icons.Default.Error, "Error", tint = MaterialTheme.colorScheme.error)
                    }
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    capitalization = KeyboardCapitalization.None
                ),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = uiState.clave,
                onValueChange = { viewModel.onClaveChange(it) },
                label = { Text("Contraseña") },
                isError = uiState.mensajeError != null && uiState.clave.isEmpty(),
                supportingText = {
                    if (uiState.mensajeError != null && uiState.clave.isEmpty()) {
                        Text("La contraseña es obligatoria", color = MaterialTheme.colorScheme.error)
                    }
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    capitalization = KeyboardCapitalization.None
                ),
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val image = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(imageVector = image, contentDescription = "Ver contraseña")
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Selector de Alumno / Docente
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier.selectable(
                        selected = (uiState.rol == "ESTUDIANTE"),
                        onClick = { viewModel.onRolChange("ESTUDIANTE") }
                    ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = (uiState.rol == "ESTUDIANTE"),
                        onClick = { viewModel.onRolChange("ESTUDIANTE") }
                    )
                    Text("Alumno", modifier = Modifier.padding(start = 4.dp))
                }

                Spacer(modifier = Modifier.width(24.dp))

                Row(
                    modifier = Modifier.selectable(
                        selected = (uiState.rol == "DOCENTE"),
                        onClick = { viewModel.onRolChange("DOCENTE") }
                    ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = (uiState.rol == "DOCENTE"),
                        onClick = { viewModel.onRolChange("DOCENTE") }
                    )
                    Text("Docente", modifier = Modifier.padding(start = 4.dp))
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Mensaje de error dinámico con animación fluida
            AnimatedVisibility(
                visible = uiState.mensajeError != null,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Text(
                    text = uiState.mensajeError ?: "",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            Button(
                onClick = {
                    viewModel.registrar {
                        viewModel.limpiarCampos()
                        onRegisterSuccess()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                if (uiState.estaCargando) {
                    CircularProgressIndicator(modifier = Modifier.size(24.dp), color = MaterialTheme.colorScheme.onPrimary)
                } else {
                    Text("Confirmar Registro")
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            TextButton(onClick = {
                viewModel.limpiarCampos()
                onNavigateBack()
            }) {
                Text("Volver al Inicio de Sesión")
            }
        }
    }
}
