package com.example.repositorioesucar.ui.screens

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.repositorioesucar.ui.components.NotificationHelper
import com.example.repositorioesucar.viewmodel.ConsultaViewModel

@Composable
fun ConsultaContent(
    correoUsuario: String,
    modifier: Modifier = Modifier,
    viewModel: ConsultaViewModel = viewModel()
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()
    val misConsultas by viewModel.misConsultas.collectAsState()

    LaunchedEffect(correoUsuario) {
        if (correoUsuario.isNotEmpty()) {
            viewModel.cargarConsultasAlumno(correoUsuario)
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Nueva Consulta",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = uiState.asunto,
            onValueChange = { viewModel.onAsuntoChange(it) },
            label = { Text("Asunto o Unidad Temática") },
            isError = uiState.asuntoError != null,
            supportingText = {
                if (uiState.asuntoError != null) {
                    Text(uiState.asuntoError!!, color = MaterialTheme.colorScheme.error)
                }
            },
            trailingIcon = {
                if (uiState.asuntoError != null) {
                    Icon(
                        imageVector = Icons.Default.Error,
                        contentDescription = "Error en asunto",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(6.dp))

        OutlinedTextField(
            value = uiState.mensaje,
            onValueChange = { viewModel.onMensajeChange(it) },
            label = { Text("Detalle de la duda pedagógica") },
            isError = uiState.mensajeError != null,
            supportingText = {
                if (uiState.mensajeError != null) {
                    Text(uiState.mensajeError!!, color = MaterialTheme.colorScheme.error)
                }
            },
            trailingIcon = {
                if (uiState.mensajeError != null) {
                    Icon(
                        imageVector = Icons.Default.Error,
                        contentDescription = "Error en mensaje",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            },
            modifier = Modifier.fillMaxWidth(),
            minLines = 3
        )
        Spacer(modifier = Modifier.height(10.dp))

        AnimatedVisibility(
            visible = uiState.enviado,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
            ) {
                Text(
                    text = "Consulta registrada en el sistema y despachada al docente.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier.padding(10.dp)
                )
            }
        }

        Button(
            onClick = {
                viewModel.enviarConsulta(correoAlumno = correoUsuario) { asuntoEnviado, _ ->
                    NotificationHelper.mostrarNotificacion(
                        context = context,
                        titulo = "Consulta enviada al docente",
                        mensaje = "Se registró la consulta sobre: $asuntoEnviado"
                    )
                    Toast.makeText(
                        context,
                        "Mensaje enviado. Funcionalidad completa disponible para una futura actualización",
                        Toast.LENGTH_LONG
                    ).show()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Enviar Consulta")
        }

        Spacer(modifier = Modifier.height(14.dp))
        HorizontalDivider()
        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Mis Consultas Enviadas (${misConsultas.size})",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(6.dp))

        if (misConsultas.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Aún no has registrado consultas.",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth().weight(1f)
            ) {
                items(misConsultas) { consulta ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = consulta.asunto,
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = consulta.fecha,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = consulta.mensaje,
                                style = MaterialTheme.typography.bodySmall
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            SuggestionChip(
                                onClick = { },
                                label = { Text("Recibido por Docente") }
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConsultaScreen(
    correoUsuario: String = "",
    onNavigateBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Consultas con el Docente") },
                navigationIcon = { TextButton(onClick = onNavigateBack) { Text("← Volver") } }
            )
        }
    ) { padding ->
        ConsultaContent(
            correoUsuario = correoUsuario,
            modifier = Modifier.padding(padding)
        )
    }
}
