package com.example.animeapp.ui.screens.logInAndSignUp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class LoginAndSignUpViewModel() : ViewModel() {
    private val _emailTextFieldValue = MutableStateFlow("")
    val emailTextFieldValue: StateFlow<String> get() = _emailTextFieldValue.asStateFlow()

    private val _passwordTextFieldValue = MutableStateFlow("")
    val passwordTextFieldValue : StateFlow<String> get() = _passwordTextFieldValue.asStateFlow()

    private val _confirmPasswordTextFieldValue = MutableStateFlow("")
    val confirmPasswordTextFieldValue : StateFlow<String> get()= _confirmPasswordTextFieldValue.asStateFlow()
    fun updateEmailTextFieldValue(newValue: String) {
        _emailTextFieldValue.value = newValue
    }
    fun updatePasswordTextFieldValue(newValue : String) {
        _passwordTextFieldValue.value = newValue
    }
    fun updateConfirmPasswordTextFieldValue(newValue : String) {
        _confirmPasswordTextFieldValue.value = newValue
    }
}

