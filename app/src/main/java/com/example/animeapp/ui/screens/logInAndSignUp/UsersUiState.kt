package com.example.animeapp.ui.screens.logInAndSignUp

data class UsersUiState(
    val email : String? = "",
    val password : String? = "",
    val userName : String? = "",
    val isSuccess : Boolean = false,
    var isLoading : Boolean = true,
    var isPasswordResetSent : Boolean = false,
    val errorMessage: String? = null,
    val userid : String = "",
)