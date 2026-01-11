package com.example.flashcardsproject.data

import androidx.compose.runtime.mutableStateListOf
import com.example.flashcardsproject.R

data class Flashcard(
    val id: Int,
    val frontImage: Int,
    val backText: String
)

data class Album(
    val id: Int,
    var name: String,
    val cards: MutableList<Flashcard> = mutableStateListOf()
)

object FlashcardRepository {
    private var albumCounter = 1

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

    fun createAlbum(name: String) {
        albums.add(Album(id = albumCounter++, name = name))
    }

    fun deleteAlbum(id: Int) {
        albums.removeAll { it.id == id }
    }

    fun updateAlbumName(id: Int, newName: String) {
        getAlbumById(id)?.let { it.name = newName }
        val index = albums.indexOfFirst { it.id == id }
        if (index != -1) {
            val updatedAlbum = albums[index]
            albums[index] = updatedAlbum.copy(name = newName)
        }
    }

    fun getAlbumById(id: Int): Album? {
        return albums.find { it.id == id }
    }

    fun addFlashcardToAlbum(albumId: Int, frontImage: Int, backText: String) {
        val album = getAlbumById(albumId)
        val newId = (album?.cards?.maxOfOrNull { it.id } ?: 0) + 1
        album?.cards?.add(Flashcard(newId, frontImage, backText))
    }
}