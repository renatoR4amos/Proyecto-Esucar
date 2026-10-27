package com.example.repositorioesucar.ui.screens

import android.widget.Toast
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.repositorioesucar.model.AsignaturaMock
import com.example.repositorioesucar.ui.components.NotificationHelper

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MaterialesScreen(
    asignaturaId: Int,
    onNavigateBack: () -> Unit
) {
    val context = LocalContext.current
    val asignatura = AsignaturaMock.listaAsignaturas.find { it.id == asignaturaId }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(asignatura?.nombre ?: "Materiales") },
                navigationIcon = {
                    TextButton(onClick = onNavigateBack) { Text("← Volver") }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Text("Unidades y Recursos Disponibles", style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(12.dp))

            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                asignatura?.unidades?.let { unidades ->
                    items(unidades) { unidad ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .animateContentSize() // Animación de tamaño fluida
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(unidad.nombre, style = MaterialTheme.typography.titleMedium)
                                Text(unidad.descripcion, style = MaterialTheme.typography.bodySmall)
                                Spacer(modifier = Modifier.height(8.dp))

                                unidad.materiales.forEach { mat ->
                                    Row(
                                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text("• ${mat.titulo} (${mat.tipo})")
                                        Button(
                                            onClick = {
                                                // Recurso Nativo 2: Notificación
                                                NotificationHelper.mostrarNotificacion(
                                                    context = context,
                                                    titulo = "Descarga de Material",
                                                    mensaje = "Se descargó: ${mat.titulo} para uso offline."
                                                )
                                                Toast.makeText(
                                                    context,
                                                    "Descarga iniciada. Modo offline disponible.",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                        ) {
                                            Text("Descargar")
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
