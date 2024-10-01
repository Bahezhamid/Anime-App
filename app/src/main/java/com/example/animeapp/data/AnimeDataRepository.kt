package com.example.animeapp.data

import com.example.animeapp.model.AllGenres
import com.example.animeapp.model.AnimeChapters
import com.example.animeapp.model.AnimeCharacters
import com.example.animeapp.model.AnimeData
import com.example.animeapp.model.AnimeDataById
import com.example.animeapp.model.CharactersAllData
import com.example.animeapp.model.Data
import com.example.animeapp.network.AnimeApiService


interface AnimeDataRepository {
    suspend fun getAnimeData(pages: Int): AnimeData?
    suspend fun getAnimeDataById(id : Int) : AnimeDataById?
    suspend fun getAllGenres() : AllGenres?
    suspend fun getAllCharacters(id : Int) : AnimeCharacters?
    suspend fun getAnimeChapters(id : Int, page: Int) : AnimeChapters?
    suspend fun getAllCharactersData(id : Int) : CharactersAllData
    suspend fun getAnimeByScore(minScore : Int) : AnimeData?
    suspend fun getAnimeByName(animeName : String) : AnimeData
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
    override suspend fun getAllGenres(): AllGenres? = animeApiService.getAllGenres()
    override suspend fun getAllCharacters(id: Int): AnimeCharacters? = animeApiService.getCharacters(id = id)
    override suspend fun getAnimeChapters(id: Int, page: Int): AnimeChapters = animeApiService.getAnimeChapters(id = id,page = page)
    override suspend fun getAllCharactersData(id: Int): CharactersAllData =animeApiService.getCharactersData(id= id)
    override suspend fun getAnimeByScore(minScore: Int): AnimeData = animeApiService.getAnimeByScore(minScore=minScore)
    override suspend fun getAnimeByName(animeName: String): AnimeData = animeApiService.getAnimeByName(animeName = animeName)
}