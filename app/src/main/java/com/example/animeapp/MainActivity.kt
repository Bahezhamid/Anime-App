package com.example.animeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.animeapp.ui.ProfilePage
import com.example.animeapp.ui.screens.AllAnimeScreen.AllAnimePage
import com.example.animeapp.ui.theme.AnimeAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AnimeAppTheme {
              AnimeApp()
            }
        }
    }
}