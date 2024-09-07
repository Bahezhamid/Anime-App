package com.example.animeapp.data

import android.content.Context
import com.example.animeapp.network.AnimeApiService

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


interface AppContainer {
    val animeRepository: AnimeRepository
    val animeDataRepository : AnimeDataRepository
}
class AppDataContainer(private val context: Context) : AppContainer {
    private val baseUrl = "https://api.jikan.moe/v4/"
    override val animeRepository: AnimeRepository by lazy {
        OfflineAnimeRepository(AnimeDatabase.getDatabase(context).animeDao())
    }

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val retrofitService: AnimeApiService by lazy {
        retrofit.create(AnimeApiService::class.java)
    }
    override val animeDataRepository: AnimeDataRepository by lazy {
        NetworkAnimeDataRepository(retrofitService)
    }
}