package com.example.animeapp.ui.screens.AllAnimeScreen

import android.content.ClipData.Item
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.animeapp.AnimeBottomNavigationBar
import com.example.animeapp.AnimeTopAppBar
import com.example.animeapp.BottomNavItem
import com.example.animeapp.R
import com.example.animeapp.model.AllGenres
import com.example.animeapp.ui.AppViewModelProvider
import com.example.animeapp.ui.screens.AnimeChapterScreen.PageNavigationRow
import com.example.animeapp.ui.screens.HomePage.AnimeCard

@Composable
fun AllAnimePage(
  allAnimeViewScreenModel: AllAnimeViewScreenModel,
    onHomeButtonClicked : () -> Unit,
    onSavedButtonClicked : () -> Unit,
    onProfileClicked : () -> Unit,
    onAnimeClicked : (Int) -> Unit,
  onSearchButtonClicked : () -> Unit,

) {
    val allGenresUiState by allAnimeViewScreenModel.allGenresUiState.collectAsState()
    val allSelectedAnimeByGenreState by allAnimeViewScreenModel.allSelectedAnimeByGenre.collectAsState()
    Scaffold (
        topBar = {
            AnimeTopAppBar(
                title = "Browse",
                onSearchButtonClicked = onSearchButtonClicked
                )
        },
        bottomBar = {
            AnimeBottomNavigationBar(
                selectedTab = BottomNavItem.Book,
                onTabSelected = { BottomNavItem.Book},
                onHomeClick = onHomeButtonClicked,
                onSavedClick = onSavedButtonClicked,
                onBookClick = {},
                onProfileClick = onProfileClicked
            )

        },
    ) { innerPadding ->
    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .background(MaterialTheme.colorScheme.primary)
    ){
        when (allGenresUiState) {
            is AllGenreUiState.Loading -> LoadingScreen(
                modifier = Modifier.fillMaxSize()
            )
            is AllGenreUiState.Success -> {
                val genres = (allGenresUiState as AllGenreUiState.Success).genres
                AllAnimeScreen(
                    allGenres = genres,
                    onGenreClicked = { genre ->
                        if (genre != null) {
                            allAnimeViewScreenModel.getAnimeByGenre()
                            allAnimeViewScreenModel.updateCurrentPage(1)
                        }
                    },
                    allAnimeSelectedByGenre = allSelectedAnimeByGenreState,
                    onAnimeClicked = onAnimeClicked,
                    allAnimeSelected = {allAnimeViewScreenModel.fetchAllData()},
                    allAnimeViewScreenModel = allAnimeViewScreenModel
                )
                }
            is AllGenreUiState.Error -> ErrorScreen(
                retryAction = allAnimeViewScreenModel::fetchAllData,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
    }
}
@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.loading_img),
        contentDescription = "Loading"
    )
}

@Composable
fun ErrorScreen(retryAction: () -> Unit,modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_connection_error), contentDescription = ""
        )
        Text(text = "Loading Faild", modifier = Modifier.padding(16.dp))
        Button(
            onClick = retryAction,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondary
            )
        ) {
            Text(text = "retry")
        }
    }
}
@Composable
fun AllAnimeScreen(
    allGenres: AllGenres?,
    allAnimeSelected : () -> Unit,
    onGenreClicked: (Int?) -> Unit,
    allAnimeSelectedByGenre: AllAnimeByGenreUiState,
    onAnimeClicked: (Int) -> Unit,
    allAnimeViewScreenModel: AllAnimeViewScreenModel
    ) {
    var selectedGenre = allAnimeViewScreenModel.selectedGenre.collectAsState().value
    val currentPage = allAnimeViewScreenModel.currentPage.collectAsState()
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.background(MaterialTheme.colorScheme.secondary)
        ) {
            item {
                GenreChip(genre = "All",
                    isSelected = selectedGenre == 0,
                    onClick = {
                       allAnimeViewScreenModel.updateSelectedGenre(0)
                        allAnimeSelected()
                    }
                )
            }
            allGenres?.data?.let {
                items(it) { genre ->
                    GenreChip(
                        genre = genre.name,
                        isSelected = selectedGenre == genre.malId,
                        onClick = {
                            allAnimeViewScreenModel.updateSelectedGenre(genre.malId!!)
                            onGenreClicked(selectedGenre)
                        }
                    )
                }
            }
    }

    when(allAnimeSelectedByGenre) {
        is AllAnimeByGenreUiState.Loading -> LoadingScreen(
            modifier = Modifier.fillMaxSize()
        )
        is AllAnimeByGenreUiState.Error -> ErrorScreen(
            retryAction = { onGenreClicked(selectedGenre) },
            modifier = Modifier.fillMaxSize()
        )
        is AllAnimeByGenreUiState.Success -> {
            val allAnimeByGenre
            = (allAnimeSelectedByGenre as AllAnimeByGenreUiState.Success).animeList

                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    modifier = Modifier
                        .fillMaxSize(),
                    contentPadding = PaddingValues(
                        start = 10.dp,
                        top = 20.dp,
                        end = 0.dp,
                        bottom = 10.dp,
                    ),
                ) {
                    items(allAnimeByGenre?.data!!.filterNotNull()) { anime ->
                        AnimeCard(animeId = anime.malId,
                            animeTitle = anime.title,
                            animePoster = anime.images?.jpg?.imageUrl,
                            onAnimeClicked = onAnimeClicked,
                        )
                    }
                    item (  span = { GridItemSpan(3) }){
                        allAnimeByGenre.pagination?.lastVisiblePage?.let {
                            PageNavigationRow(
                                currentPage =currentPage.value,
                                totalPages = it,
                                onPageClick ={ page ->
                                    allAnimeViewScreenModel.updateCurrentPage(page)
                                    selectedGenre.let { it1 -> allAnimeViewScreenModel.getAnimeByGenre() }
                                },
                            )
                        }
                    }
                }

        }
    }

}
@Composable
fun GenreChip(
    genre: String?,
    isSelected : Boolean,
    onClick: () -> Unit)
{
    val backgroundColor = if (isSelected) {
        MaterialTheme.colorScheme.primary
    } else {
       Color.Transparent
    }

    Button(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
          containerColor = backgroundColor
        ),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
    ) {
        if (genre != null) {
            Text(text = genre, color = MaterialTheme.colorScheme.onPrimary)
        }
    }
}
