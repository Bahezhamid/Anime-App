package com.example.animeapp.ui.screens.HomePage

import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.animeapp.data.AnimeDataRepository
import com.example.animeapp.data.AnimeRepository
import com.example.animeapp.data.Favorite
import com.example.animeapp.model.AnimeData
import com.example.animeapp.ui.screens.logInAndSignUp.UsersUiState
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

sealed interface AnimeDataUiState {
    data class Success(val animeData: AnimeData?) : AnimeDataUiState
    object Error : AnimeDataUiState
    object Loading : AnimeDataUiState
}

class HomePageViewModel(
    private val animeDataRepository: AnimeDataRepository
) : ViewModel() {

    private val _loginUiState = MutableStateFlow(UsersUiState())
    val loginUiState: StateFlow<UsersUiState> get() = _loginUiState.asStateFlow()
    private var _uiState = MutableStateFlow<AnimeDataUiState>(AnimeDataUiState.Loading)
    val uiState get() = _uiState.asStateFlow()
    private var _isAnimeAddedToFavorite = MutableStateFlow<Boolean>(false)
    val isAnimeAddedToFavorite = _isAnimeAddedToFavorite

    private val firestore = FirebaseFirestore.getInstance()

    init {
        getAnimeData()
    }
    fun updateUserUiState(usersUiState: UsersUiState) {
        _loginUiState.value = usersUiState
    }

    fun updateFavoriteStatus(animeId: Int, userId: String) {
        viewModelScope.launch {
            _isAnimeAddedToFavorite.value = withContext(Dispatchers.IO) {
                val doc = firestore.collection("favorite")
                    .whereEqualTo("animeId", animeId)
                    .whereEqualTo("userId", userId)
                    .get()
                    .await()
                !doc.isEmpty
            }
        }
    }

    fun getAnimeData() {
        viewModelScope.launch {
            _uiState.value = AnimeDataUiState.Loading
            try {
                val result = animeDataRepository.getAnimeData(1)
                _uiState.value = AnimeDataUiState.Success(result)

                val animeId = (result?.data?.firstOrNull()?.malId) ?: return@launch
                updateFavoriteStatus(animeId, _loginUiState.value.userid)

            } catch (e: IOException) {
                _uiState.value = AnimeDataUiState.Error
            } catch (e: HttpException) {
                _uiState.value = AnimeDataUiState.Error
            }
        }
    }


    fun insertAnimeToFavorite(favoriteAnime: FavoriteAnimeUiState) {
        _isAnimeAddedToFavorite.value = true

        viewModelScope.launch {
            val userId = loginUiState.value.userid
            val favorite = hashMapOf(
                "animeId" to favoriteAnime.animeId,
                "animePoster" to favoriteAnime.animePoster,
                "animeName" to favoriteAnime.animeName,
                "userId" to userId
            )
            try {
                firestore.collection("favorite").add(favorite)
                    .addOnSuccessListener {
                        Log.d("Firestore", "Anime added to favorites!")
                    }
                    .addOnFailureListener { e ->
                        Log.w("Firestore", "Error adding document", e)
                        _isAnimeAddedToFavorite.value = false
                    }
            } catch (e: Exception) {
                Log.e("Firestore", "Error adding favorite", e)
                _isAnimeAddedToFavorite.value = false
            }
        }
    }

    fun deleteAnimeFromFavorite(animeId: Int) {
        _isAnimeAddedToFavorite.value = false

        viewModelScope.launch {
            val userId = loginUiState.value.userid
            try {
                val querySnapshot = firestore.collection("favorite")
                    .whereEqualTo("animeId", animeId)
                    .whereEqualTo("userId", userId)
                    .get()
                    .await()

                for (document in querySnapshot) {
                    document.reference.delete()
                        .addOnSuccessListener {
                            Log.d("Firestore", "Anime removed from favorites!")
                        }
                        .addOnFailureListener { e ->
                            Log.w("Firestore", "Error removing document", e)

                            _isAnimeAddedToFavorite.value = true
                        }
                }
            } catch (e: Exception) {
                Log.e("Firestore", "Error deleting favorite", e)
                _isAnimeAddedToFavorite.value = true
            }
        }
    }
}
