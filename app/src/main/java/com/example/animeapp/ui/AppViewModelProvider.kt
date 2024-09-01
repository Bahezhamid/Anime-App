package com.example.animeapp.ui

import android.app.Application
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.animeapp.AnimeApplication
import com.example.animeapp.ui.screens.logInAndSignUp.LoginAndSignUpViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            LoginAndSignUpViewModel(
                animeApplication().container.animeRepository
            )
        }

    }
}
fun CreationExtras.animeApplication(): AnimeApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as AnimeApplication)
