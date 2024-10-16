package com.example.animeapp.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class FavoriteDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    private lateinit var database: AnimeDatabase
    private lateinit var favoriteDao: FavoriteDao
    private lateinit var animeDao: AnimeDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AnimeDatabase::class.java
        ).allowMainThreadQueries().build()
        favoriteDao = database.favoriteDao()
        animeDao = database.animeDao()
    }
    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertFavoriteAnime() = runTest {
        val user = Users(
            id = 1,
            userName = "test_user",
            email = "test@gmail.com",
            password = "password123"
        )
        animeDao.insert(user)
        val anime = Favorite(
            id = 1,
            animeId = 2,
            animePoster = "hello",
            animeName = "hi",
            userId = "1"
        )

        favoriteDao.insert(anime)
        val getAnime = favoriteDao.isFavorite(malId = 2 , userId = 1)
        assertThat(getAnime).isTrue()
    }

    @Test
    fun deleteFavoriteAnime() = runTest {
        val user = Users(
            id = 1,
            userName = "test_user",
            email = "test@gmail.com",
            password = "password123"
        )
        animeDao.insert(user)
        val anime = Favorite(
            id = 1,
            animeId = 2,
            animePoster = "hello",
            animeName = "hi",
            userId = "1"
        )

        favoriteDao.insert(anime)
        favoriteDao.deleteFavoriteByAnimeId(malId = 2 , userId = 1)
        val getAnime = favoriteDao.isFavorite(malId = 2 , userId = 1)
        assertThat(getAnime).isFalse()
    }

    @Test
    fun selectAllFavoriteAnime() = runTest {
        val user = Users(
            id = 1,
            userName = "test_user",
            email = "test@gmail.com",
            password = "password123"
        )
        animeDao.insert(user)
        val anime = Favorite(
            id = 1,
            animeId = 2,
            animePoster = "hello",
            animeName = "hi",
            userId = "1"
        )
        favoriteDao.insert(anime)
        val getAllAnime = favoriteDao.getAllSavedAnime(userId = 1)
        assertThat(getAllAnime).contains(anime)
    }
}
