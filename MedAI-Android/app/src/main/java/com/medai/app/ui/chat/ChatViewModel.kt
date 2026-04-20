package com.medai.app.ui.chat

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.medai.app.data.model.ChatMessage
import com.medai.app.data.model.Conversation
import com.medai.app.data.model.UiState
import com.medai.app.data.repository.ChatRepository
import com.medai.app.utils.PrefsManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val repo: ChatRepository,
    private val prefs: PrefsManager
) : ViewModel() {

    // ── Preferences ────────────────────────────────────────
    val apiKey    = prefs.apiKey.stateIn(viewModelScope, SharingStarted.Eagerly, "")
    val darkMode  = prefs.darkMode.stateIn(viewModelScope, SharingStarted.Eagerly, true)
    val language  = prefs.language.stateIn(viewModelScope, SharingStarted.Eagerly, "en")
    val userName  = prefs.userName.stateIn(viewModelScope, SharingStarted.Eagerly, "")
    val userEmail = prefs.userEmail.stateIn(viewModelScope, SharingStarted.Eagerly, "")

    // ── Conversations ──────────────────────────────────────
    private val _conversations = MutableStateFlow<List<Conversation>>(emptyList())
    val conversations = _conversations.asStateFlow()

    private val _activeConvId = MutableStateFlow<String?>(null)
    val activeConvId = _activeConvId.asStateFlow()

    val activeMessages: StateFlow<List<ChatMessage>> = combine(
        _conversations, _activeConvId
    ) { convs, id ->
        convs.find { it.id == id }?.messages?.toList() ?: emptyList()
    }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    // ── Sending state ──────────────────────────────────────
    private val _sendState = MutableStateFlow<UiState<String>?>(null)
    val sendState = _sendState.asStateFlow()

    private val _streamingText = MutableStateFlow("")
    val streamingText = _streamingText.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    // ── Pending image ──────────────────────────────────────
    private val _pendingImageUri = MutableStateFlow<Uri?>(null)
    val pendingImageUri = _pendingImageUri.asStateFlow()

    fun setPendingImage(uri: Uri?) { _pendingImageUri.value = uri }
    fun clearPendingImage() { _pendingImageUri.value = null }

    // ── New conversation ───────────────────────────────────
    fun newConversation() {
        _activeConvId.value = null
        _streamingText.value = ""
    }

    // ── Select conversation ────────────────────────────────
    fun selectConversation(id: String) {
        _activeConvId.value = id
    }

    // ── Send message ───────────────────────────────────────
    fun sendMessage(userText: String) {
        val key = apiKey.value
        if (key.isBlank()) {
            _sendState.value = UiState.Error("NO_KEY")
            return
        }
        if (userText.isBlank() && _pendingImageUri.value == null) return
        if (_isLoading.value) return

        val imageUri = _pendingImageUri.value
        val text = userText.ifBlank {
            if (language.value == "ta") "இந்த படத்தில் என்ன ஆரோக்கிய நிலை உள்ளது?"
            else "What health condition is shown in this image?"
        }

        viewModelScope.launch {
            _isLoading.value = true
            _streamingText.value = ""
            clearPendingImage()

            // Get or create conversation
            var convId = _activeConvId.value
            if (convId == null) {
                convId = UUID.randomUUID().toString()
                val title = text.take(40) + if (text.length > 40) "…" else ""
                val newConv = Conversation(id = convId, title = title)
                _conversations.value = listOf(newConv) + _conversations.value
                _activeConvId.value = convId
            }

            // Add user message
            val userMsg = ChatMessage(
                role      = "user",
                content   = text,
                imageUri  = imageUri?.toString()
            )
            appendMessage(convId, userMsg)

            // Add placeholder assistant message
            val asstId  = UUID.randomUUID().toString()
            val asstMsg = ChatMessage(id = asstId, role = "assistant", content = "", isLoading = true)
            appendMessage(convId, asstMsg)

            try {
                val history = _conversations.value
                    .find { it.id == convId }
                    ?.messages
                    ?.filter { !it.isLoading }
                    ?: emptyList()

                val reply = repo.sendMessageSync(key, history)
                updateMessage(convId, asstId, reply)
                _sendState.value = UiState.Success(reply)
            } catch (e: Exception) {
                val errMsg = when {
                    e.message == "INVALID_KEY" -> "Invalid API key. Please update your Groq key in Settings."
                    else -> "Error: ${e.message ?: "Unknown error"}. Please try again."
                }
                updateMessage(convId, asstId, errMsg)
                _sendState.value = UiState.Error(errMsg)
            } finally {
                _isLoading.value = false
            }
        }
    }

    // ── Helpers ────────────────────────────────────────────
    private fun appendMessage(convId: String, msg: ChatMessage) {
        _conversations.value = _conversations.value.map { conv ->
            if (conv.id == convId) {
                conv.copy(messages = (conv.messages + msg).toMutableList())
            } else conv
        }
    }

    private fun updateMessage(convId: String, msgId: String, content: String) {
        _conversations.value = _conversations.value.map { conv ->
            if (conv.id == convId) {
                val updated = conv.messages.map { msg ->
                    if (msg.id == msgId) msg.copy(content = content, isLoading = false)
                    else msg
                }.toMutableList()
                conv.copy(messages = updated)
            } else conv
        }
    }

    // ── Settings ───────────────────────────────────────────
    fun toggleDarkMode() = viewModelScope.launch {
        prefs.saveDarkMode(!darkMode.value)
    }

    fun toggleLanguage() = viewModelScope.launch {
        prefs.saveLanguage(if (language.value == "en") "ta" else "en")
    }

    fun updateLanguage(lang: String) = viewModelScope.launch {
        prefs.saveLanguage(lang)
    }

    fun updateApiKey(key: String) = viewModelScope.launch {
        prefs.saveApiKey(key)
    }

    fun logout() = viewModelScope.launch {
        prefs.clear()
    }

    fun resetSendState() { _sendState.value = null }
}
