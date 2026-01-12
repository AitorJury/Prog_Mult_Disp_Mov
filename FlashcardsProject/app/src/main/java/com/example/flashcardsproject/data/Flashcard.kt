package com.example.flashcardsproject.data

import android.content.Context
import androidx.compose.runtime.mutableStateListOf
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * Modelo de datos para las tarjetas y los álbumes.
 */
data class Flashcard(
    val id: Int,
    val frontContent: String,
    val isFrontImage: Boolean,
    val backContent: String,
    val isBackImage: Boolean
)

data class Album(
    val id: Int,
    var name: String,
    val cards: MutableList<Flashcard> = mutableStateListOf()
)

/**
 * Repositorio centralizado con persistencia XML y lógica de guardado masivo.
 */
object FlashcardRepository {
    private const val PREFS_NAME = "flashcards_prefs"
    private const val KEY_ALBUMS = "albums_json"
    private val gson = Gson()

    val albums = mutableStateListOf<Album>()
    private var albumCounter = 1

    fun init(context: Context) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val json = prefs.getString(KEY_ALBUMS, null)

        if (json != null) {
            val type = object : TypeToken<List<Album>>() {}.type
            val loadedAlbums: List<Album> = gson.fromJson(json, type)
            albums.clear()
            albums.addAll(loadedAlbums)
            albumCounter = (albums.maxOfOrNull { it.id } ?: 0) + 1
        }
    }

    private fun saveData(context: Context) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val json = gson.toJson(albums.toList())
        prefs.edit().putString(KEY_ALBUMS, json).apply()
    }

    fun createAlbum(name: String, context: Context? = null) {
        albums.add(Album(id = albumCounter++, name = name))
        context?.let { saveData(it) }
    }

    fun deleteAlbum(id: Int, context: Context) {
        albums.removeAll { it.id == id }
        saveData(context)
    }

    fun getAlbumById(id: Int): Album? = albums.find { it.id == id }

    /**
     * Añade una sola tarjeta al álbum.
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
     * NUEVA FUNCIÓN: Añade una lista completa de tarjetas de una sola vez.
     * Esto es mucho más rápido para la nueva ventana de "añadir varias".
     */
    fun addMultipleFlashcardsToAlbum(
        albumId: Int,
        newCards: List<Flashcard>,
        context: Context
    ) {
        val album = getAlbumById(albumId)
        album?.let { targetAlbum ->
            newCards.forEach { card ->
                val newId = (targetAlbum.cards.maxOfOrNull { c -> c.id } ?: 0) + 1
                targetAlbum.cards.add(card.copy(id = newId))
            }
            saveData(context)
        }
    }
}