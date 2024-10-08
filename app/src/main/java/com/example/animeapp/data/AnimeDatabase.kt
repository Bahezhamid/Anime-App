package com.example.animeapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Users::class, Favorite::class], version = 2, exportSchema = false)
abstract class AnimeDatabase : RoomDatabase() {

    abstract fun animeDao(): AnimeDao
    abstract fun favoriteDao(): FavoriteDao

    companion object {
        @Volatile
        private var Instance: AnimeDatabase? = null

        fun getDatabase(context: Context): AnimeDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, AnimeDatabase::class.java, "anime_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}
