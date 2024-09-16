package com.example.animeapp.ui.screens.SavedAnimeScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.animeapp.AnimeBottomAppBar
import com.example.animeapp.AnimeTopAppBar
import com.example.animeapp.R
import com.example.animeapp.ui.screens.HomePage.AnimeCard
import com.example.animeapp.ui.screens.logInAndSignUp.LoginAndSignUpViewModel

@Composable
fun SavedAnimePage (
    onHomeClicked : () -> Unit,
    onBookClicked : () -> Unit,
    onProfileClicked : () -> Unit,
    onAnimeClicked : (Int) -> Unit,
    onCreateNewListClicked : () -> Unit,
    loginAndSignUpViewModel: LoginAndSignUpViewModel,
    savedAnimeViewModel: SavedAnimeViewModel
) {
    val userId = loginAndSignUpViewModel.loginUiState.collectAsState().value.userid
    LaunchedEffect (userId){
        savedAnimeViewModel.getAllSavedAnime(userId)
    }
    val allSavedAnime = savedAnimeViewModel.savedAnimeUiState.collectAsState()
    Scaffold(
        topBar = {
            AnimeTopAppBar(title = "My List")
        },
        bottomBar = {
            AnimeBottomAppBar(
                onHomeClick = onHomeClicked,
                onSavedClick = {},
                onBookClick = onBookClicked,
                onProfileClick = onProfileClicked
            )
        }
    ) { innerPadding ->
        if(allSavedAnime.value.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(innerPadding)
                    .padding(start = 26.dp, end = 26.dp, top = 26.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.jiraya_pic),
                    contentDescription = "List is Empty",
                    modifier = Modifier
                        .height(220.dp)
                        .width(280.dp)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "Many shows to watch.",
                    style = MaterialTheme.typography.headlineLarge
                )
                Spacer(modifier = Modifier.height(14.dp))
                Text(
                    text = "Let's  watch some..",
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.height(20.dp))
                Button(
                    onClick = onCreateNewListClicked,

                    modifier = Modifier
                        .height(50.dp)
                        .width(184.dp)
                        .clip(RoundedCornerShape(10.dp)),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary
                    )
                ) {
                    Text(
                        text = "Create new list",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }

            }
        }
        else{
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(innerPadding)
                ,
                contentPadding = PaddingValues(
                    start = 10.dp,
                    top = 10.dp,
                    end = 0.dp,
                    bottom = 10.dp,
                ),
            ) {

                    items(allSavedAnime.value) {anime ->
                        AnimeCard(animeId = anime.animeId,
                            animeTitle = anime.animeName,
                            animePoster = anime.animePoster,
                            onAnimeClicked = onAnimeClicked,
                        )
                    }

            }
        }
    }
}