package com.example.animeapp.ui.screens.logInAndSignUp
data class LoginAndSignUpUiState(
    val email: String = "",
    val userName: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val isRememberMeOn: Boolean = true,
    val emailError: String? = null,
    val passwordError: String? = null
)
