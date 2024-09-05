package com.example.animeapp.network

import com.example.animeapp.model.AnimeData
import com.example.animeapp.model.Data
import retrofit2.http.GET


interface AnimeApiService {
    @GET("anime")
    suspend fun getAnimeData(): AnimeData?
}