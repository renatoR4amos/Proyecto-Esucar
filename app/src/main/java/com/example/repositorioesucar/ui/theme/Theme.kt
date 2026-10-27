package com.example.repositorioesucar.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = VerdeInstitucional,
    secondary = DoradoCarabinero,
    tertiary = VerdeClaro,
    background = FondoGris,
    surface = Blanco,
    onPrimary = Blanco,
    onSecondary = TextoOscuro
)

@Composable
fun RepositorioESUCARTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        content = content
    )
}