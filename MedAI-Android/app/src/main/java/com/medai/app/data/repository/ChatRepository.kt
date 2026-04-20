package com.medai.app.data.repository

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.util.Base64
import com.google.gson.Gson
import com.medai.app.data.api.GroqApiService
import com.medai.app.data.model.*
import com.medai.app.utils.Constants
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.ByteArrayOutputStream
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChatRepository @Inject constructor(
    private val api: GroqApiService,
    @ApplicationContext private val context: Context,
    private val gson: Gson
) {
    // ── Validate Groq API key ──────────────────────────────
    suspend fun validateApiKey(key: String): Result<Boolean> {
        return try {
            val res = api.validateKey("Bearer $key")
            when {
                res.isSuccessful -> Result.success(true)
                res.code() == 401 -> Result.failure(Exception("Invalid API key"))
                else -> Result.success(true) // other errors = key OK, service issue
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // ── Send chat message (streaming via chunked read) ─────
    fun sendMessage(
        apiKey: String,
        history: List<ChatMessage>,
        imageUri: Uri? = null
    ): Flow<String> = flow {
        val messages = mutableListOf<GroqMessage>()
        // System prompt
        messages.add(GroqMessage("system", Constants.SYSTEM_PROMPT))

        // History (text only for history, vision only on last message)
        for (i in 0 until history.size - 1) {
            val m = history[i]
            messages.add(GroqMessage(m.role, m.content))
        }

        // Last user message — may include image
        val lastMsg = history.last()
        if (imageUri != null) {
            val base64 = uriToBase64(imageUri)
            val mimeType = context.contentResolver.getType(imageUri) ?: "image/jpeg"
            val parts = listOf(
                ContentPart("image_url", image_url = ImageUrl("data:$mimeType;base64,$base64")),
                ContentPart("text", text = lastMsg.content)
            )
            messages.add(GroqMessage("user", parts))
        } else {
            messages.add(GroqMessage(lastMsg.role, lastMsg.content))
        }

        val model = if (imageUri != null) Constants.GROQ_VISION else Constants.GROQ_MODEL
        val request = GroqRequest(model = model, messages = messages, stream = true)

        val response = api.chatStream("Bearer $apiKey", request)

        if (!response.isSuccessful) {
            val code = response.code()
            if (code == 401) throw Exception("INVALID_KEY")
            throw Exception("API error $code")
        }

        val body = response.body() ?: throw Exception("Empty response")
        val source = body.byteStream().bufferedReader()
        val accumulated = StringBuilder()

        source.forEachLine { line ->
            val trimmed = line.trim()
            if (!trimmed.startsWith("data:")) return@forEachLine
            val data = trimmed.removePrefix("data:").trim()
            if (data == "[DONE]" || data.isEmpty()) return@forEachLine
            try {
                val parsed = gson.fromJson(data, GroqStreamChunk::class.java)
                val text = parsed?.choices?.firstOrNull()?.delta?.content ?: return@forEachLine
                accumulated.append(text)
                // Emit after each SSE chunk
            } catch (_: Exception) {}
        }

        emit(accumulated.toString())
    }.flowOn(Dispatchers.IO)

    // ── Non-streaming fallback ─────────────────────────────
    suspend fun sendMessageSync(
        apiKey: String,
        history: List<ChatMessage>
    ): String {
        val messages = mutableListOf<GroqMessage>()
        messages.add(GroqMessage("system", Constants.SYSTEM_PROMPT))
        history.forEach { messages.add(GroqMessage(it.role, it.content)) }

        val request = GroqRequest(
            model = Constants.GROQ_MODEL,
            messages = messages,
            stream = false
        )
        val response = api.chat("Bearer $apiKey", request)
        if (!response.isSuccessful) {
            if (response.code() == 401) throw Exception("INVALID_KEY")
            throw Exception("API error ${response.code()}")
        }
        return response.body()?.choices?.firstOrNull()?.message?.content?.toString() ?: ""
    }

    private fun uriToBase64(uri: Uri): String {
        val bitmap = android.provider.MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
        val resized = Bitmap.createScaledBitmap(bitmap, 800, 800, true)
        val out = ByteArrayOutputStream()
        resized.compress(Bitmap.CompressFormat.JPEG, 80, out)
        return Base64.encodeToString(out.toByteArray(), Base64.NO_WRAP)
    }
}

// Streaming chunk model
data class GroqStreamChunk(
    val choices: List<StreamChoice>?
)
data class StreamChoice(val delta: StreamDelta?)
data class StreamDelta(val content: String?)
