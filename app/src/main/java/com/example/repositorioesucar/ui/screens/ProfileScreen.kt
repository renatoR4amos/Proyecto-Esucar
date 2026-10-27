package com.example.repositorioesucar.ui.screens

import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.repositorioesucar.data.local.AppDatabase
import com.example.repositorioesucar.ui.components.ImagenInteligente
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    correoUsuarioLogueado: String, // <--- Recibe el correo del usuario actual
    onNavigateBack: () -> Unit,
    onLogout: () -> Unit
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var photoUri by remember { mutableStateOf<Uri?>(null) }

    // Estados para guardar los datos reales leídos de Room
    var nombreReal by remember { mutableStateOf("Cargando...") }
    var correoReal by remember { mutableStateOf(correoUsuarioLogueado) }
    var rolReal by remember { mutableStateOf("...") }

    // Consultamos la base de datos local al abrir la pantalla
    LaunchedEffect(correoUsuarioLogueado) {
        scope.launch {
            val dao = AppDatabase.getDatabase(context).userDao()
            val lista = dao.obtenerTodosLosUsuarios()
            // Buscamos al usuario que coincide con el correo logueado
            val usuarioEncontrado = lista.find { it.correo.equals(correoUsuarioLogueado, ignoreCase = true) }

            if (usuarioEncontrado != null) {
                nombreReal = usuarioEncontrado.nombre
                correoReal = usuarioEncontrado.correo
                rolReal = usuarioEncontrado.rol
            } else {
                nombreReal = "Usuario no encontrado"
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mi Perfil") },
                navigationIcon = { TextButton(onClick = onNavigateBack) { Text("← Volver") } }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ImagenInteligente(
                imageUri = photoUri,
                onImageSelected = { photoUri = it }
            )
            Spacer(modifier = Modifier.height(16.dp))

            // DATOS REALES EXTRAÍDOS DE ROOM
            Text(nombreReal, style = MaterialTheme.typography.titleLarge)
            Text(correoReal, style = MaterialTheme.typography.bodyMedium)
            Text("Rol en el Sistema: $rolReal", style = MaterialTheme.typography.bodySmall)

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = onLogout,
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Logout,
                    contentDescription = "Cerrar Sesión",
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Cerrar Sesión (Log Out)")
            }
        }
    }
}