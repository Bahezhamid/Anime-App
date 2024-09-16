package com.example.animeapp.data

import kotlinx.coroutines.flow.Flow

class OfflineAnimeRepository(private val animeDao: AnimeDao,
                             private val favoriteDao : FavoriteDao) : AnimeRepository {

    override suspend fun insertItem(user: Users) = animeDao.insert(user)
    override fun login(email: String, password: String): Flow<Users> =
        animeDao.login(email = email, password = password)

    override fun isEmailExist(email: String): Int =animeDao.isEmailExist(email = email)
    override fun getUserId(email: String, password: String): Int = animeDao.getUserId(email= email, password = password)
    override suspend fun insertAnimeToFavorite(favorite: Favorite) = favoriteDao.insert(favorite)
    override suspend fun deleteAnimeFromFavorite(malId: Int, userId: Int) = favoriteDao.deleteFavoriteByAnimeId(malId = malId , userId = userId)
    override fun isFavorite(malId: Int, userId : Int): Boolean =favoriteDao.isFavorite(malId= malId, userId = userId)
    override fun getAllSavedAnime(userId: Int): List<Favorite> =favoriteDao.getAllSavedAnime(userId = userId)
}
