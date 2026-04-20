package com.medai.app.data.model

import java.util.UUID

// ── Chat Message ───────────────────────────────────────────
data class ChatMessage(
    val id: String = UUID.randomUUID().toString(),
    val role: String,          // "user" or "assistant"
    val content: String,
    val imageUri: String? = null,
    val timestamp: Long = System.currentTimeMillis(),
    val isLoading: Boolean = false
)

// ── Conversation ───────────────────────────────────────────
data class Conversation(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val messages: MutableList<ChatMessage> = mutableListOf(),
    val timestamp: Long = System.currentTimeMillis()
)

// ── Groq API models ────────────────────────────────────────
data class GroqRequest(
    val model: String,
    val messages: List<GroqMessage>,
    val max_tokens: Int = 1024,
    val temperature: Double = 0.7,
    val stream: Boolean = false
)

data class GroqMessage(
    val role: String,
    val content: Any  // String or List<ContentPart> for vision
)

data class ContentPart(
    val type: String,            // "text" or "image_url"
    val text: String? = null,
    val image_url: ImageUrl? = null
)

data class ImageUrl(val url: String)

data class GroqResponse(
    val choices: List<Choice>
)

data class Choice(
    val message: GroqMessage?,
    val delta: Delta?
)

data class Delta(val content: String?)

// ── UI state ───────────────────────────────────────────────
sealed class UiState<out T> {
    object Loading : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Error(val message: String) : UiState<Nothing>()
}

// ── Suggestion ─────────────────────────────────────────────
data class Suggestion(
    val icon: String,
    val text: String,
    val subtitle: String
)
