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
    @Query("DELETE FROM favorite WHERE animeId = :malId and userId = :userId")
    suspend fun deleteFavoriteByAnimeId(malId: Int, userId: Int)
    @Query("SELECT EXISTS (SELECT 1 FROM favorite WHERE animeId = :malId and userId = :userId)")
     fun isFavorite(malId: Int , userId : Int): Boolean
     @Query("Select * from favorite where userId = :userId")
     fun getAllSavedAnime(userId: Int) : List<Favorite>
}