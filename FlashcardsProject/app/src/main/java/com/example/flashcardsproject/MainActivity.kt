package com.example.flashcardsproject

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.flashcardsproject.ui.windows.*
import com.example.flashcardsproject.ui.theme.FlashcardsProjectTheme

/**
 * Actividad principal de la aplicación.
 * Punto de entrada único (Single Activity Architecture) que aloja toda la interfaz de usuario.
 */
class MainActivity : ComponentActivity() {
    /**
     * Método de ciclo de vida donde se inicializa la interfaz de usuario.
     */
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Aplicación del tema global personalizado
            FlashcardsProjectTheme {
                // Contenedor base que proporciona la estructura visual de Material Design
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    // Inicia el grafo de navegación principal
                    MainNavigation()
                }
            }
        }
    }
}

/**
 * Orquestador de navegación de la aplicación (NavHost).
 * Define todas las rutas, transiciones animadas y lógica de paso de argumentos.
 */
@Composable
fun MainNavigation() {
    // Gestor del estado de navegación y la pila de retroceso (backstack)
    val navController = rememberNavController()



    /**
     * NavHost: Contenedor que permite intercambiar los Composable (ventanas) según la ruta activa.
     * Se definen animaciones de entrada y salida globales para una experiencia fluida.
     */
    NavHost(
        navController = navController,
        startDestination = "init", // Pantalla de inicio predeterminada
        enterTransition = {
            // Combinación de desvanecimiento y desplazamiento horizontal para la entrada
            fadeIn(animationSpec = tween(400)) + slideInHorizontally()
        },
        exitTransition = {
            // Combinación de desvanecimiento y desplazamiento horizontal para la salida
            fadeOut(animationSpec = tween(400)) + slideOutHorizontally()
        }
    ) {
        // --- RUTAS SIMPLES ---

        // Pantalla de Inicio (Menú Principal)
        composable("init") { HomeWindow(navController) }

        // Pantalla de Créditos e Información
        composable("info") { InfoWindow(navController) }

        // Listado General de Álbumes
        composable("album_list") { AlbumListWindow(navController) }

        // Formulario para crear nuevos álbumes
        composable("create_album") { CreateAlbumWindow(navController) }

        // --- RUTAS CON PARÁMETROS DINÁMICOS ---

        /**
         * Ruta de estudio: Recibe 'albumId' como argumento obligatorio.
         * Se define explícitamente el tipo Int para garantizar la integridad de los datos.
         */
        composable(
            route = "list/{albumId}",
            arguments = listOf(navArgument("albumId") { type = NavType.IntType })
        ) { backStackEntry ->
            // Extracción del parámetro desde la entrada de navegación
            val albumId = backStackEntry.arguments?.getInt("albumId") ?: 0
            FlashcardListWindow(navController, albumId)
        }

        /**
         * Ruta para añadir tarjetas: Vincula la nueva tarjeta al álbum correspondiente mediante ID.
         */
        composable(
            route = "add_card/{albumId}",
            arguments = listOf(navArgument("albumId") { type = NavType.IntType })
        ) { backStackEntry ->
            val albumId = backStackEntry.arguments?.getInt("albumId") ?: 0
            AddFlashcardWindow(navController, albumId)
        }
    }
}