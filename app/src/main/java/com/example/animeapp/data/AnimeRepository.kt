package com.example.animeapp.data

import kotlinx.coroutines.flow.Flow

interface AnimeRepository {
    suspend fun insertItem(user: Users)
    fun login (email :String , password: String) : Flow<Users>
    fun isEmailExist(email: String) : Int
    fun getUserId(email: String,password: String) : Int
    suspend fun insertAnimeToFavorite(favorite: Favorite)
    suspend fun deleteAnimeFromFavorite(malId : Int)
    fun isFavorite(malId: Int) : Boolean
}