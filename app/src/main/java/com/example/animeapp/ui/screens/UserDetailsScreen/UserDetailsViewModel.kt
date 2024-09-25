package com.example.animeapp.ui.screens.UserDetailsScreen

import androidx.lifecycle.ViewModel
import com.example.animeapp.data.AnimeRepository
import com.example.animeapp.data.UserPreferencesRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.tasks.await

class UserDetailsViewModel(
) : ViewModel() {
    val _favoriteCount = MutableStateFlow(0)
    val favoriteCount = _favoriteCount.asStateFlow()
    suspend fun getFavoriteCount(userId: String) {
        val db: FirebaseFirestore = Firebase.firestore

        _favoriteCount.value =  try {
            val querySnapshot = db.collection("favorite")
                .whereEqualTo("userId", userId)
                .get()
                .await()
            querySnapshot.size()
        } catch (e: Exception) {
            e.printStackTrace()
            0
        }
    }
}
