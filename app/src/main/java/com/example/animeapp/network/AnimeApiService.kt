package com.example.animeapp.network

import com.example.animeapp.model.AllGenres
import com.example.animeapp.model.AnimeCharacters
import com.example.animeapp.model.AnimeData
import com.example.animeapp.model.AnimeDataById
import com.example.animeapp.model.AnimeChapters
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface AnimeApiService {
    @GET("anime")
    suspend fun getAnimeData(@Query("page") page: Int): AnimeData?
    @GET("anime/{id}")
    suspend fun getAnimeDataById(@Path("id") id : Int) : AnimeDataById?
    @GET("genres/anime")
    suspend fun getAllGenres() :  AllGenres?
    @GET("anime/{id}/characters")
    suspend fun getCharacters(@Path("id") id  :Int) : AnimeCharacters?
    @GET("anime/{id}/videos/episodes")
    suspend fun getAnimeChapters(@Path("id") id : Int) : AnimeChapters
}