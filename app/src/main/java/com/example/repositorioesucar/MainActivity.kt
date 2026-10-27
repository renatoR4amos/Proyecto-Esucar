package com.example.repositorioesucar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.repositorioesucar.navigation.AppNavigation
import com.example.repositorioesucar.ui.theme.RepositorioESUCARTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RepositorioESUCARTheme {
                val navController = rememberNavController()
                AppNavigation(navController = navController)
            }
        }
    }
}