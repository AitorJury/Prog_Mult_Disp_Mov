package com.example.flashcardsproject.ui.windows

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.flashcardsproject.data.FlashcardRepository
import com.example.flashcardsproject.ui.theme.AppSizes

/**
 * Pantalla de creación de nuevos albumes.
 * @param navController Controlador de navegación para regresar a la pantalla anterior tras guardar.
 */
@Composable
fun CreateAlbumWindow(navController: NavHostController) {
    // Contexto local para realizar operaciones de escritura en SharedPreferences.
    val context = LocalContext.current

    // Estado reactivo que almacena el texto introducido por el usuario.
    var albumName by remember { mutableStateOf("") }

    // El nombre no puede estar vacío ni contener solo espacios.
    val isNameValid = albumName.isNotBlank()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(AppSizes.screenPadding)
        ) {
            // Acción de navegación hacia atrás.
            IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier.offset(x = (-12).dp)
                ) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Volver",
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Cabecera.
            Text(
                text = "Nuevo Album",
                style = MaterialTheme.typography.titleLarge.copy(fontSize = 28.sp),
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )

            Text(
                text = "Ponle un nombre a tu colección de tarjetas",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
            )

            Spacer(modifier = Modifier.height(40.dp))

            // Campo de entrada de texto principal.
            OutlinedTextField(
                value = albumName,
                onValueChange = { albumName = it },
                label = { Text("Nombre del album") },
                placeholder = { Text("Ej: Vocabulario Inglés") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(16.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Botón de confirmación.
            Button(
                onClick = {
                    // Sincronización de datos con el almacenamiento local.
                    FlashcardRepository.createAlbum(albumName, context)
                    navController.popBackStack()
                },
                enabled = isNameValid, // Control de estado para evitar entradas vacías.
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Icon(Icons.Default.Check, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Guardar Album",
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}