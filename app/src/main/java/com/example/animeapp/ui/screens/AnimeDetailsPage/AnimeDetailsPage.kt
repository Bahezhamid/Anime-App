package com.example.animeapp.ui.screens.AnimeDetailsPage

import android.util.Log

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.animeapp.AnimeTopAppBar
import com.example.animeapp.R
import com.example.animeapp.model.AnimeCharacters
import com.example.animeapp.model.AnimeDataById
import com.example.animeapp.ui.AppViewModelProvider
import com.example.animeapp.ui.screens.AllAnimeScreen.GenreChip
import com.example.animeapp.ui.screens.HomePage.AnimeDataUiState
import com.example.animeapp.ui.screens.HomePage.ErrorScreen
import com.example.animeapp.ui.screens.HomePage.LoadingScreen
import kotlin.math.truncate


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimeDetailsPage(
    animeId : Int?,
    animeDetailsViewModel: AnimeDetailsViewModel = viewModel(factory = AppViewModelProvider.Factory),
    onBackPressed : () -> Unit
) {
    LaunchedEffect(animeId) {
        animeId?.let {
            animeDetailsViewModel.getAnimeDataById(it)
        }
    }
    Scaffold (
        topBar = {
            AnimeTopAppBar(
                title = "",
                isBackButton = true,
                onBackButtonClicked = onBackPressed
            )
        }
    ){ innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.primary),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (animeDetailsViewModel.animeDataByIdUiState.collectAsState().value) {
                is AnimeDetailsUiState.Loading -> LoadingScreen(modifier = Modifier.fillMaxSize())
                is AnimeDetailsUiState.Error -> ErrorScreen(
                    retryAction = {
                        if (animeId != null) {
                            animeDetailsViewModel.getAnimeDataById(animeId)
                        }
                    },
                    modifier = Modifier.fillMaxSize()
                )

                is AnimeDetailsUiState.Success ->
                    AnimeDetailsScreen(
                        allAnimeDetails =
                        (animeDetailsViewModel.animeDataByIdUiState.collectAsState()
                            .value as AnimeDetailsUiState.Success).animeDetails,
                        animeCharacters = (animeDetailsViewModel.animeDataByIdUiState.collectAsState()
                            .value as AnimeDetailsUiState.Success).animeCharacters,
                        onBackPressed = onBackPressed
                    )
            }

        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimeDetailsScreen(
    allAnimeDetails : AnimeDataById?,
    animeCharacters : AnimeCharacters?,
    onBackPressed: () -> Unit
) {
    val genresList = allAnimeDetails?.data?.genres?.map { it.name } ?: emptyList()
    val genresText = genresList.joinToString(separator = ", ")

    Spacer(modifier = Modifier.height(16.dp))
    val animePoster = (allAnimeDetails?.data?.trailer?.images?.largeImageUrl
        ?.takeIf { it.isNotBlank() }
        ?: allAnimeDetails?.data?.images?.jpg?.largeImageUrl)
    AsyncImage(
        model = ImageRequest
            .Builder(context = LocalContext.current)
            .data(animePoster)
            .crossfade(true)
            .build(),
        contentDescription = "poster",
        modifier = Modifier
            .padding(horizontal = 19.dp)
            .fillMaxWidth()
            .heightIn(min = 120.dp, max = 198.dp)
            .clip(RoundedCornerShape(10.dp)),
        contentScale = ContentScale.Crop,
        error = painterResource(id = R.drawable.ic_broken_image),
        placeholder = painterResource(id = R.drawable.loading_img)
    )

    Spacer(modifier = Modifier.height(15.dp))

    allAnimeDetails?.data?.titleEnglish?.let {
        Text(
            text = it,
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
    Spacer(modifier = Modifier.height(16.dp))
    Row (
        modifier = Modifier
            .padding(horizontal = 19.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ){
        Text(
            text = "About Anime",
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center
        )
    }
    Spacer(modifier = Modifier.height(5.dp))
    Column(
        modifier = Modifier
            .padding(horizontal = 19.dp)
            .heightIn(max = 250.dp)
            .shadow(
                elevation = 10.dp,
                shape = RoundedCornerShape(12.dp),
                clip = false
            )
            .clip(RoundedCornerShape(20.dp))
            .verticalScroll(rememberScrollState())
            .background(
                MaterialTheme.colorScheme.secondary
            )
            .padding(10.dp)
    ) {

        Text(
            text = "Released Date: ${extractYearFromDate(allAnimeDetails?.data?.aired?.from)}",
            color = MaterialTheme.colorScheme.onPrimary
        )
        Spacer(modifier = Modifier.height(3.dp))
        Text(
            text = "Popularity Rank: ${allAnimeDetails?.data?.popularity}",
            color = MaterialTheme.colorScheme.onPrimary
        )
        Spacer(modifier = Modifier.height(3.dp))
        Text(
            text = "Global Ranked: ${allAnimeDetails?.data?.rank}",
            color = MaterialTheme.colorScheme.onPrimary
        )
        Spacer(modifier = Modifier.height(3.dp))
        Text(
            text = "Genre: $genresText",
            color = MaterialTheme.colorScheme.onPrimary,
        )
        Spacer(modifier = Modifier.height(3.dp))
        Text(
            text = "Episodes:${allAnimeDetails?.data?.episodes}(${allAnimeDetails?.data?.duration})",
            color = MaterialTheme.colorScheme.onPrimary
        )
        Spacer(modifier = Modifier.height(3.dp))
        Text(
            text = "Description:",
            color = MaterialTheme.colorScheme.onPrimary
        )

        allAnimeDetails?.data?.synopsis?.let {
            Text(
                text = it,
                color = MaterialTheme.colorScheme.onPrimary,
                style = androidx.compose.ui.text.TextStyle(

                    lineHeight = 22.sp,
                    textAlign = TextAlign.Start,
                )
            )
        }

    }
    Spacer(modifier = Modifier.height(10.dp))
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        animeCharacters?.data?.let {
            items(it.filterNotNull()) { character ->
                CharacterCard(characterImage = character.character?.images?.jpg?.imageUrl)
            }
        }
    }
    Spacer(modifier = Modifier.height(15.dp))
    Button(
        onClick = { /*TODO*/ },
        modifier = Modifier
            .padding(horizontal = 19.dp)
            .width(200.dp)
            .height(80.dp)
        ,

        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.secondary,
            contentColor = MaterialTheme.colorScheme.onSecondary
        ),
        shape = RoundedCornerShape(20.dp)
    ) {
        Text(text = "Play", style = MaterialTheme.typography.headlineSmall)
    }
    Spacer(modifier = Modifier.height(16.dp))
    Row(
        modifier = Modifier
            .padding(horizontal = 19.dp)
            .fillMaxWidth()
            .height(80.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        IconsFunction(imageVector = Icons.Default.Add, iconName = "Add")
        IconsFunction(imageVector = Icons.Default.Share, iconName = "Share")
    }
}

@Composable
fun IconsFunction(
    imageVector: ImageVector,
    iconName : String,
) {
    Box(
        modifier = Modifier
            .size(80.dp)
            .clip(RoundedCornerShape(30.dp))
            .clickable { },
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Icon(
                imageVector = imageVector,
                contentDescription = "",
                modifier = Modifier.size(50.dp),
                tint = MaterialTheme.colorScheme.onPrimary
            )
            Text(
                text = iconName,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}
@Composable
fun CharacterCard(
    characterImage: String?,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier
            .size(80.dp)
            .clip(CircleShape)
            .background(Color.LightGray)
    ) {
        AsyncImage(
            model = ImageRequest
                .Builder(context = LocalContext.current)
                .data(characterImage)
                .crossfade(true)
                .build(),
            contentDescription = "Character Image",
            modifier = Modifier
                .fillMaxSize(),
            contentScale = ContentScale.Crop,
            error = painterResource(id = R.drawable.ic_broken_image),
            placeholder = painterResource(id = R.drawable.loading_img)
        )
    }
}

fun extractYearFromDate(dateString: String?): String {
    return dateString?.takeIf { it.length >= 4 }?.substring(0, 4) ?: ""
}