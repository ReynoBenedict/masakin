package com.example.masakin.data.ai

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import java.util.concurrent.TimeUnit

object OpenAIClient {
    private val json = "application/json; charset=utf-8".toMediaType()

    private val http = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .build()

    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    data class ChatMessage(val role: String, val content: String)
    data class ChatRequest(
        val model: String,
        val messages: List<ChatMessage>,
        val temperature: Double = 0.7
    )
    data class Choice(val message: ChatMessage)
    data class ChatResponse(val choices: List<Choice> = emptyList())

    private val reqAdapter = moshi.adapter(ChatRequest::class.java)
    private val respAdapter = moshi.adapter(ChatResponse::class.java)

    @Throws(Exception::class)
    fun chat(apiKey: String, messages: List<ChatMessage>): String {
        val bodyStr = reqAdapter.toJson(
            ChatRequest(model = "gpt-4o-mini", messages = messages)
        )
        val req = Request.Builder()
            .url("https://api.openai.com/v1/chat/completions")
            .addHeader("Authorization", "Bearer $apiKey")
            .addHeader("Content-Type", "application/json")
            .addHeader("Accept", "application/json")
            .post(bodyStr.toRequestBody(json))
            .build()

        http.newCall(req).execute().use { resp ->
            val raw = resp.body?.string().orEmpty()
            if (!resp.isSuccessful) {
                // Kirimkan kode + body supaya kelihatan jelas di UI
                throw IllegalStateException("HTTP ${resp.code}: ${raw.take(180)}")
            }
            val parsed = respAdapter.fromJson(raw)
            return parsed?.choices?.firstOrNull()?.message?.content?.trim()
                ?: throw IllegalStateException("Empty response")
        }
    }
}
