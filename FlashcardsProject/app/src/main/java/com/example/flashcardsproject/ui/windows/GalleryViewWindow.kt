package com.example.flashcardsproject.ui.windows

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.flashcardsproject.data.FlashcardRepository

/**
 * Ventana de Galería: Muestra todas las imágenes de un álbum en cuadrícula de 4 columnas.
 * Permite visualización avanzada con gestos táctiles.
 */
@Composable
fun GalleryViewWindow(navController: NavHostController, albumId: Int) {
    val album = remember { FlashcardRepository.getAlbumById(albumId) }
    // Filtramos solo los contenidos que son imágenes
    val images = remember(album) {
        val list = mutableListOf<String>()
        album?.cards?.forEach {
            if (it.isFrontImage) list.add(it.frontContent)
            if (it.isBackImage) list.add(it.backContent)
        }
        list
    }

    // Estado para controlar qué imagen se está viendo en grande
    var selectedImageUrl by remember { mutableStateOf<String?>(null) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Cabecera con margen de seguridad
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                }
                Text(
                    text = "Imágenes de ${album?.name}",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }

            // Cuadrícula de 4 columnas
            LazyVerticalGrid(
                columns = GridCells.Fixed(4),
                contentPadding = PaddingValues(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(images) { imageUrl ->
                    AsyncImage(
                        model = imageUrl,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .aspectRatio(1f)
                            .clip(RoundedCornerShape(8.dp))
                            .clickable { selectedImageUrl = imageUrl }
                    )
                }
            }
        }
    }

    // Si hay una imagen seleccionada, mostramos el visor con gestos
    selectedImageUrl?.let { url ->
        FullScreenImageModal(url = url, onDismiss = { selectedImageUrl = null })
    }
}

/**
 * Diálogo a pantalla completa que permite manipular la imagen.
 */
@Composable
fun FullScreenImageModal(url: String, onDismiss: () -> Unit) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background.copy(alpha = 0.95f)),
            contentAlignment = Alignment.Center
        ) {
            ZoomableImage(url)

            // Botón para cerrar el visor
            Button(
                onClick = onDismiss,
                modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 48.dp)
            ) {
                Text("Cerrar")
            }
        }
    }
}

/**
 * Componente que gestiona Zoom, Pan (Mover) y Rotación.
 */
@Composable
fun ZoomableImage(url: String) {
    var scale by remember { mutableStateOf(1f) }
    var rotation by remember { mutableStateOf(0f) }
    var offset by remember { mutableStateOf(Offset.Zero) }

    val state = rememberTransformableState { zoomChange, offsetChange, rotationChange ->
        scale *= zoomChange
        rotation += rotationChange
        offset += offsetChange
    }



    AsyncImage(
        model = url,
        contentDescription = null,
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            // Aplicamos las transformaciones físicas
            .graphicsLayer(
                scaleX = maxOf(.5f, minOf(3f, scale)),
                scaleY = maxOf(.5f, minOf(3f, scale)),
                rotationZ = rotation,
                translationX = offset.x,
                translationY = offset.y
            )
            // Habilitamos la escucha de gestos múltiples
            .transformable(state = state)
    )
}