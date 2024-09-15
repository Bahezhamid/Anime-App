package com.example.animeapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface AnimeDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(users: Users)
    @Query("select * from Users where email = :email And password = :password")
    fun login(email : String, password: String) : Flow<Users>
    @Query("select COUNT(*) from Users where email = :email")
    fun isEmailExist(email: String) : Int
    @Query("select id from Users where email = :email and password = :password")
    fun getUserId(email: String,password: String) : Int
}
