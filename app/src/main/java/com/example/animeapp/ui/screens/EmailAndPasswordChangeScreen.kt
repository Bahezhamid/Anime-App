package com.example.animeapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.animeapp.AnimeTopAppBar
import com.example.animeapp.ui.screens.logInAndSignUp.LoginAndSignUpTextField

@Composable
fun EmailAndPasswordChangeScreen(
    onButtonClicked : () -> Unit,
) {
    Scaffold (
        topBar = { AnimeTopAppBar(
            title = "",
            isBackButton = true,
            onBackButtonClicked = onButtonClicked
            )}
    ){ innerPadding ->
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primary)
        ){

        }
    }
}