package com.example.animeapp.data

import kotlinx.coroutines.flow.Flow

class OfflineAnimeRepository(private val animeDao: AnimeDao) : AnimeRepository {

    override suspend fun insertItem(user: Users) = animeDao.insert(user)
    override fun login(email: String, password: String): Flow<Users> =
        animeDao.login(email = email, password = password)

    override fun isEmailExist(email: String): Int =animeDao.isEmailExist(email = email)
}
