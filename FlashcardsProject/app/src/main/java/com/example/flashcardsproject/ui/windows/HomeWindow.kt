package com.example.flashcardsproject.ui.windows

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.flashcardsproject.ui.theme.AppSizes

/**
 * Pantalla de menú principal de la aplicación.
 * Presenta las opciones de la aplicación.
 * @param navController Controlador de navegación para redirigir a las diferentes secciones.
 */
@Composable
fun HomeWindow(navController: NavHostController) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                // Inserción de márgenes para la barra de estado.
                .verticalScroll(rememberScrollState())
                .padding(AppSizes.screenPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Espaciador superiorpara el título.
            Spacer(modifier = Modifier.height(60.dp))

            // Título principal de la aplicación.
            Text(
                text = "Gestor de\nFlashcards",
                style = MaterialTheme.typography.titleLarge.copy(fontSize = 32.sp),
                fontWeight = FontWeight.Black,
                lineHeight = 38.sp,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Eslogan de la aplicación.
            Text(
                text = "Organiza tu aprendizaje",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
            )

            Spacer(modifier = Modifier.height(56.dp))

            // Contenedor de acciones principales.
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
                )
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    HomeButton("Revisar Álbumes", Icons.Default.List) {
                        navController.navigate("album_list")
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    HomeButton("Crear Nuevo Álbum", Icons.Default.Add) {
                        navController.navigate("create_album")
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    OutlinedButton(
                        onClick = { navController.navigate("info") },
                        modifier = Modifier.fillMaxWidth().height(56.dp),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Icon(Icons.Default.Info, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "Créditos", style = MaterialTheme.typography.labelLarge)
                    }
                }
            }
            // Espaciador inferior.
            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Composable
fun HomeButton(text: String, icon: androidx.compose.ui.graphics.vector.ImageVector, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth().height(56.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Icon(icon, contentDescription = null)
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = text, style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold)
    }
}