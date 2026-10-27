package com.example.repositorioesucar.ui.screens

import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.repositorioesucar.model.AsignaturaMock

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    correoUsuarioLogueado: String = "",
    onNavigateToMateriales: (Int) -> Unit,
    onNavigateToProfile: () -> Unit
) {
    var currentTab by remember { mutableStateOf(0) } // 0: Materias, 1: Progreso, 2: Consultas
    var semestreSeleccionado by remember { mutableStateOf(1) }
    val asignaturas = AsignaturaMock.listaAsignaturas.filter { it.semestre == semestreSeleccionado }

    // Solicitar permiso de notificaciones para Android 13+ (Recurso Nativo)
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { }

    LaunchedEffect(Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    val tituloBarra = when (currentTab) {
        0 -> "ESUCAR - Mis Asignaturas"
        1 -> "ESUCAR - Mi Progreso"
        else -> "ESUCAR - Consultas Docentes"
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(tituloBarra) },
                actions = {
                    IconButton(onClick = onNavigateToProfile) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Ir a Perfil",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = (currentTab == 0),
                    onClick = { currentTab = 0 },
                    label = { Text("Materias") },
                    icon = { Icon(Icons.Default.MenuBook, contentDescription = "Materias") }
                )
                NavigationBarItem(
                    selected = (currentTab == 1),
                    onClick = { currentTab = 1 },
                    label = { Text("Progreso") },
                    icon = { Icon(Icons.Default.BarChart, contentDescription = "Progreso") }
                )
                NavigationBarItem(
                    selected = (currentTab == 2),
                    onClick = { currentTab = 2 },
                    label = { Text("Consultas") },
                    icon = { Icon(Icons.Default.Email, contentDescription = "Consultas") }
                )
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .animateContentSize()
        ) {
            when (currentTab) {
                0 -> {
                    // PESTAÑA 0: ASIGNATURAS Y SEMESTRES
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        TabRow(selectedTabIndex = semestreSeleccionado - 1) {
                            for (s in 1..4) {
                                Tab(
                                    selected = semestreSeleccionado == s,
                                    onClick = { semestreSeleccionado = s },
                                    text = { Text("Sem $s") }
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            items(asignaturas) { asig ->
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable { onNavigateToMateriales(asig.id) }
                                ) {
                                    Column(modifier = Modifier.padding(16.dp)) {
                                        Text(asig.codigo, style = MaterialTheme.typography.labelSmall)
                                        Text(asig.nombre, style = MaterialTheme.typography.titleMedium)
                                        Text("Área: ${asig.area}", style = MaterialTheme.typography.bodySmall)
                                    }
                                }
                            }
                        }
                    }
                }
                1 -> {
                    // PESTAÑA 1: PROGRESO ACADÉMICO
                    ProgresoContent()
                }
                2 -> {
                    // PESTAÑA 2: CONSULTAS BIDIRECCIONALES CON ROOM
                    ConsultaContent(correoUsuario = correoUsuarioLogueado)
                }
            }
        }
    }
}
