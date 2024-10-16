package com.example.animeapp.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class AnimeDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    private lateinit var database: AnimeDatabase
    private lateinit var animeDao: AnimeDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AnimeDatabase::class.java
        ).allowMainThreadQueries().build()
        animeDao = database.animeDao()
    }

    @After
    fun teardown() {
        database.close()
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun insertUsersToDatabase() = runBlockingTest {
        val users = Users(
            id = 1,
            userName = "bahez",
            email = "bbb@gmail.com",
            password = "Bahez.151@"
        )
        animeDao.insert(users)
        val user = animeDao.login(email = "bbb@gmail.com" , password = "Bahez.151@").firstOrNull()
        assertThat(user).isEqualTo(users)
    }

}