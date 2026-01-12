package com.example.flashcardsproject.ui.windows

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.navigation.NavHostController
import com.example.flashcardsproject.data.FlashcardRepository

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
                .padding(24.dp)
        ) {
            // Acción de navegación hacia atrás.
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Volver",
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Cabecera.
            Text(
                text = "Nuevo Album",
                style = MaterialTheme.typography.displaySmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )

            Text(
                text = "Ponle un nombre a tu colección de tarjetas",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
            )

            Spacer(modifier = Modifier.height(48.dp))

            // Campo de entrada de texto principal.
            OutlinedTextField(
                value = albumName,
                onValueChange = { albumName = it },
                label = { Text("Nombre del album") },
                placeholder = { Text("Ej: Herramientas de Carpintería") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outline
                )
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
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}