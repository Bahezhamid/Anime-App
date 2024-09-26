package com.example.animeapp.ui.screens.logInAndSignUp

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.animeapp.data.AnimeRepository
import com.example.animeapp.data.UserPreferencesRepository
import com.example.animeapp.data.Users
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class LoginAndSignUpViewModel(
    private val animeRepository: AnimeRepository,
     private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginAndSignUpUiState())
    val uiState: StateFlow<LoginAndSignUpUiState> get() = _uiState.asStateFlow()

    private val _loginUiState = MutableStateFlow(UsersUiState())
    val loginUiState : StateFlow<UsersUiState> get() = _loginUiState.asStateFlow()
    private val firebaseAuth: FirebaseAuth = Firebase.auth

    init {

        viewModelScope.launch {

            userPreferencesRepository.userEmail.collect { savedEmail ->
                userPreferencesRepository.userPassword.collect { savedPassword ->
                    if (!savedEmail.isNullOrEmpty() && !savedPassword.isNullOrEmpty()) {
                        login(savedEmail, savedPassword)
                    } else {
                        _loginUiState.value = _loginUiState.value.copy(isLoading = false)
                }
                }
            }
        }
    }
    fun signOut() {
        firebaseAuth.signOut()
        _loginUiState.value = UsersUiState()
        _uiState.value = LoginAndSignUpUiState(
            email =  "",
            password = "",
            isRememberMeOn = false,
            confirmPassword = "",
            userName = "",
        )
        viewModelScope.launch {
            userPreferencesRepository.saveUserCredentials("", "")
        }
    }
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
            _loginUiState.value = _loginUiState.value.copy(
                isLoading = true
            )
            try {

                val authResult = withContext(Dispatchers.IO) {
                    firebaseAuth.createUserWithEmailAndPassword(signUpState.email, signUpState.password).await()
                }

                val firebaseUser = firebaseAuth.currentUser
                val userId = firebaseUser?.uid

                if (userId != null) {

                    val user = hashMapOf(
                        "id" to userId,
                        "userName" to signUpState.userName,
                        "email" to signUpState.email,
                    )
                    val db = Firebase.firestore
                    db.collection("users").document(userId)
                        .set(user)
                        .await()
                    _loginUiState.value = UsersUiState(
                        email = signUpState.email,
                        userName = signUpState.userName,
                        isSuccess = true,
                        isLoading = false,
                        userid = userId
                    )
                    if (_uiState.value.isRememberMeOn) {
                        viewModelScope.launch {
                            userPreferencesRepository.saveUserCredentials(signUpState.email, signUpState.password)
                        }
                    }
                }
            } catch (e: Exception) {

                _uiState.value = _uiState.value.copy(
                    emailError = "Not Valid Email"
                )
                _loginUiState.value = _loginUiState.value.copy(
                    isSuccess = false,
                    isLoading = false
                )
            }
        }
    }
    suspend fun login(email: String, password: String) {
        _loginUiState.value = _loginUiState.value.copy(
            isLoading = true
        )
        try {

            val authResult = withContext(Dispatchers.IO) {
                firebaseAuth.signInWithEmailAndPassword(email, password).await()
            }

            val firebaseUser = firebaseAuth.currentUser

            if (firebaseUser != null) {

                val userId = firebaseUser.uid

                val userDocument = Firebase.firestore.collection("users").document(userId).get().await()

                if (userDocument.exists()) {
                    val userData = userDocument.data
                    val userName = userData?.get("userName") as? String ?: ""
                    _loginUiState.value = UsersUiState(
                        email = firebaseUser.email,
                        userName = userName,
                        isSuccess = true,
                        isLoading = false,
                        userid = firebaseUser.uid
                    )
                    if (_uiState.value.isRememberMeOn) {
                        viewModelScope.launch {
                            firebaseUser.email?.let {
                                userPreferencesRepository.saveUserCredentials(
                                    it,password)
                            }
                        }
                    }
                } else {
                    _loginUiState.value = _loginUiState.value.copy(
                        errorMessage = "User data not found ",
                        isSuccess = false,
                        isLoading = false
                    )
                }
            } else {
                _loginUiState.value = _loginUiState.value.copy(
                    errorMessage = "Authentication failed",
                    isSuccess = false,
                    isLoading = false
                )
            }
        } catch (e: Exception) {
            _loginUiState.value = _loginUiState.value.copy(
                errorMessage = "Wrong Email Or Password",
                isSuccess = false,
                isLoading = false
            )
        }
    }
    fun forgetPassword(signUpState: LoginAndSignUpUiState) {
        if(  validateInput(signUpState = signUpState, isForgetPassword = true)) {
            _loginUiState.value = _loginUiState.value.copy(
                isLoading = true
            )
            FirebaseAuth.getInstance().sendPasswordResetEmail(signUpState.email)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        _loginUiState.value = _loginUiState.value.copy(
                            isLoading = false,
                            isPasswordResetSent = true
                        )

                    } else {
                        _loginUiState.value = _loginUiState.value.copy(
                            isSuccess = false,
                            isLoading = false,
                            isPasswordResetSent = false,
                            errorMessage = "Wrong Email"
                        )
                    }
                }
        }
    }
    private fun validateInput(signUpState: LoginAndSignUpUiState ,isForgetPassword : Boolean = false): Boolean {

        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        val passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#\$%^&+=])(?=\\S+\$).{8,}\$"
        if (isForgetPassword) {
            val isEmailValid = signUpState.email.matches(Regex(emailPattern))
            var emailError : String? = null
            if (!isEmailValid) {
                emailError = "Invalid email format."
            }
            _uiState.value = _uiState.value.copy(
                emailError = emailError,
            )

            return isEmailValid
        } else {
            val isEmailValid = signUpState.email.matches(Regex(emailPattern))
            val isPasswordValid = signUpState.password.matches(Regex(passwordPattern))
            val doPasswordsMatch = signUpState.password == signUpState.confirmPassword


            var emailError: String? = null
            var passwordError: String? = null
            var confirmPasswordError: String? = null
            var emptyUserName: String? = null
            if (!isEmailValid) {
                emailError = "Invalid email format."
            }

            if (!isPasswordValid) {
                passwordError =
                    "Password must be at least 8 characters, include an upper case letter, a number, and a special character."
            }

            if (!doPasswordsMatch) {
                confirmPasswordError = "Passwords do not match."
            }
            if (signUpState.userName == "") {
                emptyUserName = "Please Enter Your Username."
            }
            _uiState.value = _uiState.value.copy(
                emailError = emailError,
                passwordError = passwordError,
                confirmPasswordError = confirmPasswordError,
                userNameError = emptyUserName
            )

            return isEmailValid && isPasswordValid && doPasswordsMatch && signUpState.userName != ""
        }

    }
}
