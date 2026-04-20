package com.medai.app.data.api

import com.medai.app.data.model.GroqRequest
import com.medai.app.data.model.GroqResponse
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface GroqApiService {

    @POST("openai/v1/chat/completions")
    suspend fun chat(
        @Header("Authorization") authHeader: String,
        @Body request: GroqRequest
    ): Response<GroqResponse>

    @Streaming
    @POST("openai/v1/chat/completions")
    suspend fun chatStream(
        @Header("Authorization") authHeader: String,
        @Body request: GroqRequest
    ): Response<ResponseBody>

    @GET("openai/v1/models")
    suspend fun validateKey(
        @Header("Authorization") authHeader: String
    ): Response<ResponseBody>
}
