package com.example.animeapp.data

import android.content.Context

interface AppContainer {
    val animeRepository: AnimeRepository
}
class AppDataContainer(private val context: Context) : AppContainer {
    override val animeRepository: AnimeRepository by lazy {
        OfflineAnimeRepository(AnimeDatabase.getDatabase(context).animeDao())
    }
}