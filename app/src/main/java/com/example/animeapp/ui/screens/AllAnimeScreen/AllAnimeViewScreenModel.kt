package com.example.animeapp.ui.screens.AllAnimeScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.animeapp.data.AnimeDataRepository
import com.example.animeapp.model.AllGenres
import com.example.animeapp.model.AnimeData
import com.example.animeapp.model.Data
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface AllGenreUiState {
    data class Success(val genres: AllGenres?) : AllGenreUiState
    object Error : AllGenreUiState
    object Loading : AllGenreUiState
}
sealed interface AllAnimeByGenreUiState {
    data class Success( val animeList: AnimeData?) : AllAnimeByGenreUiState
    object Error : AllAnimeByGenreUiState
    object Loading : AllAnimeByGenreUiState
}

class AllAnimeViewScreenModel (private val animeDataRepository: AnimeDataRepository) : ViewModel() {
    private var _allGenresUiState = MutableStateFlow<AllGenreUiState>(AllGenreUiState.Loading)
    val allGenresUiState get() = _allGenresUiState.asStateFlow()

    private var _allSelectedAnimeByGenre = MutableStateFlow<AllAnimeByGenreUiState>(AllAnimeByGenreUiState.Loading)
    val allSelectedAnimeByGenre = _allSelectedAnimeByGenre.asStateFlow()

    private var allAnimeData: AnimeData? = null

    private var _selectedGenre = MutableStateFlow(0)
    val selectedGenre = _selectedGenre
    private val _currentPage = MutableStateFlow(1)
    val currentPage = _currentPage

    init {
        fetchAllData()
    }
    fun updateSelectedGenre(selectedGenreId : Int) {
        _selectedGenre.value = selectedGenreId
    }
    fun updateCurrentPage(currentPage  : Int) {
        _currentPage.value = currentPage
    }
    fun fetchAllData() {
        viewModelScope.launch {
            try {
                _allGenresUiState.value = AllGenreUiState.Loading
                val genres = animeDataRepository.getAllGenres()
                _allGenresUiState.value = AllGenreUiState.Success(genres)
                _allSelectedAnimeByGenre.value = AllAnimeByGenreUiState.Loading
                val fetchedAnimeData = animeDataRepository.getAnimeData(1)
                allAnimeData = fetchedAnimeData
                _allSelectedAnimeByGenre.value = AllAnimeByGenreUiState.Success(allAnimeData)
            } catch (e: IOException) {
                _allGenresUiState.value = AllGenreUiState.Error
                _allSelectedAnimeByGenre.value = AllAnimeByGenreUiState.Error
            } catch (e: HttpException) {
                _allGenresUiState.value = AllGenreUiState.Error
                _allSelectedAnimeByGenre.value = AllAnimeByGenreUiState.Error
            } catch (e: Exception) {
                _allGenresUiState.value = AllGenreUiState.Error
                _allSelectedAnimeByGenre.value = AllAnimeByGenreUiState.Error
            }
        }
    }

    fun getAnimeByGenre(genreId: Int , page : Int) {
        viewModelScope.launch {
            _allSelectedAnimeByGenre.value = AllAnimeByGenreUiState.Loading

            _allSelectedAnimeByGenre.value = try {
                val filteredAnime = animeDataRepository.getAnimeByGenre(genreId = genreId , page = page)
                allAnimeData = filteredAnime
                AllAnimeByGenreUiState.Success(allAnimeData)
            } catch (e: IOException) {
                AllAnimeByGenreUiState.Error
            } catch (e: HttpException) {
                AllAnimeByGenreUiState.Error
            } catch (e: Exception) {
                AllAnimeByGenreUiState.Error
            }
        }
    }
}
