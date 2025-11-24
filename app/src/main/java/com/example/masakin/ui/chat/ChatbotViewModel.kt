package com.example.masakin.ui.chat

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.masakin.data.ai.MasakinSystemPrompt
import com.example.masakin.data.ai.OpenAIClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID

enum class Sender { User, Bot, Error }

data class UiMessage(
    val id: String,
    val sender: Sender,
    val text: String
)

class ChatbotViewModel : ViewModel() {

    // delay per WORD
    private val typingDelayMs = 80L

    private val _messages = MutableStateFlow(
        listOf(
            UiMessage(
                id = UUID.randomUUID().toString(),
                sender = Sender.Bot,
                text = "Halo, Saya Masakin-AI 👋 Saya asisten pribadi Anda. Ada yang bisa saya bantu?"
            )
        )
    )
    val messages: StateFlow<List<UiMessage>> = _messages

    // loading = masih nunggu response dari API (belum mulai animasi typing)
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private fun buildApiMessages(userText: String): List<OpenAIClient.ChatMessage> {
        val history = _messages.value.map { msg ->
            val role = when (msg.sender) {
                Sender.User -> "user"
                Sender.Bot, Sender.Error -> "assistant"
            }
            OpenAIClient.ChatMessage(role = role, content = msg.text)
        }

        return listOf(
            OpenAIClient.ChatMessage(
                role = "system",
                content = MasakinSystemPrompt.content
            )
        ) + history + OpenAIClient.ChatMessage(
            role = "user",
            content = userText
        )
    }

    fun send(userText: String, apiKey: String) {
        val trimmed = userText.trim()
        if (trimmed.isBlank()) return

        _messages.update { current ->
            current + UiMessage(
                id = "u-${System.nanoTime()}",
                sender = Sender.User,
                text = trimmed
            )
        }

        viewModelScope.launch(Dispatchers.IO) {
            try {
                _isLoading.value = true
                val reply = OpenAIClient.chat(
                    apiKey = apiKey,
                    messages = buildApiMessages(trimmed)
                )
                _isLoading.value = false

                val finalText = reply.ifBlank { "Siap! Ada yang lain?" }

                val botId = "b-${System.nanoTime()}"
                _messages.update { current ->
                    current + UiMessage(
                        id = botId,
                        sender = Sender.Bot,
                        text = "" // mulai kosong untuk animasi typing
                    )
                }

                // word-by-word typing
                val words = finalText.split(" ")
                var partial = ""
                for ((index, word) in words.withIndex()) {
                    partial = if (index == 0) word else "$partial $word"

                    _messages.update { list ->
                        list.map { msg ->
                            if (msg.id == botId) msg.copy(text = partial) else msg
                        }
                    }
                    delay(typingDelayMs)
                }
            } catch (e: Exception) {
                _isLoading.value = false
                _messages.update { current ->
                    current + UiMessage(
                        id = "e-${System.nanoTime()}",
                        sender = Sender.Error,
                        text = "Gagal menghubungi MASAKIN-AI (${e.message ?: "unknown"}). Coba lagi."
                    )
                }
            }
        }
    }
}

/* ======================= */
/*  Markdown Parser MASAKIN */
/* ======================= */

fun masakinMarkdown(text: String): AnnotatedString = buildAnnotatedString {
    val lines = text.lines()

    fun AnnotatedString.Builder.appendInlineWithBold(str: String) {
        var i = 0
        while (i < str.length) {
            val start = str.indexOf("**", startIndex = i)
            if (start == -1) {
                append(str.substring(i))
                break
            }
            if (start > i) append(str.substring(i, start))

            val end = str.indexOf("**", startIndex = start + 2)
            if (end == -1) {
                append(str.substring(start))
                break
            }

            pushStyle(SpanStyle(fontWeight = FontWeight.Bold))
            append(str.substring(start + 2, end))
            pop()

            i = end + 2
        }
    }

    fun AnnotatedString.Builder.appendHeader(text: String, level: Int) {
        val size = when (level) {
            1 -> 20.sp
            2 -> 18.sp
            3 -> 16.sp
            else -> 15.sp
        }
        pushStyle(SpanStyle(fontWeight = FontWeight.Bold, fontSize = size))
        appendInlineWithBold(text)
        pop()
    }

    lines.forEachIndexed { index, raw ->
        val line = raw.trimEnd()

        if (line.isBlank()) {
            if (index != lines.lastIndex) append("\n")
            return@forEachIndexed
        }

        when {
            line.startsWith("#### ") -> {
                appendHeader(line.removePrefix("#### "), 4)
            }
            line.startsWith("### ") -> {
                appendHeader(line.removePrefix("### "), 3)
            }
            line.startsWith("## ") -> {
                appendHeader(line.removePrefix("## "), 2)
            }
            line.startsWith("# ") -> {
                appendHeader(line.removePrefix("# "), 1)
            }
            line.startsWith("- ") -> {
                append("• ")
                appendInlineWithBold(line.removePrefix("- "))
            }
            else -> {
                appendInlineWithBold(line)
            }
        }

        if (index != lines.lastIndex) append("\n")
    }
}