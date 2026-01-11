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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.flashcardsproject.data.FlashcardRepository

/**
 * Pantalla dedicada a la creación de nuevos álbumes de estudio.
 * Proporciona una interfaz sencilla para que el usuario nombre sus colecciones.
 *
 * @param navController Gestor de navegación para controlar el flujo de la aplicación.
 */
@Composable
fun CreateAlbumWindow(navController: NavHostController) {
    // Estado reactivo que almacena el texto introducido por el usuario en el campo de nombre
    var albumName by remember { mutableStateOf("") }

    // Lógica de validación: el nombre no debe estar vacío ni contener solo espacios
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
            // Acción de navegación para regresar a la pantalla anterior (pop de la pila)
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Volver",
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Título principal de la ventana
            Text(
                text = "Nuevo Álbum",
                style = MaterialTheme.typography.displaySmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )

            // Instrucciones secundarias con opacidad reducida para jerarquía visual
            Text(
                text = "Ponle un nombre a tu colección de tarjetas",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
            )

            Spacer(modifier = Modifier.height(48.dp))

            /**
             * Campo de texto de estilo contorneado (Outlined).
             * Incluye placeholders y etiquetas flotantes para mejorar la accesibilidad.
             */
            OutlinedTextField(
                value = albumName,
                onValueChange = { albumName = it },
                label = { Text("Nombre del álbum") },
                placeholder = { Text("Ej: Herramientas de Carpintería") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true, // Restringe la entrada a una sola línea para mayor claridad
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outline
                )
            )

            Spacer(modifier = Modifier.height(32.dp))

            /**
             * Botón de confirmación para guardar el álbum.
             * Propiedad 'enabled': se vincula dinámicamente a la validez del nombre para prevenir
             * la creación de registros vacíos en el repositorio.
             */
            Button(
                onClick = {
                    // Llamada al repositorio para persistir el nuevo álbum en memoria
                    FlashcardRepository.createAlbum(albumName)
                    // Navegación automática hacia atrás tras completar la acción con éxito
                    navController.popBackStack()
                },
                enabled = isNameValid, // Control de estado del botón basado en validación
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Icon(Icons.Default.Check, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Guardar Álbum",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}