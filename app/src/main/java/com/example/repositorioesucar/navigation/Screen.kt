package com.example.repositorioesucar.navigation

sealed class Screen(val route: String) {
    // Autenticación
    object Login : Screen("login")
    object Register : Screen("register")

    // Portal Estudiante
    object Home : Screen("estudiante_home")
    object Materiales : Screen("materiales/{asignaturaId}") {
        fun createRoute(asignaturaId: Int) = "materiales/$asignaturaId"
    }
    object Progreso : Screen("progreso")
    object Consulta : Screen("consulta")
    object Profile : Screen("profile")

    // Portal Docente
    object DocenteHome : Screen("docente_home")
    object DocenteConsultas : Screen("docente_consultas")
}