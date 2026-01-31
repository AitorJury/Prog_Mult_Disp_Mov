package com.example.flashcardsproject.data

import android.content.Context
import androidx.compose.runtime.mutableStateListOf
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * Representa una tarjeta individual (flashcard) dentro del sistema.
 * @property id Identificador único de la tarjeta.
 * @property frontContent Contenido de la cara frontal (texto o ruta de imagen).
 * @property isFrontImage Indica si el contenido frontal debe interpretarse como imagen.
 * @property backContent Contenido de la cara posterior (texto o ruta de imagen).
 * @property isBackImage Indica si el contenido posterior debe interpretarse como imagen.
 */
data class Flashcard(
    val id: Int,
    val frontContent: String,
    val isFrontImage: Boolean,
    val backContent: String,
    val isBackImage: Boolean
)

/**
 * Representa una colección o conjunto de tarjetas bajo un nombre específico.
 * @property id Identificador único del álbum.
 * @property name Nombre descriptivo del álbum.
 * @property cards Lista observable de tarjetas contenidas en el álbum.
 */
data class Album(
    val id: Int,
    var name: String,
    val cards: MutableList<Flashcard> = mutableStateListOf()
)

/**
 * Repositorio encargado de la gestión, persistencia y lógica de las tarjetas.
 * Utiliza SharedPreferences para el almacenamiento de datos en formato JSON mediante Gson.
 */
object FlashcardRepository {
    private const val PREFS_NAME = "flashcards_prefs"
    private const val KEY_ALBUMS = "albums_json"
    private val gson = Gson()

    /** Lista de albumes. */
    val albums = mutableStateListOf<Album>()

    /** Contador interno para la generación de IDs incrementales de álbumes. */
    private var albumCounter = 1

    /**
     * Inicializa el repositorio cargando los datos persistidos desde SharedPreferences.
     * @param context Contexto de la aplicación necesario para acceder a las preferencias.
     */
    fun init(context: Context) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val json = prefs.getString(KEY_ALBUMS, null)

        if (json != null) {
            val type = object : TypeToken<List<Album>>() {}.type
            val loadedAlbums: List<Album> = gson.fromJson(json, type)
            albums.clear()
            albums.addAll(loadedAlbums)
            // Sincroniza el contador de IDs basado en el valor más alto existente
            albumCounter = (albums.maxOfOrNull { it.id } ?: 0) + 1
        }
    }

    /**
     * Guarda el estado actual de los albumes en el almacenamiento local.
     * @param context Contexto para acceder al sistema de persistencia.
     */
    private fun saveData(context: Context) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val json = gson.toJson(albums.toList())
        prefs.edit().putString(KEY_ALBUMS, json).apply()
    }

    /**
     * Crea un nuevo album y lo añade a la colección global.
     * @param name Nombre del nuevo album.
     * @param context Si se da, los cambios se guardan en disco.
     */
    fun createAlbum(name: String, context: Context? = null) {
        albums.add(Album(id = albumCounter++, name = name))
        context?.let { saveData(it) }
    }

    /**
     * Elimina un album del repositorio basándose en su identificador.
     * @param id ID del album a eliminar.
     * @param context Contexto para persistir los cambios tras la eliminación.
     */
    fun deleteAlbum(id: Int, context: Context) {
        albums.removeAll { it.id == id }
        saveData(context)
    }

    /**
     * Busca un album específico dentro de la lista cargada.
     * @param id Identificador único del album.
     * @return El objeto Album si se encuentra, null en caso contrario.
     */
    fun getAlbumById(id: Int): Album? = albums.find { it.id == id }

    /**
     * Crea y añade una nueva tarjeta a un album existente.
     * @param albumId Identificador del album destino.
     * @param front Texto o imagen frontal.
     * @param isFrontImg Booleano que dice si el frontal es imagen.
     * @param back Texto o imagen posterior.
     * @param isBackImg Booleano que dice si la trasera es imagen.
     * @param context Contexto para guardar los cambios de forma inmediata.
     */
    fun addFlashcardToAlbum(
        albumId: Int,
        front: String,
        isFrontImg: Boolean,
        back: String,
        isBackImg: Boolean,
        context: Context
    ) {
        val album = getAlbumById(albumId)
        album?.let {
            val newId = (it.cards.maxOfOrNull { c -> c.id } ?: 0) + 1
            it.cards.add(Flashcard(newId, front, isFrontImg, back, isBackImg))
            saveData(context)
        }
    }

    /**
     * Inserta múltiples tarjetas en un album de forma masiva.
     * Optimiza el proceso de guardado al realizar una única llamada a persistencia.
     * @param albumId ID del album donde se insertan las tarjetas.
     * @param newCards Lista de objetos Flashcard a integrar.
     * @param context Contexto para guardar los cambios.
     */
    fun addMultipleFlashcardsToAlbum(
        albumId: Int,
        newCards: List<Flashcard>,
        context: Context
    ) {
        val album = getAlbumById(albumId)
        album?.let { targetAlbum ->
            newCards.forEach { card ->
                // Genera un ID incremental para cada nueva tarjeta.
                val newId = (targetAlbum.cards.maxOfOrNull { c -> c.id } ?: 0) + 1
                targetAlbum.cards.add(card.copy(id = newId))
            }
            saveData(context)
        }
    }
}