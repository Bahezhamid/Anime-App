package com.example.animeapp.ui.screens.CharactersDetailsPage

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.animeapp.data.AnimeDataRepository
import com.example.animeapp.model.CharactersAllData
import com.example.animeapp.ui.screens.AnimeChapterScreen.AnimeChaptersUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface CharactersDetailsUiState {
    data class Success(val charactersDetails: CharactersAllData) : CharactersDetailsUiState
    object Error : CharactersDetailsUiState
    object Loading : CharactersDetailsUiState
}

class CharactersDetailsViewModel(private val animeDataRepository: AnimeDataRepository) : ViewModel() {
    private val _characterDetails = MutableStateFlow<CharactersDetailsUiState>(CharactersDetailsUiState.Loading)
    val charactersDetails = _characterDetails.asStateFlow()
    init {
        getCharactersDetail(145)
    }
    private fun getCharactersDetail(id : Int) {
        viewModelScope.launch {
            _characterDetails.value = CharactersDetailsUiState.Loading
            _characterDetails.value = try {
                val result = animeDataRepository.getAllCharactersData(id=id)
                Log.d("charactersData",result.toString())
                CharactersDetailsUiState.Success(result)
            }catch (e: IOException) {
                Log.e("AnimeChapter", "IOException while fetching anime data: ${e.message}")
                CharactersDetailsUiState.Error
            } catch (e: HttpException) {
                Log.e("AnimeChapter", "HttpException while fetching anime data: ${e.message}")
                CharactersDetailsUiState.Error
            }

        }

    }
}