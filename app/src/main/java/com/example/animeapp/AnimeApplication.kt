package com.example.animeapp

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.preferencesDataStore
import com.example.animeapp.data.AppContainer
import com.example.animeapp.data.AppDataContainer
import com.example.animeapp.data.UserPreferencesRepository
import java.util.prefs.Preferences


    private const val REMEMBER_ME_PREFERENCE_NAME = "remember_me_preference"
private val Context.dataStore: DataStore<androidx.datastore.preferences.core.Preferences> by preferencesDataStore(
    name = REMEMBER_ME_PREFERENCE_NAME
)
class AnimeApplication : Application() {
    lateinit var userPreferencesRepository: UserPreferencesRepository
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
        userPreferencesRepository = UserPreferencesRepository(dataStore)
    }
}
