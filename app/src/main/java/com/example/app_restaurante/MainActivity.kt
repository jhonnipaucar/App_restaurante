package com.example.app_restaurante

import android.R.attr.type
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.app_restaurante.ui.theme.App_restauranteTheme
import com.example.app_restaurante.viewmodel.BiteBoxViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            App_restauranteTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation() // Aquí iniciamos nuestro mapa de navegación
                }
            }
        }
    }
}

@Composable
fun AppNavigation() {
    // 1. Creamos el controlador de navegación y traemos nuestro ViewModel
    val navController = rememberNavController()
    val viewModel: BiteBoxViewModel = viewModel()

    // 2. El NavHost es el mapa. Le decimos que empiece en la ruta "login"
    NavHost(navController = navController, startDestination = "login") {

        // PANTALLA 1: Login
        // PANTALLA 1: Login
        composable("login") {
            com.example.app_restaurante.screens.LoginScreen(navController)
        }

        // PANTALLA 2: Menú del Catálogo
        // Nota: Le indicamos que en su ruta va a recibir una variable tipo String llamada "nombreUsuario"
        // PANTALLA 2: Menú del Catálogo
        composable(
            route = "menu/{nombreUsuario}",
            arguments = listOf(navArgument("nombreUsuario") { type = NavType.StringType })
        ) { backStackEntry ->
            val nombre = backStackEntry.arguments?.getString("nombreUsuario") ?: ""
            com.example.app_restaurante.screens.MenuScreen(navController, viewModel, nombre)
        }

        // PANTALLA 3: Detalles del Platillo
        // Nota: Le indicamos que en su ruta va a recibir una variable tipo Entero (Int) llamada "platilloId"
        // PANTALLA 3: Detalles del platillo
        composable(
            route = "detalle/{platilloId}",
            arguments = listOf(navArgument("platilloId") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("platilloId") ?: 0
            com.example.app_restaurante.screens.DetalleScreen(navController, viewModel, id)
        }

        // PANTALLA 4: Carrito de compras
        composable("carrito") {
            // (Paso 6) Aquí irá el diseño del Carrito
        }
    }
}