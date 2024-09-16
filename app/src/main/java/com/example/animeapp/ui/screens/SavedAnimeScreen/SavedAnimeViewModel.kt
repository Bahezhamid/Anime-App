package com.example.animeapp.ui.screens.SavedAnimeScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.animeapp.data.AnimeDataRepository
import com.example.animeapp.data.AnimeRepository
import com.example.animeapp.data.Favorite
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SavedAnimeViewModel(
    private val animeDataRepository: AnimeDataRepository,
    private val animeRepository: AnimeRepository
) : ViewModel() {
    private val _savedAnimeUiState = MutableStateFlow<List<Favorite>>(emptyList())
    val savedAnimeUiState: StateFlow<List<Favorite>> = _savedAnimeUiState
    fun getAllSavedAnime(userId : Int) {
        viewModelScope.launch {
            _savedAnimeUiState.value =  withContext(Dispatchers.IO) {
             animeRepository.getAllSavedAnime(userId = userId)
            }
        }
    }
}