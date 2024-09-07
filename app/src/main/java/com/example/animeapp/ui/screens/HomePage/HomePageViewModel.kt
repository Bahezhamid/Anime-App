package com.example.animeapp.ui.screens.HomePage

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.animeapp.data.AnimeDataRepository
import com.example.animeapp.model.AnimeData
import com.example.animeapp.model.Data
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomePageViewModel(private val animeDataRepository: AnimeDataRepository) : ViewModel(){
    private var _uiState = MutableStateFlow<AnimeData?>(null)
    val uiState get() = _uiState.asStateFlow()
    init {
        getAnimeData()
    }
    private fun getAnimeData() {
        viewModelScope.launch {
            try {
              _uiState.value =animeDataRepository.getAnimeData(3)
            } catch (e: Exception) {
                Log.e("HomePageViewModel", "Error fetching data", e)
                _uiState.value = null
            }
        }
    }
}