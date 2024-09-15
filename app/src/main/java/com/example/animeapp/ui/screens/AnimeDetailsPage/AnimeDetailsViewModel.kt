package com.example.animeapp.ui.screens.AnimeDetailsPage

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.animeapp.data.AnimeDataRepository
import com.example.animeapp.data.AnimeRepository
import com.example.animeapp.data.Favorite
import com.example.animeapp.model.AnimeCharacters
import com.example.animeapp.model.AnimeDataById
import com.example.animeapp.ui.screens.HomePage.AnimeDataUiState
import com.example.animeapp.ui.screens.HomePage.FavoriteAnimeUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface AnimeDetailsUiState {
    data class Success(val animeDetails : AnimeDataById?, val animeCharacters: AnimeCharacters?) : AnimeDetailsUiState
    object Error : AnimeDetailsUiState
    object Loading : AnimeDetailsUiState
}
class AnimeDetailsViewModel (private val animeDataRepository: AnimeDataRepository, private val animeRepository: AnimeRepository) : ViewModel() {
    private var _animeDataByIdUiState = MutableStateFlow<AnimeDetailsUiState>(AnimeDetailsUiState.Loading)
    val animeDataByIdUiState get() = _animeDataByIdUiState.asStateFlow()
    private var _isAnimeAddedToFavorite = MutableStateFlow<Boolean>(false)
    val isAnimeAddedToFavorite = _isAnimeAddedToFavorite
    fun getAnimeDataById(id : Int) {
        viewModelScope.launch {
            _animeDataByIdUiState.value = AnimeDetailsUiState.Loading
            try {
                val animeDetails = animeDataRepository.getAnimeDataById(id)
                val animeCharacters = animeDataRepository.getAllCharacters(id)

                _animeDataByIdUiState.value = AnimeDetailsUiState.Success(
                    animeDetails = animeDetails,
                    animeCharacters = animeCharacters
                )
            } catch (e: IOException) {
                Log.e("AnimeDetailsViewModel", "IOException while fetching anime data: ${e.message}")
                _animeDataByIdUiState.value = AnimeDetailsUiState.Error
            } catch (e: HttpException) {
                Log.e("AnimeDetailsViewModel", "HttpException while fetching anime data: ${e.message}")
                _animeDataByIdUiState.value = AnimeDetailsUiState.Error
            }
        }
        fun insertAnimeToFavorite(favoriteAnime : FavoriteAnimeUiState) {
            Log.d("animeInser",favoriteAnime.animeName)
            viewModelScope.launch {
                animeRepository.insertAnimeToFavorite(favorite = Favorite(
                    animeId = favoriteAnime.animeId,
                    animePoster = favoriteAnime.animePoster,
                    animeName = favoriteAnime.animeName,
                    userId = favoriteAnime.userId
                )
                )
            }
            _isAnimeAddedToFavorite.value = true
        }
        fun deleteAnimeFromFavorite(malId : Int) {
            viewModelScope.launch {
                animeRepository.deleteAnimeFromFavorite(malId = malId)
            }
            _isAnimeAddedToFavorite.value = false
        }

    }

}