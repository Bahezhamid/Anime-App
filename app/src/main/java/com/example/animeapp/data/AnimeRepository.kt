package com.example.animeapp.data

import kotlinx.coroutines.flow.Flow

interface AnimeRepository {
    suspend fun insertItem(user: Users)
    fun login (email :String , password: String) : Flow<Users>
    fun isEmailExist(email: String) : Int
}