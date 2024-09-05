package com.example.animeapp.ui.screens.logInAndSignUp

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.animeapp.data.AnimeRepository
import com.example.animeapp.data.Users
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.withContext

class LoginAndSignUpViewModel(private val animeRepository: AnimeRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginAndSignUpUiState())
    val uiState: StateFlow<LoginAndSignUpUiState> get() = _uiState.asStateFlow()

    private val _loginUiState = MutableStateFlow(UsersUiState())
    val loginUiState : StateFlow<UsersUiState> get() = _loginUiState.asStateFlow()
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
        return withContext(Dispatchers.IO) {
            val emailExist = animeRepository.isEmailExist(signUpState.email)

            if (validateInput(signUpState) && emailExist == 0) {
                val user = Users(
                    email = signUpState.email,
                    userName = signUpState.userName,
                    password = signUpState.password
                )
                animeRepository.insertItem(user)
            } else if (emailExist > 0) {

                _uiState.value = _uiState.value.copy(emailError = "Email is already registered.")
            }
        }
    }
    suspend fun login(email: String, password: String) {
        animeRepository.login(email = email, password = password)
            .onStart {
                _loginUiState.value = _loginUiState.value.copy(
                    errorMessage = null,
                    isSuccess = false
                )
            }
            .map { user ->
                UsersUiState(
                    email = user.email,
                    password = user.password,
                    userName = user.userName,
                    isSuccess = true
                )
            }
            .catch { exception ->
                _loginUiState.value = _loginUiState.value.copy(
                    errorMessage = "Invalid email or password",
                    isSuccess = false
                )
            }
            .collect { newState ->
                _loginUiState.value = newState
                Log.d("hello", newState.toString())
            }
    }

    private fun validateInput(signUpState: LoginAndSignUpUiState): Boolean {
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        val passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#\$%^&+=])(?=\\S+\$).{8,}\$"

        val isEmailValid = signUpState.email.matches(Regex(emailPattern))
        val isPasswordValid = signUpState.password.matches(Regex(passwordPattern))
        val doPasswordsMatch = signUpState.password == signUpState.confirmPassword
        var emailError: String? = null
        var passwordError: String? = null
        var confirmPasswordError: String? = null
        var emptyUserName : String? = null
        if (!isEmailValid) {
            emailError = "Invalid email format."
        }

        if (!isPasswordValid) {
            passwordError = "Password must be at least 8 characters, include an upper case letter, a number, and a special character."
        }

        if (!doPasswordsMatch) {
            confirmPasswordError = "Passwords do not match."
        }
        if(signUpState.userName==""){
            emptyUserName = "Please Enter Your Username."
        }
        _uiState.value = _uiState.value.copy(
            emailError = emailError,
            passwordError = passwordError,
            confirmPasswordError = confirmPasswordError,
            userNameError = emptyUserName
        )

        return isEmailValid && isPasswordValid && doPasswordsMatch && signUpState.userName !=""
    }


}
