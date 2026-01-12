package com.example.flashcardsproject.ui.windows

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.flashcardsproject.data.Flashcard
import com.example.flashcardsproject.data.FlashcardRepository

/**
 * Pantalla de visualización y estudio de tarjetas para un album específico.
 * Presenta una lista de tarjetas interactivas.
 * @param navController Controlador de navegación para gestionar el flujo de la aplicación.
 * @param albumId Identificador único del album que se desea consultar.
 */
@Composable
fun FlashcardListWindow(navController: NavHostController, albumId: Int) {
    // Recupera el album desde el repositorio central de datos.
    val album = remember { FlashcardRepository.getAlbumById(albumId) }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(modifier = Modifier.fillMaxSize()) {

            // Cabecera.
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Volver",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = album?.name ?: "Album no encontrado",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            // Si no hay tarjetas, notifica al usuario.
            if (album == null || album.cards.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "Este album está vacío.",
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                        )
                        Spacer(modifier = Modifier.height(16.dp))

                        Button(onClick = { navController.navigate("add_card/$albumId") }) {
                            Icon(Icons.Default.Add, contentDescription = null)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Añadir mi primera tarjeta")
                        }
                    }
                }
            } else {
                // Lista de las tarjetas existentes en el album.
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    items(album.cards) { flashcard ->
                        FlashcardItem(flashcard)
                    }

                    // Botón de inserción rápida al final de la lista.
                    item {
                        OutlinedButton(
                            onClick = { navController.navigate("add_card/$albumId") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 16.dp),
                            shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp)
                        ) {
                            Icon(Icons.Default.Add, contentDescription = null)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Añadir más tarjetas a este álbum")
                        }
                    }

                    item { Spacer(modifier = Modifier.height(32.dp)) }
                }
            }
        }
    }
}

/**
 * Representa una tarjeta individual con capacidad de rotación.
 * @param flashcard El objeto de datos que contiene el contenido frontal y posterior.
 */
@Composable
fun FlashcardItem(flashcard: Flashcard) {
    // Estado para controlar si la tarjeta está girada.
    var isFlipped by remember { mutableStateOf(false) }

    // Animación para el ángulo de rotación.
    val rotation by animateFloatAsState(
        targetValue = if (isFlipped) 180f else 0f,
        animationSpec = tween(durationMillis = 600)
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(280.dp)
            .clickable { isFlipped = !isFlipped } // Alternar estado al hacer clic.
            .graphicsLayer {
                rotationY = rotation // Aplica el giro en el eje vertical.
                cameraDistance = 12f * density // Define la perspectiva para un efecto 3D realista.
            },
        colors = CardDefaults.cardColors(
            containerColor = if (rotation > 90f)
                MaterialTheme.colorScheme.surfaceVariant
            else
                MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            // Carga condicional basada en la rotación (90 grados).
            if (rotation <= 90f) {
                // Cara frontal (Anverso).
                CardFaceContent(
                    content = flashcard.frontContent,
                    isImage = flashcard.isFrontImage
                )
            } else {
                // Cara posterior (Reverso) .
                // Invertimos la rotación interna.
                Box(Modifier.graphicsLayer { rotationY = 180f }) {
                    CardFaceContent(
                        content = flashcard.backContent,
                        isImage = flashcard.isBackImage
                    )
                }
            }
        }
    }
}

/**
 * Componente interno encargado de cargar el tipo de contenido dentro de la cara de una tarjeta.
 * @param content Cadena que contiene el texto o el URI de la imagen.
 * @param isImage Booleano que define la lógica de renderizado a aplicar.
 */
@Composable
fun CardFaceContent(content: String, isImage: Boolean) {
    if (isImage) {
        AsyncImage(
            model = content,
            contentDescription = "Imagen de la tarjeta",
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        )
    } else {
        Text(
            text = content,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(24.dp)
        )
    }
}