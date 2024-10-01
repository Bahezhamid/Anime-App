package com.example.animeapp.ui.screens.SearchScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.animeapp.data.AnimeDataRepository
import com.example.animeapp.data.AnimeRepository
import com.example.animeapp.model.AnimeData
import com.example.animeapp.model.Data
import com.example.animeapp.ui.screens.AllAnimeScreen.AllAnimeByGenreUiState
import com.example.animeapp.ui.screens.AllAnimeScreen.AllGenreUiState
import com.example.animeapp.ui.screens.logInAndSignUp.LoginAndSignUpUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface SearchedAnimeUiState {
    data class Success( val SearchedAnime: AnimeData?) : SearchedAnimeUiState
    object Error : SearchedAnimeUiState
    object Loading : SearchedAnimeUiState
}
class SearchScreenViewModel ( private val animeDataRepository: AnimeDataRepository) : ViewModel() {
    private val _searchTextFieldValue = MutableStateFlow("")
    val searchTextFieldValue: StateFlow<String> get() = _searchTextFieldValue.asStateFlow()
    private var _searchedAnimeUiState = MutableStateFlow<SearchedAnimeUiState>(
        SearchedAnimeUiState.Success(null))
    val searchedAnimeUiState = _searchedAnimeUiState.asStateFlow()
    fun updateSearchTextFieldValue(newValue : String) {
        _searchTextFieldValue.value = newValue
    }

    fun getSearchedAnime(animeName : String) {
        viewModelScope.launch {
            try {
                _searchedAnimeUiState.value = SearchedAnimeUiState.Loading
                val searchedAnime = animeDataRepository.getAnimeByName(animeName = animeName)
                _searchedAnimeUiState.value = SearchedAnimeUiState.Success(searchedAnime)
            } catch (e: IOException) {
                _searchedAnimeUiState.value = SearchedAnimeUiState.Error
            } catch (e: HttpException) {
                _searchedAnimeUiState.value = SearchedAnimeUiState.Error
            } catch (e: Exception) {
                _searchedAnimeUiState.value = SearchedAnimeUiState.Error
            }
        }
    }
}