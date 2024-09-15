package com.example.animeapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(favoriteAnime : Favorite)
    @Query("DELETE FROM favorite WHERE animeId = :malId")
    suspend fun deleteFavoriteByAnimeId(malId: Int)
    @Query("SELECT EXISTS (SELECT 1 FROM favorite WHERE animeId = :malId)")
     fun isFavorite(malId: Int): Boolean
}