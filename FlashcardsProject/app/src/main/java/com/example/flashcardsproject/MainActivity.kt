package com.example.flashcardsproject

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.flashcardsproject.data.FlashcardRepository
import com.example.flashcardsproject.ui.windows.*
import com.example.flashcardsproject.ui.theme.FlashcardsProjectTheme

/**
 * Punto de entrada principal de la aplicación.
 * Gestiona el ciclo de vida inicial, la configuración del sistema visual y la inicialización.
 */
class MainActivity : ComponentActivity() {

    // Configuración inicial al crear la actividad.
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        // Implementación de la pantalla de carga.
        installSplashScreen()
        super.onCreate(savedInstanceState)

        // Habilita el diseño Edge-to-Edge.
        enableEdgeToEdge()

        // Inicialización del repositorio y carga de datos.
        FlashcardRepository.init(this)

        setContent {
            FlashcardsProjectTheme {
                // Contenedor base de la aplicación.
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    MainNavigation()
                }
            }
        }
    }
}

/**
 * Orquestador de navegación de la aplicación.
 * Define todas las rutas, los argumentos necesarios y las transiciones.
 */
@Composable
fun MainNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "init",
        // Definición de transiciones.
        enterTransition = { fadeIn(animationSpec = tween(400)) + slideInHorizontally() },
        exitTransition = { fadeOut(animationSpec = tween(400)) + slideOutHorizontally() }
    ) {
        // Rutas básicas.
        composable("init") { HomeWindow(navController) }
        composable("info") { InfoWindow(navController) }
        composable("album_list") { AlbumListWindow(navController) }
        composable("create_album") { CreateAlbumWindow(navController) }

        // Ruta: Visualización y estudio de tarjetas.
        composable(
            route = "list/{albumId}",
            arguments = listOf(navArgument("albumId") { type = NavType.IntType })
        ) { backStackEntry ->
            val albumId = backStackEntry.arguments?.getInt("albumId") ?: 0
            FlashcardListWindow(navController, albumId)
        }

        // Ruta: Galería de imágenes.
        composable(
            route = "gallery/{albumId}",
            arguments = listOf(navArgument("albumId") { type = NavType.IntType })
        ) { backStackEntry ->
            val albumId = backStackEntry.arguments?.getInt("albumId") ?: 0
            GalleryViewWindow(navController, albumId)
        }

        // Ruta: Editor de tarjetas.
        composable(
            route = "add_card/{albumId}",
            arguments = listOf(navArgument("albumId") { type = NavType.IntType })
        ) { backStackEntry ->
            val albumId = backStackEntry.arguments?.getInt("albumId") ?: 0
            AddFlashcardWindow(navController, albumId)
        }
    }
}