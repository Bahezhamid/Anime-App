package com.example.animeapp.data

import com.example.animeapp.model.AnimeData
import com.example.animeapp.model.AnimeDataById
import com.example.animeapp.model.Data
import com.example.animeapp.network.AnimeApiService


interface AnimeDataRepository {
    suspend fun getAnimeData(pages: Int): AnimeData?
    suspend fun getAnimeDataById(id : Int) : AnimeDataById?
}

class NetworkAnimeDataRepository(
    private val animeApiService: AnimeApiService
) : AnimeDataRepository {

    override suspend fun getAnimeData(pages: Int): AnimeData? {
        val allAnimeData = mutableListOf<Data?>()

        for (page in 1..pages) {
            val animeData = animeApiService.getAnimeData(page)
            animeData?.data?.let { allAnimeData.addAll(it) }
        }

        return AnimeData(
            pagination = null,
            data = allAnimeData
        )
    }

    override suspend fun getAnimeDataById(id: Int): AnimeDataById? = animeApiService.getAnimeDataById(id)
}