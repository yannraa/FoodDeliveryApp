package com.example.tdm.ui.screens.signup

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.tdm.data.model.SignUpRequest
import com.example.tdm.data.repository.AuthRepository
import com.example.tdm.utils.TokenManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SignUpViewModel(private val repository: AuthRepository) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _signUpSuccess = MutableStateFlow(false)
    val signUpSuccess: StateFlow<Boolean> = _signUpSuccess

    private val _signUpError = MutableStateFlow<String?>(null)
    val signUpError: StateFlow<String?> = _signUpError

    fun signUp(context: Context, fullName: String, phoneNumber: String, email: String, password: String) {
        // Vérification des champs
        if (fullName.isBlank() || phoneNumber.isBlank() || email.isBlank() || password.isBlank()) {
            _signUpError.value = "Please fill in all the fields."
            return
        }

        _isLoading.value = true
        _signUpError.value = null

        // Création de la requête d'inscription
        val signUpRequest = SignUpRequest(
            username = email, // Utilisation de l'email comme nom d'utilisateur
            email = email,
            password = password,

        )

        // Appel à l'API via le repository
        viewModelScope.launch {
            val result = repository.signUp(signUpRequest)

            result.onSuccess { response ->
                val token = response.jwt
                if (!token.isNullOrEmpty()) {
                    TokenManager.saveToken(context, token) // Sauvegarder le token
                    _signUpSuccess.value = true
                } else {
                    _signUpError.value = "Sign up failed: Invalid response"
                }
            }.onFailure { exception ->
                _signUpError.value = exception.localizedMessage ?: "An error occurred."
            }

            _isLoading.value = false
        }
    }
}

class SignUpViewModelFactory(private val repository: AuthRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SignUpViewModel::class.java)) {
            return SignUpViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}