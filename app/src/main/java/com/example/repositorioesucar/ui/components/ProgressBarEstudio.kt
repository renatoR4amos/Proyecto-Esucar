package com.example.repositorioesucar.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ProgressBarEstudio(
    completados: Int,
    total: Int,
    modifier: Modifier = Modifier
) {
    val progreso = if (total > 0) completados.toFloat() / total.toFloat() else 0f
    val porcentaje = (progreso * 100).toInt()

    Column(modifier = modifier.fillMaxWidth().padding(16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Avance de Estudio", style = MaterialTheme.typography.titleMedium)
            Text("$porcentaje%", style = MaterialTheme.typography.titleMedium)
        }
        Spacer(modifier = Modifier.height(8.dp))
        LinearProgressIndicator(
            progress = { progreso },
            modifier = Modifier.fillMaxWidth().height(10.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            "$completados de $total lecturas realizadas",
            style = MaterialTheme.typography.bodySmall
        )
    }
}