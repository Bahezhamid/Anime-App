package com.example.animeapp.network

import com.example.animeapp.model.AllGenres
import com.example.animeapp.model.AnimeCharacters
import com.example.animeapp.model.AnimeData
import com.example.animeapp.model.AnimeDataById
import com.example.animeapp.model.AnimeChapters
import com.example.animeapp.model.CharactersAllData
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
    suspend fun getAnimeChapters(@Path("id") id : Int,@Query("page") page : Int) : AnimeChapters
    @GET("characters/{id}/full")
    suspend fun getCharactersData(@Path("id") id : Int) : CharactersAllData
    @GET("anime")
    suspend fun getAnimeByScore(
        @Query("min_score") minScore: Int,
        @Query("order_by") orderBy: String = "rank",
        @Query("limit") limit: Int = 10
    ): AnimeData
    @GET("anime")
    suspend fun getAnimeByName(@Query("q") animeName : String) : AnimeData
    @GET("anime")
    suspend fun getAnimeByGenre(@Query("genres")genreId : Int, @Query("page") page : Int) : AnimeData
}