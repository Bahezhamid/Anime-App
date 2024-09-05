package com.example.animeapp.ui.screens.HomePage

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.animeapp.AnimeBottomAppBar
import com.example.animeapp.R
import com.example.animeapp.model.AnimeData
import com.example.animeapp.model.Data
import com.example.animeapp.ui.AppViewModelProvider
import com.example.animeapp.ui.screens.logInAndSignUp.LoginAndSignUpViewModel

@Composable
fun HomeScreen(
    loginAndSignUpViewModel: LoginAndSignUpViewModel = viewModel(factory = AppViewModelProvider.Factory),
    homePageViewModel: HomePageViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val anime = homePageViewModel.uiState.collectAsState()
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
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(560.dp)
            ) {

                AsyncImage(model =ImageRequest
                    .Builder(context = LocalContext.current)
                    .data(anime.value?.data?.first()?.attributes?.posterImage?.medium)
                    .crossfade(true)
                    .build() ,
                    contentDescription = "poster",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .height(100.dp)
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    MaterialTheme.colorScheme.primary
                                ),
                                startY = 0f,
                                endY = Float.POSITIVE_INFINITY
                            )
                        )
                )
                Column (modifier = Modifier.fillMaxSize()){

                    Box(modifier = Modifier
                        .padding(end = 26.dp, top = 77.dp)
                        .align(AbsoluteAlignment.Right)
                        .size(50.dp)
                        .clip(RoundedCornerShape(50.dp))
                        .clickable { },
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Search,
                            contentDescription = "Search",
                            tint = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Row(
                        horizontalArrangement = Arrangement.SpaceAround,
                        modifier = Modifier.fillMaxWidth(),

                        ) {
                        Text(text = "Action",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(text = "Drama",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(text = "Adventure",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(text = "Thriller",
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                    Spacer(modifier = Modifier.weight(0.1f))
                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Box(
                            modifier = Modifier
                                .size(70.dp)
                                .clip(RoundedCornerShape(20.dp))
                                .clickable { },
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.fillMaxSize()
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Add, contentDescription = "",
                                    modifier = Modifier.size(50.dp),
                                    tint = MaterialTheme.colorScheme.onPrimary
                                )

                                Text(text = "My List", style = MaterialTheme.typography.bodyLarge)
                            }
                        }
                        Button(
                            onClick = { /*TODO*/ },
                            modifier = Modifier
                                .width(126.dp)
                                .height(56.dp)
                        ) {
                            Text(text = "Play", style = MaterialTheme.typography.headlineSmall)
                        }
                        Box(
                            modifier = Modifier
                                .size(70.dp)
                                .clip(RoundedCornerShape(30.dp))
                                .clickable { },
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.fillMaxSize()
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Info,
                                    contentDescription = "",
                                    modifier = Modifier.size(50.dp),
                                    tint = MaterialTheme.colorScheme.onPrimary
                                )
                                Text(
                                    text = "Info",
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = MaterialTheme.colorScheme.onPrimary
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.weight(0.05f))
                }
            }
            Text(text = "Free To Watch", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(10.dp))
            anime.value?.data?.let {
                AnimeGrid(listofAnime = it,
                    modifier = Modifier.padding(innerPadding)
                )
            }
        }
    }
}

@Composable
fun AnimeGrid(
    listofAnime: List<Data>,
    modifier: Modifier
) {

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier.fillMaxSize()
            .padding(bottom = 80.dp),
        contentPadding = PaddingValues(
            start = 10.dp,
            top = 10.dp,
            end = 0.dp,
            bottom = 10.dp,
        ),
    ) {

        items(listofAnime) { anime ->
            AnimeCard(animeTitle = anime.attributes?.titles?.enJp,
                animePoster = anime.attributes?.posterImage?.medium
                )
        }
    }
}
@Composable
fun AnimeCard(
    animeTitle: String?,
    animePoster : String?,
) {
    Column (
        modifier = Modifier
            .padding(end = 10.dp, bottom = 10.dp)
            .wrapContentHeight()
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){

        AsyncImage(model =ImageRequest
            .Builder(context = LocalContext.current)
            .data(animePoster)
            .crossfade(true)
            .build() ,
            contentDescription = "poster",
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 120.dp, max = 190.dp)
                .clip(RoundedCornerShape(10.dp))
            ,
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(5.dp))
        if (animeTitle != null) {
            Text(
                text = animeTitle,
                style = MaterialTheme.typography.bodyLarge,
            )
        }
    }
}