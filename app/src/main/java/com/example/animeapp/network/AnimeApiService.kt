package com.example.animeapp.network

import com.example.animeapp.model.AnimeData
import com.example.animeapp.model.Data
import retrofit2.http.GET
import retrofit2.http.Query


interface AnimeApiService {
    @GET("anime")
    suspend fun getAnimeData(@Query("page") page: Int): AnimeData?
}