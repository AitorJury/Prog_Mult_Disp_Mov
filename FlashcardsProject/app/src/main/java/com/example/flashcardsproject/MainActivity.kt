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

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        // Mantiene el diseño moderno pero permitiremos que el fondo fluya hasta arriba
        enableEdgeToEdge()

        FlashcardRepository.init(this)

        setContent {
            FlashcardsProjectTheme {
                // HEMOS QUITADO EL statusBarsPadding() DE AQUÍ.
                // Ahora el color de fondo será continuo hasta arriba.
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    MainNavigation()
                }
            }
        }
    }
}

@Composable
fun MainNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "init",
        enterTransition = { fadeIn(animationSpec = tween(400)) + slideInHorizontally() },
        exitTransition = { fadeOut(animationSpec = tween(400)) + slideOutHorizontally() }
    ) {
        composable("init") { HomeWindow(navController) }
        composable("info") { InfoWindow(navController) }
        composable("album_list") { AlbumListWindow(navController) }
        composable("create_album") { CreateAlbumWindow(navController) }

        // Ruta clásica: Estudiar tarjetas (con giro 3D)
        composable(
            route = "list/{albumId}",
            arguments = listOf(navArgument("albumId") { type = NavType.IntType })
        ) { backStackEntry ->
            val albumId = backStackEntry.arguments?.getInt("albumId") ?: 0
            FlashcardListWindow(navController, albumId)
        }

        // NUEVA RUTA: Galería de imágenes (Mirar imágenes con zoom/rotación)
        composable(
            route = "gallery/{albumId}",
            arguments = listOf(navArgument("albumId") { type = NavType.IntType })
        ) { backStackEntry ->
            val albumId = backStackEntry.arguments?.getInt("albumId") ?: 0
            GalleryViewWindow(navController, albumId)
        }

        composable(
            route = "add_card/{albumId}",
            arguments = listOf(navArgument("albumId") { type = NavType.IntType })
        ) { backStackEntry ->
            val albumId = backStackEntry.arguments?.getInt("albumId") ?: 0
            AddFlashcardWindow(navController, albumId)
        }
    }
}