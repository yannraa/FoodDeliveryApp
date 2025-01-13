package com.example.tdm.ui.screens.login

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.tdm.data.repository.AuthRepository
import com.example.tdm.utils.TokenManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: AuthRepository) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _loginSuccess = MutableStateFlow(false)
    val loginSuccess: StateFlow<Boolean> = _loginSuccess

    private val _loginError = MutableStateFlow<String?>(null)
    val loginError: StateFlow<String?> = _loginError

    fun login(context: Context, identifier: String, password: String) {
        // Vérification des champs
        if (identifier.isBlank() || password.isBlank()) {
            _loginError.value = "Please fill in all the fields."
            return
        }

        _isLoading.value = true
        _loginError.value = null

        // Appel à l'API via le repository
        viewModelScope.launch {
            val result = repository.login(identifier, password)

            result.onSuccess { response ->
                val token = result.getOrNull()?.jwt
                if (!token.isNullOrEmpty()) {
                    TokenManager.saveToken(context, token) // Sauvegarder le token
                    _loginSuccess.value = true
                } else {
                    _loginError.value = "Login failed: Invalid response"
                }
            }.onFailure { exception ->
                _loginError.value = exception.localizedMessage ?: "An error occurred."
            }

            _isLoading.value = false
        }
    }
}

class LoginViewModelFactory(private val repository: AuthRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
