package com.example.animeapp.data

import kotlinx.coroutines.flow.Flow

class OfflineAnimeRepository(private val animeDao: AnimeDao) : AnimeRepository {

    override suspend fun insertItem(user: Users) = animeDao.insert(user)
}
