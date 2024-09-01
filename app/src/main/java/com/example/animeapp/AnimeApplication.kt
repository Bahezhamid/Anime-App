package com.example.animeapp

import android.app.Application
import com.example.animeapp.data.AppContainer
import com.example.animeapp.data.AppDataContainer

class AnimeApplication : Application() {

    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}
