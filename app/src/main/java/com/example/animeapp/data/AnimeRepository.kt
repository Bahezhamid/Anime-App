package com.example.animeapp.data

interface AnimeRepository {
    suspend fun insertItem(user: Users)
}