package com.example.segundamanoproject

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.segundamanoproject.ui.pantallas.PantallaInicio
import com.example.segundamanoproject.ui.theme.SegundaManoProjectTheme

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SegundaManoProjectTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    ObjectApp()
                }
            }
        }
    }
}

@Composable
fun ObjectApp() {
    // Controlador de navegación
    val navController = rememberNavController()
    // Crear contenedor de navegacion: definir las rutas
    NavHost(navController = navController, startDestination = "inicio"){
        composable("inicio") {PantallaInicio(navController)}
    }
}
