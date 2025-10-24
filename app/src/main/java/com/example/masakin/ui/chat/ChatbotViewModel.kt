package com.example.masakin.ui.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.masakin.data.ai.MasakinSystemPrompt
import com.example.masakin.data.ai.OpenAIClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.UUID

enum class Sender { User, Bot, Error }
data class UiMessage(val id: String, val sender: Sender, val text: String)

class ChatbotViewModel : ViewModel() {

    private val _messages = MutableStateFlow<List<UiMessage>>(
        listOf(
            UiMessage(
                UUID.randomUUID().toString(),
                Sender.Bot,
                "Halo, Saya Masakin-AI 👋 Saya asisten pribadi Anda. Ada yang bisa saya bantu?"
            )
        )
    )
    val messages: StateFlow<List<UiMessage>> = _messages

    private fun buildApiMessages(userText: String): List<OpenAIClient.ChatMessage> {
        val history = _messages.value.map {
            val role = when (it.sender) {
                Sender.User -> "user"
                Sender.Bot, Sender.Error -> "assistant"
            }
            OpenAIClient.ChatMessage(role, it.text)
        }
        return listOf(OpenAIClient.ChatMessage("system", MasakinSystemPrompt.content)) +
                history + OpenAIClient.ChatMessage("user", userText)
    }

    fun send(userText: String, apiKey: String) {
        if (userText.isBlank()) return

        _messages.value = _messages.value + UiMessage(
            id = "u-${System.nanoTime()}",
            sender = Sender.User,
            text = userText.trim()
        )

        // 🔧 PENTING: pakai IO dispatcher supaya tidak NetworkOnMainThreadException
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val reply = OpenAIClient.chat(
                    apiKey = apiKey,
                    messages = buildApiMessages(userText)
                )
                _messages.value = _messages.value + UiMessage(
                    id = "b-${System.nanoTime()}",
                    sender = Sender.Bot,
                    text = reply.ifBlank { "Siap! Ada yang lain?" }
                )
            } catch (e: Exception) {
                _messages.value = _messages.value + UiMessage(
                    id = "e-${System.nanoTime()}",
                    sender = Sender.Error,
                    text = "Gagal menghubungi MASAKIN-AI (${e.message ?: "unknown"}). Coba lagi."
                )
            }
        }
    }
}
