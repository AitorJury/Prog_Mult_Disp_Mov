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
import com.example.flashcardsproject.ui.theme.AppSizes

/**
 * Pantalla de Galería de Imágenes.
 * Extrae y presenta todas las imágenes contenidas en las tarjetas de un album.
 * @param navController Controlador de navegación para el retorno a la pantalla anterior.
 * @param albumId Identificador del album del cual se extraen las imágenes.
 */
@Composable
fun GalleryViewWindow(navController: NavHostController, albumId: Int) {
    // Referencia al album recuperado del repositorio.
    val album = remember { FlashcardRepository.getAlbumById(albumId) }

    /**
     * Lista procesada de URIs. Filtra el contenido del album para obtener
     * únicamente las rutas que corresponden a imágenes (frontales o posteriores).
     */
    val images = remember(album) {
        val list = mutableListOf<String>()
        album?.cards?.forEach {
            if (it.isFrontImage) list.add(it.frontContent)
            if (it.isBackImage) list.add(it.backContent)
        }
        list
    }

    // Estado que almacena la URI de la imagen seleccionada para su visualización ampliada.
    var selectedImageUrl by remember { mutableStateOf<String?>(null) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Cabecera.
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = AppSizes.screenPadding)
                    .padding(top = AppSizes.screenPadding, bottom = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier.offset(x = (-12).dp)
                ) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                }
                Text(
                    text = "Galería: ${album?.name}",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }

            // Cuadrícula de imágenes. Utiliza 4 columnas fijas.
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 90.dp),
                contentPadding = PaddingValues(AppSizes.screenPadding),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(images) { imageUrl ->
                    AsyncImage(
                        model = imageUrl,
                        contentDescription = "Miniatura",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .aspectRatio(1f)
                            .clip(RoundedCornerShape(12.dp))
                            .clickable { selectedImageUrl = imageUrl }
                    )
                }
            }
        }
    }

    // Disparador de la pantalla completa cuando existe una selección.
    selectedImageUrl?.let { url ->
        FullScreenImageModal(url = url, onDismiss = { selectedImageUrl = null })
    }
}

/**
 * Visionado de imágenes en pantalla completa.
 * @param url URI de la imagen a cargar.
 * @param onDismiss Callback para cerrar la pantalla y limpiar el estado de selección.
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
            // Componente para la manipulación de gestos.
            ZoomableImage(url)

            // Control de salida del visor.
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
 * Componente de visualización. Implementa reconocimiento de gestos múltiples.
 * @param url URI del recurso visual a mostrar.
 */
@Composable
fun ZoomableImage(url: String) {
    // Estados de transformación física de la imagen.
    var scale by remember { mutableStateOf(1f) }
    var rotation by remember { mutableStateOf(0f) }
    var offset by remember { mutableStateOf(Offset.Zero) }

    // Estado que escucha y acumula los cambios producidos por gestos.
    val state = rememberTransformableState { zoomChange, offsetChange, rotationChange ->
        scale *= zoomChange
        rotation += rotationChange
        offset += offsetChange
    }

    AsyncImage(
        model = url,
        contentDescription = "Imagen ampliada",
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            // Aplicación de transformaciones mediante la capa de gráficos.
            .graphicsLayer(
                scaleX = maxOf(.5f, minOf(3f, scale)),
                scaleY = maxOf(.5f, minOf(3f, scale)),
                rotationZ = rotation,
                translationX = offset.x,
                translationY = offset.y
            )
            // Vinculación del detector de transformaciones al componente.
            .transformable(state = state)
    )
}