package com.example.flashcardsproject.ui.windows

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.flashcardsproject.data.Album
import com.example.flashcardsproject.data.FlashcardRepository

/**
 * Pantalla principal de gestión de albumes.
 * Muestra una lista de todos los albumes creados y da acceso a las funciones de estudio, visualización y edición.
 * @param navController Controlador para gestionar el flujo de navegación hacia las pantallas de las funciones.
 */
@Composable
fun AlbumListWindow(navController: NavHostController) {
    // Referencia reactiva a la lista de albumes.
    val albums = FlashcardRepository.albums
    val context = LocalContext.current

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(modifier = Modifier.fillMaxSize()) {

            // Cabecera.
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
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
                Text(
                    text = "Mis Albumes",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
            }

            // Lista de albumes.
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(albums) { album ->
                    AlbumItem(
                        album = album,
                        onOpenStudy = { navController.navigate("list/${album.id}") },
                        onOpenGallery = { navController.navigate("gallery/${album.id}") },
                        onAddCard = { navController.navigate("add_card/${album.id}") },
                        onDelete = { FlashcardRepository.deleteAlbum(album.id, context) }
                    )
                }
            }
        }
    }
}

/**
 * Componente que representa una tarjeta de album dentro de la lista.
 * Incluye un menú contextual para acciones secundarias.
 * @param album El objeto Album con los datos a mostrar.
 * @param onOpenStudy Acción para navegar al modo de estudio del album.
 * @param onOpenGallery Acción para abrir el visor de imágenes del album.
 * @param onAddCard Acción para navegar a la pantalla de creación de tarjetas.
 * @param onDelete Callback para ejecutar la eliminación definitiva del álbum.
 */
@Composable
fun AlbumItem(
    album: Album,
    onOpenStudy: () -> Unit,
    onOpenGallery: () -> Unit,
    onAddCard: () -> Unit,
    onDelete: () -> Unit
) {
    // Estados locales para la visibilidad de componentes.
    var showMenu by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }

    // Confirmación de borrado.
    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("¿Eliminar album?") },
            text = { Text("Esta acción borrará todas las tarjetas dentro de '${album.name}'.") },
            confirmButton = {
                TextButton(onClick = {
                    onDelete()
                    showDeleteDialog = false
                }) {
                    Text("Eliminar", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = { TextButton(onClick = { showDeleteDialog = false }) { Text("Cancelar") } }
        )
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            // Navegación directa al estudio mediante clic en la tarjeta.
            .clickable { onOpenStudy() },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)
        )
    ) {
        Row(
            modifier = Modifier.padding(20.dp).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Texto del album y contador de tarjetas.
            Column {
                Text(text = album.name, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                Text(
                    text = "${album.cards.size} tarjetas",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                )
            }

            // Menú de opciones secundarias.
            Box {
                IconButton(onClick = { showMenu = true }) {
                    Icon(Icons.Default.MoreVert, contentDescription = "Opciones")
                }

                DropdownMenu(expanded = showMenu, onDismissRequest = { showMenu = false }) {
                    DropdownMenuItem(
                        text = { Text("Ver imágenes") },
                        onClick = {
                            showMenu = false
                            onOpenGallery()
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Añadir tarjetas") },
                        onClick = {
                            showMenu = false
                            onAddCard()
                        }
                    )
                    HorizontalDivider()
                    DropdownMenuItem(
                        text = { Text("Eliminar album", color = MaterialTheme.colorScheme.error) },
                        onClick = {
                            showMenu = false
                            showDeleteDialog = true
                        }
                    )
                }
            }
        }
    }
}