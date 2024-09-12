package com.example.animeapp.ui.screens.AnimeChapterScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.animeapp.data.AnimeDataRepository
import com.example.animeapp.model.AnimeChapters
import com.example.animeapp.ui.screens.AnimeDetailsPage.AnimeDetailsUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface AnimeChaptersUiState {
    data class Success(val animeChapters: AnimeChapters?) : AnimeChaptersUiState
    object Error : AnimeChaptersUiState
    object Loading : AnimeChaptersUiState
}

class AnimeChaptersViewModel (private val animeDataRepository: AnimeDataRepository) : ViewModel() {

    private var _animeChaptersUiState = MutableStateFlow<AnimeChaptersUiState>(AnimeChaptersUiState.Loading)
    val animeChaptersUiState get() = _animeChaptersUiState.asStateFlow()
    private val _currentPage = MutableStateFlow(1)
    val currentPage = _currentPage
    fun updateCurrentPage(currentPage  : Int) {
        _currentPage.value = currentPage
    }
    fun getAllChapters(id  :Int, page : Int) {
        viewModelScope.launch {
            _animeChaptersUiState.value = AnimeChaptersUiState.Loading
            _animeChaptersUiState.value = try {
                val result = animeDataRepository.getAnimeChapters(id=id,page=page)
                Log.d("animeData", result.toString())
                AnimeChaptersUiState.Success(result)

            } catch (e: IOException) {
                Log.e("AnimeChapter", "IOException while fetching anime data: ${e.message}")
                AnimeChaptersUiState.Error
            } catch (e: HttpException) {
                Log.e("AnimeChapter", "HttpException while fetching anime data: ${e.message}")
              AnimeChaptersUiState.Error
            }
        }
    }
}