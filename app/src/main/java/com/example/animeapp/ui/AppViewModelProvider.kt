package com.example.animeapp.ui

import android.app.Application
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.animeapp.AnimeApplication
import com.example.animeapp.ui.screens.AllAnimeScreen.AllAnimeViewScreenModel
import com.example.animeapp.ui.screens.AnimeDetailsPage.AnimeDetailsViewModel
import com.example.animeapp.ui.screens.HomePage.HomePageViewModel
import com.example.animeapp.ui.screens.logInAndSignUp.LoginAndSignUpViewModel
import com.example.animeapp.ui.screens.AnimeChapterScreen.AnimeChaptersViewModel
import com.example.animeapp.ui.screens.CharactersDetailsPage.CharactersDetailsPage
import com.example.animeapp.ui.screens.CharactersDetailsPage.CharactersDetailsViewModel
import com.example.animeapp.ui.screens.SavedAnimeScreen.SavedAnimeViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            LoginAndSignUpViewModel(
                animeApplication().container.animeRepository,
                animeApplication().userPreferencesRepository
            )
        }
        initializer {
            HomePageViewModel(animeApplication().container.animeDataRepository,
            )
        }
        initializer {
            AnimeDetailsViewModel(
                animeApplication().container.animeDataRepository,
                animeApplication().container.animeRepository
                )
        }
        initializer {
            AllAnimeViewScreenModel(animeApplication().container.animeDataRepository)
        }
        initializer {
            AnimeChaptersViewModel(animeApplication().container.animeDataRepository)
        }
        initializer {
            CharactersDetailsViewModel(animeApplication().container.animeDataRepository)
        }
        initializer {
            SavedAnimeViewModel(animeApplication().container.animeDataRepository,
            )
        }

    }
}

fun CreationExtras.animeApplication(): AnimeApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as AnimeApplication)
