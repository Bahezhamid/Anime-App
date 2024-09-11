package com.example.animeapp.ui.screens.AnimeChapterScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.animeapp.AnimeTopAppBar
import com.example.animeapp.R
import com.example.animeapp.model.AnimeChapters
import com.example.animeapp.ui.AppViewModelProvider
import com.example.animeapp.ui.screens.AnimeDetailsPage.AnimeDetailsScreen
import com.example.animeapp.ui.screens.AnimeDetailsPage.AnimeDetailsUiState
import com.example.animeapp.ui.screens.HomePage.ErrorScreen
import com.example.animeapp.ui.screens.HomePage.LoadingScreen

@Composable
fun AnimeChaptersScreen(
    onBackButtonClicked : () -> Unit,
    animeChaptersViewModel: AnimeChaptersViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.primary),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Scaffold (
            topBar = {
                AnimeTopAppBar(
                    title = "",
                    isBackButton = true,
                    onBackButtonClicked = onBackButtonClicked
                    )
            }
        ){ innerPadding ->


            when (animeChaptersViewModel.animeChaptersUiState.collectAsState().value) {
                is AnimeChaptersUiState.Loading -> LoadingScreen(modifier = Modifier.fillMaxSize())
                is AnimeChaptersUiState.Error -> ErrorScreen(
                    retryAction = {

                    },
                    modifier = Modifier.fillMaxSize()
                )

                is AnimeChaptersUiState.Success ->
                    AnimeChaptersPage(
                        chapters = (animeChaptersViewModel.animeChaptersUiState.collectAsState()
                            .value as AnimeChaptersUiState.Success).animeChapters,
                        modifier = Modifier.padding(innerPadding)
                            .background(MaterialTheme.colorScheme.primary)
                            .fillMaxSize()

                    )
            }
        }

    }
}

@Composable
fun AnimeChaptersPage(
    chapters : AnimeChapters?,
    modifier: Modifier

) {

}