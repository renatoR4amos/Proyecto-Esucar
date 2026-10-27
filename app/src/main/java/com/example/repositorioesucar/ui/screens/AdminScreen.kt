package com.example.repositorioesucar.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.repositorioesucar.data.local.AppDatabase
import com.example.repositorioesucar.data.local.UserEntity
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminScreen(
    onLogout: () -> Unit // Esta función ya limpia el historial y te regresa al Login
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var listaUsuarios by remember { mutableStateOf<List<UserEntity>>(emptyList()) }

    // Al abrir la pantalla, cargamos todos los usuarios desde la base de datos de Room
    LaunchedEffect(Unit) {
        scope.launch {
            val dao = AppDatabase.getDatabase(context).userDao()
            listaUsuarios = dao.obtenerTodosLosUsuarios()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Panel Administrador - Base de Datos") },
                actions = {
                    // Botón superior para regresar / cerrar sesión
                    IconButton(onClick = onLogout) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Logout,
                            contentDescription = "Cerrar Sesión / Regresar",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
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
            Text(
                text = "Usuarios Registrados en el Sistema",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(12.dp))

            // Cabecera de la Tabla
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Nombre", fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                Text("Correo", fontWeight = FontWeight.Bold, modifier = Modifier.weight(1.2f))
                Text("Rol", fontWeight = FontWeight.Bold, modifier = Modifier.weight(0.8f))
            }

            Spacer(modifier = Modifier.height(4.dp))

            // Filas con los datos de cada usuario obtenido de Room
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                items(listaUsuarios) { user ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(user.nombre, modifier = Modifier.weight(1f), style = MaterialTheme.typography.bodyMedium)
                            Text(user.correo, modifier = Modifier.weight(1.2f), style = MaterialTheme.typography.bodySmall)

                            Surface(
                                modifier = Modifier.weight(0.8f),
                                color = if (user.rol == "DOCENTE") Color(0xFFD0E1F9) else Color(0xFFD9F8C4),
                                shape = MaterialTheme.shapes.small
                            ) {
                                Text(
                                    text = user.rol,
                                    modifier = Modifier.padding(4.dp),
                                    style = MaterialTheme.typography.labelSmall,
                                    color = Color.Black
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Botón adicional abajo por si prefieres un botón grande de "Cerrar Sesión / Regresar"
            Button(
                onClick = onLogout,
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Logout,
                    contentDescription = "Regresar",
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Cerrar Sesión y Regresar")
            }
        }
    }
}