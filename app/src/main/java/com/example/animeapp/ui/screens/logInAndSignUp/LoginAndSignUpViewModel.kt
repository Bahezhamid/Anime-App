package com.example.animeapp.ui.screens.logInAndSignUp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.animeapp.data.AnimeRepository
import com.example.animeapp.data.Users
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class LoginAndSignUpViewModel(private val animeRepository: AnimeRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginAndSignUpUiState())
    val uiState: StateFlow<LoginAndSignUpUiState> get() = _uiState.asStateFlow()

    fun updateEmailTextFieldValue(newValue: String) {
        _uiState.value = _uiState.value.copy(email = newValue)
    }

    fun updateUserNameTextFieldValue(newValue: String) {
        _uiState.value = _uiState.value.copy(userName = newValue)
    }

    fun updatePasswordTextFieldValue(newValue: String) {
        _uiState.value = _uiState.value.copy(password = newValue)
    }

    fun updateConfirmPasswordTextFieldValue(newValue: String) {
        _uiState.value = _uiState.value.copy(confirmPassword = newValue)
    }

    fun updateRememberMeValue(rememberMe: Boolean) {
        _uiState.value = _uiState.value.copy(isRememberMeOn = rememberMe)
    }
    suspend fun saveAccount(signUpState: LoginAndSignUpUiState) {
        if (validateInput(signUpState)) {
            val user = Users(
                email = signUpState.email,
                userName = signUpState.userName,
                password = signUpState.password
            )

            animeRepository.insertItem(user)

            println("Account saved successfully.")
        } else {

            println("Invalid email or password.")
        }
    }

    private fun validateInput(signUpState: LoginAndSignUpUiState): Boolean {
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        val passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#\$%^&+=])(?=\\S+\$).{8,}\$"
        val isEmailValid = signUpState.email.matches(Regex(emailPattern))
        val isPasswordValid = signUpState.password.matches(Regex(passwordPattern))
        val doPasswordsMatch = signUpState.password == signUpState.confirmPassword
        return isEmailValid && isPasswordValid && doPasswordsMatch
    }


}
