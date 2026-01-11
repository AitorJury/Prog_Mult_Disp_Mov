package com.example.flashcardsproject.ui.windows

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.flashcardsproject.data.Flashcard
import com.example.flashcardsproject.data.FlashcardRepository

/**
 * Ventana de estudio que visualiza las tarjetas de un álbum específico.
 * Implementa una lógica de carga dinámica basada en el ID del álbum recibido.
 */
@Composable
fun FlashcardListWindow(navController: NavHostController, albumId: Int) {
    // Recupera el álbum del repositorio. Se usa 'remember' para evitar búsquedas innecesarias en cada recomposición.
    val album = remember { FlashcardRepository.getAlbumById(albumId) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Cabecera dinámica que muestra el nombre del álbum actual
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
                    text = album?.name ?: "Álbum no encontrado",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            /**
             * Lógica de estado vacío: Si el álbum no existe o no tiene tarjetas,
             * se muestra un mensaje informativo en lugar de una pantalla vacía.
             */
            if (album == null || album.cards.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Este álbum está vacío.\n¡Añade algunas tarjetas!",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                    )
                }
            } else {
                // Listado vertical de tarjetas con espaciado definido
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    items(album.cards) { flashcard ->
                        FlashcardItem(flashcard)
                    }
                }
            }
        }
    }
}

/**
 * Componente individual de Flashcard con capacidad de rotación 3D.
 * Utiliza transiciones de estado para animar el giro de la tarjeta.
 */
@Composable
fun FlashcardItem(flashcard: Flashcard) {
    // Estado booleano para controlar si la tarjeta está volteada
    var isFlipped by remember { mutableStateOf(false) }

    // Animación suavizada del valor de rotación (0 a 180 grados)
    val rotation by animateFloatAsState(
        targetValue = if (isFlipped) 180f else 0f,
        animationSpec = tween(durationMillis = 600) // Duración de la transición en milisegundos
    )



    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(280.dp)
            .clickable { isFlipped = !isFlipped } // Alterna el estado al hacer clic
            .graphicsLayer {
                // Aplicación de la rotación en el eje Y
                rotationY = rotation
                // Define la distancia de la cámara para que el efecto 3D sea realista y no plano
                cameraDistance = 12f * density
            },
        colors = CardDefaults.cardColors(
            // Cambio dinámico de color según el grado de rotación para simular iluminación/profundidad
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
            /**
             * Decisión de renderizado:
             * Si la rotación es <= 90°, mostramos el anverso (Imagen).
             * Si es > 90°, mostramos el reverso (Texto).
             */
            if (rotation <= 90f) {
                Image(
                    painter = painterResource(id = flashcard.frontImage),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp)
                )
            } else {
                /**
                 * Nota técnica: Al rotar la tarjeta 180°, el texto aparecería en espejo.
                 * Se aplica una rotación inversa de 180° en el eje Y al texto para compensarlo.
                 */
                Text(
                    text = flashcard.backText,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .graphicsLayer { rotationY = 180f }
                        .padding(24.dp)
                )
            }
        }
    }
}