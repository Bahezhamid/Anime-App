package com.example.animeapp.ui.screens.logInAndSignUp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.animeapp.AnimeTopAppBar

@Composable
fun ForgetPasswordScreen(
    onBackButtonClicked : () -> Unit,

) {
    Scaffold(
        topBar = {
            AnimeTopAppBar(
                title = "",
                isBackButton = true,
                onBackButtonClicked = onBackButtonClicked
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primary),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            
        }
    }
}