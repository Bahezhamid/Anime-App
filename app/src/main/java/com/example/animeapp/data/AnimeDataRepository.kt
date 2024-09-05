package com.example.animeapp.data

import com.example.animeapp.model.AnimeData
import com.example.animeapp.network.AnimeApiService


interface AnimeDataRepository {
    suspend fun getAnimeData() : AnimeData?
}

class NetworkAnimeDataRepository(
    private val animeApiService: AnimeApiService
) : AnimeDataRepository {

    override suspend fun getAnimeData(): AnimeData? = animeApiService.getAnimeData()
}