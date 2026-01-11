package com.example.flashcardsproject.data

import androidx.compose.runtime.mutableStateListOf
import com.example.flashcardsproject.R

/**
 * Representa la unidad mínima de información del sistema.
 * @property id Identificador único de la tarjeta.
 * @property frontImage Recurso gráfico (ID de drawable) que se muestra en la cara frontal.
 * @property backText Texto descriptivo o solución que se muestra en la cara posterior.
 */
data class Flashcard(
    val id: Int,
    val frontImage: Int,
    val backText: String
)

/**
 * Agrupación lógica de tarjetas de estudio.
 * @property id Identificador único del álbum.
 * @property name Título descriptivo del álbum.
 * @property cards Lista reactiva de tarjetas contenidas en el álbum.
 */
data class Album(
    val id: Int,
    var name: String,
    val cards: MutableList<Flashcard> = mutableStateListOf()
)

/**
 * Repositorio centralizado encargado de la gestión de persistencia en memoria.
 * Implementa el patrón Singleton para garantizar una única fuente de verdad de los datos.
 */
object FlashcardRepository {
    // Contador interno para garantizar la unicidad de los identificadores de los álbumes
    private var albumCounter = 1

    /**
     * Lista observable de álbumes. El uso de mutableStateListOf permite que
     * Compose reaccione automáticamente a cambios en la lista (añadir/eliminar).
     */
    val albums = mutableStateListOf<Album>(
        Album(
            id = albumCounter++,
            name = "Herramientas Taller",
            cards = mutableStateListOf(
                Flashcard(1, R.drawable.image1, "Llave Allen"),
                Flashcard(2, R.drawable.image2, "Llave Estrellada"),
                Flashcard(3, R.drawable.image3, "Llave de Tubo")
            )
        )
    )

    /**
     * Registra un nuevo álbum en el sistema con una lista de tarjetas vacía.
     * @param name Nombre que se asignará al nuevo álbum.
     */
    fun createAlbum(name: String) {
        albums.add(Album(id = albumCounter++, name = name))
    }

    /**
     * Elimina un álbum del repositorio basándose en su identificador.
     * @param id Identificador del álbum a eliminar.
     */
    fun deleteAlbum(id: Int) {
        albums.removeAll { it.id == id }
    }

    /**
     * Actualiza el nombre de un álbum existente y notifica el cambio al estado de la UI.
     * @param id Identificador del álbum a modificar.
     * @param newName Nuevo título para el álbum.
     */
    fun updateAlbumName(id: Int, newName: String) {
        getAlbumById(id)?.let { it.name = newName }
        val index = albums.indexOfFirst { it.id == id }
        if (index != -1) {
            val updatedAlbum = albums[index]
            albums[index] = updatedAlbum.copy(name = newName)
        }
    }

    /**
     * Busca y retorna un álbum específico.
     * @param id Identificador del álbum.
     * @return El objeto [Album] si se encuentra, de lo contrario retorna null.
     */
    fun getAlbumById(id: Int): Album? {
        return albums.find { it.id == id }
    }

    /**
     * Inserta una nueva tarjeta dentro de un álbum específico.
     * Calcula automáticamente el nuevo ID basándose en el ID más alto existente en el álbum.
     * @param albumId ID del álbum donde se añadirá la tarjeta.
     * @param frontImage Recurso de imagen seleccionado para el frontal.
     * @param backText Texto explicativo para el reverso.
     */
    fun addFlashcardToAlbum(albumId: Int, frontImage: Int, backText: String) {
        val album = getAlbumById(albumId)
        val newId = (album?.cards?.maxOfOrNull { it.id } ?: 0) + 1
        album?.cards?.add(Flashcard(newId, frontImage, backText))
    }
}