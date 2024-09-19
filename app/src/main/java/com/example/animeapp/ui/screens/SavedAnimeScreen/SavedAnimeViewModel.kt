package com.example.animeapp.ui.screens.SavedAnimeScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.animeapp.data.AnimeDataRepository
import com.example.animeapp.data.Favorite
import com.example.animeapp.model.AnimeData
import com.example.animeapp.ui.screens.HomePage.AnimeDataUiState
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
sealed interface SavedAnimeUiState {
    data class Success(val savedAnimeData: List<Favorite>?) : SavedAnimeUiState
    object Error : SavedAnimeUiState
    object Loading : SavedAnimeUiState
}

class SavedAnimeViewModel(
    private val animeDataRepository: AnimeDataRepository
) : ViewModel() {
    private val _savedAnimeUiState = MutableStateFlow<SavedAnimeUiState>(SavedAnimeUiState.Loading)
    val savedAnimeUiState = _savedAnimeUiState

    private val firestore = FirebaseFirestore.getInstance()
    fun removeSavedAnime() {
        _savedAnimeUiState.value = SavedAnimeUiState.Success(null)
    }
    fun getAllSavedAnime(userId: String) {
        viewModelScope.launch {
            _savedAnimeUiState.value = SavedAnimeUiState.Loading

            try {
                val favorites = withContext(Dispatchers.IO) {
                    val querySnapshot = firestore.collection("favorite")
                        .whereEqualTo("userId", userId)
                        .get()
                        .await()

                    querySnapshot.documents.map { document ->
                        Favorite(
                            animeId = document.getLong("animeId")?.toInt() ?: 0,
                            animePoster = document.getString("animePoster") ?: "",
                            animeName = document.getString("animeName") ?: "",
                            userId = document.getString("userId") ?: ""
                        )
                    }
                }
                _savedAnimeUiState.value = SavedAnimeUiState.Success(favorites)
            } catch (e: Exception) {
                Log.e("SavedAnimeViewModel", "Error fetching saved anime", e)
                _savedAnimeUiState.value = SavedAnimeUiState.Error
            }
        }

    }
}
