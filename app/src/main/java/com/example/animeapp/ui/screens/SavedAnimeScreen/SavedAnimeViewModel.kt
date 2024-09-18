package com.example.animeapp.ui.screens.SavedAnimeScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.animeapp.data.AnimeDataRepository
import com.example.animeapp.data.Favorite
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class SavedAnimeViewModel(
    private val animeDataRepository: AnimeDataRepository
) : ViewModel() {
    private val _savedAnimeUiState = MutableStateFlow<List<Favorite>>(emptyList())
    val savedAnimeUiState: StateFlow<List<Favorite>> = _savedAnimeUiState

    private val firestore = FirebaseFirestore.getInstance()

    fun getAllSavedAnime(userId: String) {
        viewModelScope.launch {
            _savedAnimeUiState.value = withContext(Dispatchers.IO) {
                try {
                    val querySnapshot = firestore.collection("favorite")
                        .whereEqualTo("userId", userId)
                        .get()
                        .await()

                    val favorites = querySnapshot.documents.map { document ->
                        Favorite(
                            animeId = document.getLong("animeId")?.toInt() ?: 0,
                            animePoster = document.getString("animePoster") ?: "",
                            animeName = document.getString("animeName") ?: "",
                            userId = document.getString("userId") ?: ""
                        )
                    }
                    favorites
                } catch (e: Exception) {
                    Log.e("SavedAnimeViewModel", "Error fetching saved anime", e)
                    emptyList()
                }
            }
        }
    }
}
