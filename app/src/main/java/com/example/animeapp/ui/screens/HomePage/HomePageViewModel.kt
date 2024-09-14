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
import retrofit2.HttpException
import java.io.IOException

sealed interface AnimeDataUiState {
    data class Success(val animeData: AnimeData?) : AnimeDataUiState
    object Error : AnimeDataUiState
    object Loading : AnimeDataUiState
}
class HomePageViewModel(private val animeDataRepository: AnimeDataRepository) : ViewModel(){
    private var _uiState = MutableStateFlow<AnimeDataUiState>(AnimeDataUiState.Loading)
    val uiState get() = _uiState.asStateFlow()
    init {
        getAnimeData()
    }
     fun getAnimeData() {
        viewModelScope.launch {
            _uiState.value = AnimeDataUiState.Loading
            _uiState.value = try {
                val result = animeDataRepository.getAnimeData(1)
                AnimeDataUiState.Success(
                    result
                )
            } catch (e: IOException) {
                AnimeDataUiState.Error
            } catch (e: HttpException) {
                AnimeDataUiState.Error
            }
        }
    }
}