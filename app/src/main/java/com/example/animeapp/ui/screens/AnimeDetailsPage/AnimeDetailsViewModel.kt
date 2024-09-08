package com.example.animeapp.ui.screens.AnimeDetailsPage

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.animeapp.data.AnimeDataRepository
import com.example.animeapp.model.AnimeDataById
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AnimeDetailsViewModel (private val animeDataRepository: AnimeDataRepository) : ViewModel() {
    private var _animeDataByIdUiState = MutableStateFlow<AnimeDataById?>(null)
    val animeDataByIdUiState get() = _animeDataByIdUiState.asStateFlow()
    fun getAnimeDataById(id : Int) {
        viewModelScope.launch {
            try {
                _animeDataByIdUiState.value=animeDataRepository.getAnimeDataById(id = id)
            } catch (e: Exception) {
                Log.e("HomePageViewModel", "Error fetching data", e)
                _animeDataByIdUiState.value = null
            }
        }
    }
}