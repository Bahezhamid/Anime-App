package com.example.animeapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.animeapp.AnimeBottomAppBar
import com.example.animeapp.ui.AppViewModelProvider
import com.example.animeapp.ui.screens.logInAndSignUp.LoginAndSignUpViewModel

@Composable
fun HomeScreen(
    loginAndSignUpViewModel: LoginAndSignUpViewModel
) {
    val loginUiState by loginAndSignUpViewModel.loginUiState.collectAsState()
    Scaffold(
        bottomBar = {
            AnimeBottomAppBar(
                onHomeClick = {},
                onSavedClick = {},
                onBookClick = {},
                onProfileClick = {}
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primary)
                .padding(innerPadding)
        ) {

            Text(
                text = if (loginUiState.isSuccess) {
                    "Welcome ${loginUiState.userName}, your Email is: ${loginUiState.email} " +
                            "and Password is: ${loginUiState.password}. " +
                            "We will start working on home page design soon."
                } else {
                    "User not logged in."
                }
            )
        }

    }
}
