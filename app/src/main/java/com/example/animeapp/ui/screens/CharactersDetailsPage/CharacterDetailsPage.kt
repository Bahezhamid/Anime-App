package com.example.animeapp.ui.screens.CharactersDetailsPage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.animeapp.AnimeTopAppBar
import com.example.animeapp.ui.AppViewModelProvider
import com.example.animeapp.ui.screens.AnimeChapterScreen.AnimeChaptersPage
import com.example.animeapp.ui.screens.AnimeChapterScreen.AnimeChaptersUiState
import com.example.animeapp.ui.screens.HomePage.ErrorScreen
import com.example.animeapp.ui.screens.HomePage.LoadingScreen

@Composable
fun CharactersDetailsPage(
    charactersDetailsViewModel: CharactersDetailsViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    Scaffold (
        topBar = {
            AnimeTopAppBar(
                title = "",
                isBackButton = true,
                onBackButtonClicked = {}
            )
        }
    ){ innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.primary)
                .padding(innerPadding)
            ,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (charactersDetailsViewModel.charactersDetails.collectAsState().value) {
                is CharactersDetailsUiState.Loading -> LoadingScreen(
                    modifier = Modifier.fillMaxSize()
                )
                is CharactersDetailsUiState.Error -> ErrorScreen(
                    retryAction = {

                    },
                    modifier = Modifier.fillMaxSize()
                )

                is CharactersDetailsUiState.Success ->
                    CharacterDetailsScreen(
                        charactersDetailsViewModel = charactersDetailsViewModel,
                        modifier = Modifier.fillMaxSize()
                    )
            }
        }

    }
}

@Composable
fun CharacterDetailsScreen(
    charactersDetailsViewModel: CharactersDetailsViewModel,
    modifier: Modifier
) {
    Column (
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        val characterData =  (charactersDetailsViewModel.charactersDetails.collectAsState()
            .value as CharactersDetailsUiState.Success).charactersDetails
        Text(text =characterData.toString())
    }
}