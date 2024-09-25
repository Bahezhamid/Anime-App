package com.example.animeapp.ui.screens.EmailAndPasswordChangePage

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
class EmailAndPasswordChangeViewModel() : ViewModel() {
    private val _emailAndPasswordChangeUiState = MutableStateFlow(EmailAndPasswordChangeUiState())
    val emailAndPasswordChangeUiState: StateFlow<EmailAndPasswordChangeUiState> get() = _emailAndPasswordChangeUiState.asStateFlow()
    fun updateNewEmailTextFieldValue(newValue: String) {
        _emailAndPasswordChangeUiState.value = _emailAndPasswordChangeUiState.value.copy(newEmail = newValue)
    }
    fun updateOldEmailTextFieldValue(newValue: String) {
        _emailAndPasswordChangeUiState.value = _emailAndPasswordChangeUiState.value.copy(oldEmail = newValue)
    }
    fun updateConfirmEmailTextFieldValue(newValue: String) {
        _emailAndPasswordChangeUiState.value = _emailAndPasswordChangeUiState.value.copy(confirmEmail = newValue)
    }
    fun updatePasswordTextFieldValue(newValue: String) {
        _emailAndPasswordChangeUiState.value = _emailAndPasswordChangeUiState.value.copy(password = newValue)
    }
    fun updateNewPasswordTextFieldValue(newValue: String) {
        _emailAndPasswordChangeUiState.value = _emailAndPasswordChangeUiState.value.copy(newPassword = newValue)
    }
    fun updateOldPasswordTextFieldValue(newValue: String) {
        _emailAndPasswordChangeUiState.value = _emailAndPasswordChangeUiState.value.copy(oldPassword = newValue)
    }
    fun updateConfirmPasswordTextFieldValue(newValue: String) {
        _emailAndPasswordChangeUiState.value = _emailAndPasswordChangeUiState.value.copy(confirmPassword = newValue)
    }
    fun SignOut() {
        _emailAndPasswordChangeUiState.value = EmailAndPasswordChangeUiState()
    }
    fun updateEmail(
        emailAndPasswordChangeUiState: EmailAndPasswordChangeUiState,
        password: String
    ) {
        if (validateInput(emailAndPasswordChangeUiState, false)) {
            _emailAndPasswordChangeUiState.value = _emailAndPasswordChangeUiState.value.copy(isLoading = true)

            val user = FirebaseAuth.getInstance().currentUser

            user?.let {
                val currentEmail = it.email
                val newEmail = emailAndPasswordChangeUiState.newEmail

                if (currentEmail == emailAndPasswordChangeUiState.oldEmail) {
                    Log.d("currentEmail", emailAndPasswordChangeUiState.oldEmail)

                    val credential = EmailAuthProvider.getCredential(currentEmail, password)

                    it.reauthenticate(credential).addOnCompleteListener { reauthTask ->
                        if (reauthTask.isSuccessful) {
                            try {
                                it.verifyBeforeUpdateEmail(newEmail)
                                    .addOnCompleteListener { verifyTask ->
                                        if (verifyTask.isSuccessful) {
                                           checkForEmailUpdateOnce(newEmail)
                                        } else {
                                            _emailAndPasswordChangeUiState.value = _emailAndPasswordChangeUiState.value.copy(
                                                isLoading = false,
                                                isSuccess = true,
                                                currentEmailError = "Failed to send verification email"
                                            )
                                        }
                                    }
                            } catch (e: Exception) {
                                _emailAndPasswordChangeUiState.value = _emailAndPasswordChangeUiState.value.copy(
                                    isLoading = false,
                                    isSuccess = false,
                                    currentEmailError = e.localizedMessage ?: "Unknown error occurred"
                                )
                            }
                        } else {
                            _emailAndPasswordChangeUiState.value = _emailAndPasswordChangeUiState.value.copy(
                                isLoading = false,
                                passwordError = "Wrong Email Or Password"
                            )
                        }
                    }
                } else {
                    _emailAndPasswordChangeUiState.value = _emailAndPasswordChangeUiState.value.copy(
                        isLoading = false,
                        isSuccess = false,
                        currentEmailError = "Current email does not match"
                    )
                }
            } ?: run {
                _emailAndPasswordChangeUiState.value = _emailAndPasswordChangeUiState.value.copy(
                    isLoading = false,
                    isSuccess = false,
                    currentEmailError = "User is not authenticated"
                )
            }
        } else {

            _emailAndPasswordChangeUiState.value = _emailAndPasswordChangeUiState.value.copy(
                currentEmailError = "Invalid input"
            )
        }
    }
    private fun checkForEmailUpdateOnce(newEmail: String) {
        val user = FirebaseAuth.getInstance().currentUser
        val maxRetries = 7
        var retryCount = 0
        var isUpdateCompleted = false
        viewModelScope.launch {
            while (!isUpdateCompleted && retryCount < maxRetries) {
                withContext(Dispatchers.IO) {
                    user?.reload()?.addOnCompleteListener { reloadTask ->
                        if (reloadTask.isSuccessful) {
                            if (user.email == newEmail) {

                                isUpdateCompleted = true
                                _emailAndPasswordChangeUiState.value = _emailAndPasswordChangeUiState.value.copy(
                                    isLoading = false,
                                    isSuccess = true,
                                    currentEmailError = "Email successfully updated"
                                )
                                Log.d("EmailUpdate", "Email successfully updated")
                            } else {

                                Log.d("EmailUpdate", "Email not updated yet, retrying...")
                            }
                        } else {
                            isUpdateCompleted = true
                            _emailAndPasswordChangeUiState.value = _emailAndPasswordChangeUiState.value.copy(
                                isLoading = false,
                                isSuccess = true,
                            )
                        }
                    }
                }

                retryCount++
                if (!isUpdateCompleted && retryCount < maxRetries) {
                    delay(5000L)
                }
            }

            if (!isUpdateCompleted) {
                _emailAndPasswordChangeUiState.value = _emailAndPasswordChangeUiState.value.copy(
                    isLoading = false,
                    isSuccess = false,
                    currentEmailError = "Email failed to update after 30 seconds"
                )
                Log.d("EmailUpdate", "Email failed to update after 30 seconds")
            }
        }
    }

    fun updatePassword(
        emailAndPasswordChangeUiState: EmailAndPasswordChangeUiState,
    ) {
        if (validateInput(emailAndPasswordChangeUiState, true)) {
            _emailAndPasswordChangeUiState.value = _emailAndPasswordChangeUiState.value.copy(isLoading = true)

            val user = FirebaseAuth.getInstance().currentUser

            user?.let {
                val currentEmail = it.email
                val currentPassword = emailAndPasswordChangeUiState.oldPassword

                val credential = EmailAuthProvider.getCredential(currentEmail!!, currentPassword)

                it.reauthenticate(credential).addOnCompleteListener { reauthTask ->
                    if (reauthTask.isSuccessful) {
                        try {
                            it.updatePassword(emailAndPasswordChangeUiState.newPassword).addOnCompleteListener { updateTask ->
                                if (updateTask.isSuccessful) {
                                    _emailAndPasswordChangeUiState.value = _emailAndPasswordChangeUiState.value.copy(
                                        isLoading = false,
                                        isSuccess = true,
                                        currentPasswordError = "Password successfully updated"
                                    )
                                } else {
                                    _emailAndPasswordChangeUiState.value = _emailAndPasswordChangeUiState.value.copy(
                                        isLoading = false,
                                        isSuccess = false,
                                        currentPasswordError = "Failed to update password"
                                    )
                                }
                            }
                        } catch (e: Exception) {
                            _emailAndPasswordChangeUiState.value = _emailAndPasswordChangeUiState.value.copy(
                                isLoading = false,
                                isSuccess = false,
                                currentPasswordError = e.localizedMessage ?: "Unknown error occurred"
                            )
                        }
                    } else {
                        _emailAndPasswordChangeUiState.value = _emailAndPasswordChangeUiState.value.copy(
                            isLoading = false,
                            currentPasswordError = reauthTask.exception?.message ?: "Reauthentication failed"
                        )
                    }
                }
            } ?: run {
                _emailAndPasswordChangeUiState.value = _emailAndPasswordChangeUiState.value.copy(
                    isLoading = false,
                    isSuccess = false,
                    currentPasswordError = "User is not authenticated"
                )
            }
        } else {
            _emailAndPasswordChangeUiState.value = _emailAndPasswordChangeUiState.value.copy(
                currentPasswordError = "Invalid input"
            )
        }
    }




    private fun validateInput(emailAndPasswordChangeUiState: EmailAndPasswordChangeUiState, iaPasswordChange : Boolean): Boolean {
        if(iaPasswordChange){
            val passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#\$%^&+=])(?=\\S+\$).{8,}\$"
            val isPasswordValid = emailAndPasswordChangeUiState.newPassword.matches(Regex(passwordPattern))
            val doPasswordsMatch = emailAndPasswordChangeUiState.newPassword== emailAndPasswordChangeUiState.confirmPassword
            var passwordError: String? = null
            var confirmPasswordError: String? = null
            if (!isPasswordValid) {
                passwordError = "Password must be at least 8 characters, include an upper case letter, a number, and a special character."
            }
            if (!doPasswordsMatch) {
                confirmPasswordError = "Passwords do not match."
            }
            _emailAndPasswordChangeUiState.value = _emailAndPasswordChangeUiState.value.copy(
                passwordError = passwordError,
                confirmPasswordError = confirmPasswordError
            )
            return  isPasswordValid && doPasswordsMatch
        }
        else {


            val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
            var confirmEmailError : String? = null

            val isEmailValid = emailAndPasswordChangeUiState.newEmail.matches(Regex(emailPattern))

            val doEmailsMatch =
                emailAndPasswordChangeUiState.newEmail == emailAndPasswordChangeUiState.confirmEmail

            var emailError: String? = null
            if (!isEmailValid) {
                emailError = "Invalid email format."
            }
            if (!doEmailsMatch) {
                confirmEmailError = "Emails do not match."
            }

            _emailAndPasswordChangeUiState.value = _emailAndPasswordChangeUiState.value.copy(
                emailError = emailError,
               confirmEmailError = confirmEmailError
            )
            return isEmailValid && doEmailsMatch
        }
    }

}
