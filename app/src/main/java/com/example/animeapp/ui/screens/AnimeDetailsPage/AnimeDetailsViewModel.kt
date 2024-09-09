package com.example.animeapp.ui.screens.AnimeDetailsPage

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.animeapp.data.AnimeDataRepository
import com.example.animeapp.model.AnimeDataById
import com.example.animeapp.ui.screens.HomePage.AnimeDataUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface AnimeDetailsUiState {
    data class Success(val animeDetails : AnimeDataById?) : AnimeDetailsUiState
    object Error : AnimeDetailsUiState
    object Loading : AnimeDetailsUiState
}
class AnimeDetailsViewModel (private val animeDataRepository: AnimeDataRepository) : ViewModel() {
    private var _animeDataByIdUiState = MutableStateFlow<AnimeDetailsUiState>(AnimeDetailsUiState.Loading)
    val animeDataByIdUiState get() = _animeDataByIdUiState.asStateFlow()
    fun getAnimeDataById(id : Int) {
        viewModelScope.launch {
            _animeDataByIdUiState.value = AnimeDetailsUiState.Loading
            _animeDataByIdUiState.value = try {
                val result = animeDataRepository.getAnimeDataById(id = id)
                AnimeDetailsUiState.Success(
                    result
                )
            } catch (e: IOException) {
                AnimeDetailsUiState.Error
            } catch (e: HttpException) {
                AnimeDetailsUiState.Error
            }
        }
    }
}