package com.example.animeapp.ui.screens.HomePage

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.animeapp.data.AnimeDataRepository
import com.example.animeapp.data.AnimeRepository
import com.example.animeapp.data.Favorite
import com.example.animeapp.model.AnimeData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

sealed interface AnimeDataUiState {
    data class Success(val animeData: AnimeData?) : AnimeDataUiState
    object Error : AnimeDataUiState
    object Loading : AnimeDataUiState
}

class HomePageViewModel(
    private val animeDataRepository: AnimeDataRepository,
    private val animeRepository: AnimeRepository
) : ViewModel() {

    private var _uiState = MutableStateFlow<AnimeDataUiState>(AnimeDataUiState.Loading)
    val uiState get() = _uiState.asStateFlow()

    private var _isAnimeAddedToFavorite = MutableStateFlow<Boolean>(false)
    val isAnimeAddedToFavorite = _isAnimeAddedToFavorite

    init {
        getAnimeData()
    }

    private fun updateFavoriteStatus(animeId: Int) {
        viewModelScope.launch {
            _isAnimeAddedToFavorite.value = withContext(Dispatchers.IO) {
                animeRepository.isFavorite(animeId)
            }
        }
    }

    fun getAnimeData() {
        viewModelScope.launch {
            _uiState.value = AnimeDataUiState.Loading
            try {
                val result = animeDataRepository.getAnimeData(1)
                _uiState.value = AnimeDataUiState.Success(result)

                val animeId = (result?.data?.firstOrNull()?.malId) ?: return@launch
                updateFavoriteStatus(animeId)

            } catch (e: IOException) {
                _uiState.value = AnimeDataUiState.Error
            } catch (e: HttpException) {
                _uiState.value = AnimeDataUiState.Error
            }
        }
    }

    fun insertAnimeToFavorite(favoriteAnime: FavoriteAnimeUiState) {
        viewModelScope.launch {
            animeRepository.insertAnimeToFavorite(favorite = Favorite(
                animeId = favoriteAnime.animeId,
                animePoster = favoriteAnime.animePoster,
                animeName = favoriteAnime.animeName,
                userId = favoriteAnime.userId
            ))
            updateFavoriteStatus(favoriteAnime.animeId)
        }
    }

    fun deleteAnimeFromFavorite(malId: Int) {
        viewModelScope.launch {
            animeRepository.deleteAnimeFromFavorite(malId)
            updateFavoriteStatus(malId)
        }
    }
}
