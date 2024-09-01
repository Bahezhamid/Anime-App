package com.example.animeapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Users::class], version = 1, exportSchema = false)
abstract class AnimeDatabase : RoomDatabase() {

    abstract fun animeDao(): AnimeDao

    companion object {
        @Volatile
        private var Instance: AnimeDatabase? = null

        fun getDatabase(context: Context): AnimeDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, AnimeDatabase::class.java, "anime_database")
                    .build()
                    .also { Instance = it }
            }
        }
    }
}
