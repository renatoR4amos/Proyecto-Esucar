package com.example.repositorioesucar.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.repositorioesucar.ui.screens.*

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {
        // Auth
        composable(Screen.Login.route) {
            LoginScreen(
                onNavigateToRegister = { navController.navigate(Screen.Register.route) },
                onLoginSuccess = { correo, rol ->
                    if (correo.equals("admin@esucar.cl", ignoreCase = true)) {
                        navController.navigate("admin_screen") {
                            popUpTo(Screen.Login.route) { inclusive = true }
                        }
                    } else if (rol == "DOCENTE") {
                        navController.navigate("docente_home/$correo") {
                            popUpTo(Screen.Login.route) { inclusive = true }
                        }
                    } else {
                        navController.navigate("student_home/$correo") {
                            popUpTo(Screen.Login.route) { inclusive = true }
                        }
                    }
                }
            )
        }

        composable(Screen.Register.route) {
            RegisterScreen(
                onNavigateBack = { navController.popBackStack() },
                onRegisterSuccess = { navController.popBackStack() }
            )
        }

        composable("admin_screen") {
            AdminScreen(
                onLogout = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }

        // Estudiante Home
        composable(
            route = "student_home/{correo}",
            arguments = listOf(navArgument("correo") { type = NavType.StringType })
        ) { backStackEntry ->
            val correo = backStackEntry.arguments?.getString("correo") ?: ""
            HomeScreen(
                correoUsuarioLogueado = correo,
                onNavigateToMateriales = { id -> navController.navigate(Screen.Materiales.createRoute(id)) },
                onNavigateToProfile = { navController.navigate("profile_screen/$correo") }
            )
        }

        // Docente Home
        composable(
            route = "docente_home/{correo}",
            arguments = listOf(navArgument("correo") { type = NavType.StringType })
        ) { backStackEntry ->
            val correo = backStackEntry.arguments?.getString("correo") ?: ""
            DocenteHomeScreen(
                onNavigateToConsultas = { navController.navigate(Screen.DocenteConsultas.route) },
                onNavigateToProfile = { navController.navigate("profile_screen/$correo") },
                onLogout = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }

        // Perfil Dinámico
        composable(
            route = "profile_screen/{correo}",
            arguments = listOf(navArgument("correo") { type = NavType.StringType })
        ) { backStackEntry ->
            val correoUser = backStackEntry.arguments?.getString("correo") ?: ""
            ProfileScreen(
                correoUsuarioLogueado = correoUser,
                onNavigateBack = { navController.popBackStack() },
                onLogout = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }

        composable(
            route = Screen.Materiales.route,
            arguments = listOf(navArgument("asignaturaId") { type = NavType.IntType })
        ) { backStackEntry ->
            val asigId = backStackEntry.arguments?.getInt("asignaturaId") ?: 1
            MaterialesScreen(
                asignaturaId = asigId,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(Screen.Progreso.route) {
            ProgresoScreen(onNavigateBack = { navController.popBackStack() })
        }

        // Consulta Dinámica con correo del alumno
        composable(
            route = "consulta_screen/{correo}",
            arguments = listOf(navArgument("correo") { type = NavType.StringType })
        ) { backStackEntry ->
            val correo = backStackEntry.arguments?.getString("correo") ?: ""
            ConsultaScreen(
                correoUsuario = correo,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(Screen.Consulta.route) {
            ConsultaScreen(
                correoUsuario = "",
                onNavigateBack = { navController.popBackStack() }
            )
        }

        // Bandeja de Consultas del Docente (lee todas las consultas de Room)
        composable(Screen.DocenteConsultas.route) {
            DocenteConsultasScreen(onNavigateBack = { navController.popBackStack() })
        }
    }
}
