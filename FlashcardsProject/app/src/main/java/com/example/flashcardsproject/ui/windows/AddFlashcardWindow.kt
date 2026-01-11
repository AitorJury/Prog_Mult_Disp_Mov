package com.example.flashcardsproject.ui.windows

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.flashcardsproject.R
import com.example.flashcardsproject.data.FlashcardRepository

/**
 * Pantalla de formulario para la creación de nuevas tarjetas de estudio.
 * Permite al usuario seleccionar un icono visual y asignar un nombre descriptivo.
 * * @param navController Controlador de navegación para gestionar el flujo de pantallas.
 * @param albumId Identificador del álbum destino donde se insertará la tarjeta.
 */
@Composable
fun AddFlashcardWindow(navController: NavHostController, albumId: Int) {
    // Estado local para capturar la descripción escrita por el usuario
    var backText by remember { mutableStateOf("") }

    // Lista de recursos gráficos disponibles para representar la herramienta
    val availableImages = listOf(R.drawable.image1, R.drawable.image2, R.drawable.image3)

    // Estado local para rastrear qué imagen de la cuadrícula ha seleccionado el usuario
    var selectedImage by remember { mutableStateOf(availableImages[0]) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {
            // Botón de retroceso para cancelar la operación y volver a la vista anterior
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Volver",
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Nueva Tarjeta",
                style = MaterialTheme.typography.displaySmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "1. Selecciona una imagen",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(16.dp))

            /**
             * Cuadrícula de selección de iconos.
             * Utiliza LazyVerticalGrid para optimizar el rendimiento y organizar los elementos en 3 columnas.
             */
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(110.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(availableImages) { img ->
                    val isSelected = selectedImage == img
                    Box(
                        modifier = Modifier
                            .aspectRatio(1f) // Mantiene los contenedores perfectamente cuadrados
                            .clip(RoundedCornerShape(12.dp))
                            .background(
                                // Feedback visual: cambia el fondo si está seleccionado
                                if (isSelected) MaterialTheme.colorScheme.primaryContainer
                                else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
                            )
                            .border(
                                width = 2.dp,
                                color = if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent,
                                shape = RoundedCornerShape(12.dp)
                            )
                            .clickable { selectedImage = img }, // Actualiza el estado al hacer clic
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = img),
                            contentDescription = null,
                            modifier = Modifier.padding(12.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "2. Nombre de la herramienta",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Campo de entrada de texto para la solución o descripción de la flashcard
            OutlinedTextField(
                value = backText,
                onValueChange = { backText = it },
                label = { Text("Texto del reverso") },
                placeholder = { Text("Ej: Llave Inglesa") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(16.dp)
            )

            Spacer(modifier = Modifier.weight(1f)) // Empuja el botón hacia la parte inferior

            /**
             * Botón de confirmación.
             * Lógica de negocio: solo se habilita si el campo de texto contiene información.
             */
            Button(
                onClick = {
                    // Persiste la nueva tarjeta en el repositorio global
                    FlashcardRepository.addFlashcardToAlbum(albumId, selectedImage, backText)
                    // Cierra la ventana y regresa a la lista de álbumes
                    navController.popBackStack()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                enabled = backText.isNotBlank(), // Validación básica de formulario
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = "Añadir al álbum",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}