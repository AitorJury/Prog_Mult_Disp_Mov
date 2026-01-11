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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.flashcardsproject.data.Album
import com.example.flashcardsproject.data.FlashcardRepository

/**
 * Ventana que despliega el listado de álbumes creados por el usuario.
 * Actúa como el centro de gestión para navegar a las sesiones de estudio o edición.
 */
@Composable
fun AlbumListWindow(navController: NavHostController) {
    // Referencia directa a la lista reactiva del repositorio
    val albums = FlashcardRepository.albums

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Barra superior con botón de retroceso y título de la sección
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
                Text(
                    text = "Mis Álbumes",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
            }

            /**
             * Listado optimizado de álbumes.
             * LazyColumn solo renderiza los elementos visibles en pantalla (equivalente a RecyclerView).
             */
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp) // Espaciado uniforme entre tarjetas
            ) {
                items(albums) { album ->
                    AlbumItem(
                        album = album,
                        onOpen = { navController.navigate("list/${album.id}") },
                        onAddCard = { navController.navigate("add_card/${album.id}") },
                        onDelete = { FlashcardRepository.deleteAlbum(album.id) }
                    )
                }
            }
        }
    }
}

/**
 * Componente representativo de un álbum individual dentro de la lista.
 * Gestiona sus propios estados de menú contextual y diálogos de confirmación.
 * * @param album Objeto de datos que contiene la información del álbum.
 * @param onOpen Callback para iniciar la sesión de estudio.
 * @param onAddCard Callback para navegar al formulario de creación de tarjetas.
 * @param onDelete Callback para ejecutar la eliminación en el repositorio.
 */
@Composable
fun AlbumItem(album: Album, onOpen: () -> Unit, onAddCard: () -> Unit, onDelete: () -> Unit) {
    // Controla la visibilidad del menú desplegable de opciones (tres puntos)
    var showMenu by remember { mutableStateOf(false) }

    // Controla la visibilidad del aviso de seguridad para borrar el álbum
    var showDeleteDialog by remember { mutableStateOf(false) }

    /**
     * Diálogo de confirmación (Seguridad de usuario).
     * Evita eliminaciones accidentales siguiendo las guías de Material Design.
     */
    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("¿Eliminar álbum?") },
            text = { Text("Esta acción borrará todas las tarjetas dentro de '${album.name}'.") },
            confirmButton = {
                TextButton(onClick = {
                    onDelete() // Ejecuta la lógica de borrado heredada
                    showDeleteDialog = false
                }) {
                    Text("Eliminar", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("Cancelar")
                }
            }
        )
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onOpen() },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)
        )
    ) {
        Row(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Información textual del álbum (Nombre y contador de tarjetas)
            Column {
                Text(
                    text = album.name,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "${album.cards.size} tarjetas",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                )
            }

            // Menú de acciones secundarias
            Box {
                IconButton(onClick = { showMenu = true }) {
                    Icon(Icons.Default.MoreVert, contentDescription = "Opciones")
                }

                DropdownMenu(
                    expanded = showMenu,
                    onDismissRequest = { showMenu = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Estudiar") },
                        onClick = {
                            showMenu = false
                            onOpen()
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Añadir tarjeta") },
                        onClick = {
                            showMenu = false
                            onAddCard()
                        }
                    )
                    // Separador visual para diferenciar acciones destructivas
                    Divider()
                    DropdownMenuItem(
                        text = { Text("Eliminar álbum", color = MaterialTheme.colorScheme.error) },
                        onClick = {
                            showMenu = false
                            showDeleteDialog = true // Activa el diálogo de confirmación
                        }
                    )
                }
            }
        }
    }
}