package com.medai.app.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.medai.app.data.model.UiState
import com.medai.app.data.repository.ChatRepository
import com.medai.app.utils.PrefsManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repo: ChatRepository,
    private val prefs: PrefsManager
) : ViewModel() {

    private val _loginState = MutableStateFlow<UiState<Boolean>?>(null)
    val loginState = _loginState.asStateFlow()

    fun login(name: String, email: String, password: String, apiKey: String) {
        if (name.isBlank() || email.isBlank() || password.length < 6) {
            _loginState.value = UiState.Error("Please fill all fields. Password min 6 chars.")
            return
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _loginState.value = UiState.Error("Enter a valid email address")
            return
        }
        if (apiKey.isBlank() || !apiKey.startsWith("gsk_")) {
            _loginState.value = UiState.Error("Groq API key must start with gsk_")
            return
        }

        viewModelScope.launch {
            _loginState.value = UiState.Loading
            val result = repo.validateApiKey(apiKey)
            if (result.isSuccess) {
                prefs.saveUser(name, email)
                prefs.saveApiKey(apiKey)
                _loginState.value = UiState.Success(true)
            } else {
                _loginState.value = UiState.Error(result.exceptionOrNull()?.message ?: "Invalid API key")
            }
        }
    }
}
