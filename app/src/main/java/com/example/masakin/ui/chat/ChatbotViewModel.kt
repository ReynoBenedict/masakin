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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.UUID

// =======================
//  Model UI
// =======================

enum class Sender { User, Bot, Error }

data class UiMessage(
    val id: String,
    val sender: Sender,
    val text: String
)

data class UiConversation(
    val id: String,
    val title: String,
    val lastMessage: String,
    val updatedAt: Long
)

// =======================
//  ViewModel
// =======================

class ChatbotViewModel : ViewModel() {

    // Firebase
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    // Kecepatan animasi typing (ms per KATA)
    private val typingDelayMs = 80L

    // Daftar percakapan (untuk sidebar)
    private val _conversations = MutableStateFlow<List<UiConversation>>(emptyList())
    val conversations: StateFlow<List<UiConversation>> = _conversations

    // ID percakapan yang sedang aktif
    private val _currentConversationId = MutableStateFlow<String?>(null)
    val currentConversationId: StateFlow<String?> = _currentConversationId

    // Pesan dari percakapan aktif
    private val _messages = MutableStateFlow<List<UiMessage>>(emptyList())
    val messages: StateFlow<List<UiMessage>> = _messages

    // Loading indikator (3 dot)
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val defaultGreeting =
        "Halo, Saya Masakin-AI 👋 Saya asisten pribadi Anda. Ada yang bisa saya bantu?"

    init {
        // Saat ViewModel dibuat: pastikan minimal 1 conversation, lalu load list dan messages
        viewModelScope.launch(Dispatchers.IO) {
            initConversations()
        }
    }

    // =======================
    //  Init & helper Firestore
    // =======================

    private suspend fun initConversations() {
        val uid = auth.currentUser?.uid ?: return
        val userDoc = db.collection("users").document(uid)
        val convCol = userDoc.collection("conversations")

        // Ambil semua conversation (terbaru dulu)
        val snap = convCol
            .orderBy("updatedAtMillis", com.google.firebase.firestore.Query.Direction.DESCENDING)
            .get()
            .await()

        if (snap.isEmpty) {
            // Belum ada chat → buat 1 default
            val convId = createConversationInFirestore(convCol, "Chat baru", defaultGreeting)
            _currentConversationId.value = convId
            _messages.value = listOf(
                UiMessage(
                    id = "init-$convId",
                    sender = Sender.Bot,
                    text = defaultGreeting
                )
            )
            _conversations.value = listOf(
                UiConversation(
                    id = convId,
                    title = "Chat baru",
                    lastMessage = defaultGreeting,
                    updatedAt = System.currentTimeMillis()
                )
            )
        } else {
            val convs = snap.documents.map { doc ->
                UiConversation(
                    id = doc.id,
                    title = doc.getString("title") ?: "Chat",
                    lastMessage = doc.getString("lastMessage") ?: "",
                    updatedAt = doc.getLong("updatedAtMillis") ?: 0L
                )
            }
            _conversations.value = convs

            // pilih conversation pertama sebagai aktif
            val first = convs.first()
            _currentConversationId.value = first.id
            loadMessagesForConversation(first.id)
        }
    }

    /**
     * Create conversation di Firestore + 1 pesan greeting bot.
     * Return: conversationId
     */
    private suspend fun createConversationInFirestore(
        convCol: com.google.firebase.firestore.CollectionReference,
        title: String,
        greeting: String
    ): String {
        val now = System.currentTimeMillis()
        val convRef = convCol.document()
        convRef.set(
            mapOf(
                "title" to title,
                "lastMessage" to greeting,
                "createdAtMillis" to now,
                "updatedAtMillis" to now
            )
        ).await()

        convRef.collection("messages").add(
            mapOf(
                "sender" to "bot",
                "text" to greeting,
                "createdAtMillis" to now
            )
        ).await()

        return convRef.id
    }

    /**
     * Public: buat chat baru + langsung aktif + isi greeting di UI.
     */
    fun createNewConversation() {
        viewModelScope.launch(Dispatchers.IO) {
            val uid = auth.currentUser?.uid ?: return@launch
            val userDoc = db.collection("users").document(uid)
            val convCol = userDoc.collection("conversations")

            val title = "Chat baru"
            val now = System.currentTimeMillis()
            val convId = createConversationInFirestore(convCol, title, defaultGreeting)

            val newConv = UiConversation(
                id = convId,
                title = title,
                lastMessage = defaultGreeting,
                updatedAt = now
            )

            // Tambahkan ke list (di atas)
            _conversations.update { listOf(newConv) + it }
            _currentConversationId.value = convId
            _messages.value = listOf(
                UiMessage(
                    id = "init-$convId",
                    sender = Sender.Bot,
                    text = defaultGreeting
                )
            )
        }
    }

    /**
     * Memuat pesan untuk conversation tertentu dan menjadikannya aktif.
     */
    fun selectConversation(conversationId: String) {
        if (conversationId == _currentConversationId.value) return
        _currentConversationId.value = conversationId

        viewModelScope.launch(Dispatchers.IO) {
            loadMessagesForConversation(conversationId)
        }
    }

    private suspend fun loadMessagesForConversation(conversationId: String) {
        val uid = auth.currentUser?.uid ?: return
        val convRef = db.collection("users")
            .document(uid)
            .collection("conversations")
            .document(conversationId)

        try {
            val snap = convRef.collection("messages")
                .orderBy("createdAtMillis")
                .get()
                .await()

            if (snap.isEmpty) {
                _messages.value = emptyList()
            } else {
                val msgs = snap.documents.map { doc ->
                    val senderStr = doc.getString("sender") ?: "bot"
                    val sender = when (senderStr) {
                        "user" -> Sender.User
                        "bot" -> Sender.Bot
                        else -> Sender.Error
                    }
                    UiMessage(
                        id = doc.id,
                        sender = sender,
                        text = doc.getString("text") ?: ""
                    )
                }
                _messages.value = msgs
            }
        } catch (_: Exception) {
            // optional log
        }
    }

    /**
     * Simpan pesan ke Firestore + update metadata conversation.
     */
    private fun saveMessageToFirestore(conversationId: String, msg: UiMessage) {
        val uid = auth.currentUser?.uid ?: return
        val userDoc = db.collection("users").document(uid)
        val convRef = userDoc.collection("conversations").document(conversationId)

        val senderString = when (msg.sender) {
            Sender.User -> "user"
            Sender.Bot -> "bot"
            Sender.Error -> "error"
        }
        val now = System.currentTimeMillis()

        val msgData = mapOf(
            "sender" to senderString,
            "text" to msg.text,
            "createdAtMillis" to now
        )

        convRef.collection("messages")
            .add(msgData)
            .addOnSuccessListener {
                val convUpdate = mapOf(
                    "lastMessage" to msg.text,
                    "updatedAtMillis" to now
                )
                convRef.update(convUpdate)

                // update local conversations list (lastMessage + urutan)
                _conversations.update { list ->
                    val mutable = list.toMutableList()
                    val idx = mutable.indexOfFirst { it.id == conversationId }
                    if (idx != -1) {
                        val old = mutable[idx]
                        val updated = old.copy(lastMessage = msg.text, updatedAt = now)
                        mutable.removeAt(idx)
                        mutable.add(0, updated) // pindah ke paling atas
                    }
                    mutable
                }
            }
    }
    private fun generateTitleFrom(text: String): String {
        // buang markdown simpel
        val noMd = text
            .replace(Regex("[*#`_]"), "")
            .trim()

        val firstLine = noMd.lineSequence().firstOrNull { it.isNotBlank() } ?: "Chat"

        return if (firstLine.length > 30) {
            firstLine.take(30) + "…"
        } else {
            firstLine
        }
    }

    /**
     * Kalau title masih "Chat baru" / kosong, update title pakai pesan user pertama.
     */
    private fun maybeUpdateConversationTitleFromUserText(conversationId: String, userText: String) {
        val uid = auth.currentUser?.uid ?: return
        val newTitle = generateTitleFrom(userText)

        _conversations.update { list ->
            val mutable = list.toMutableList()
            val idx = mutable.indexOfFirst { it.id == conversationId }
            if (idx != -1) {
                val old = mutable[idx]
                if (old.title.isBlank() || old.title == "Chat baru") {
                    val updated = old.copy(title = newTitle)
                    mutable[idx] = updated

                    // update di Firestore (async)
                    db.collection("users")
                        .document(uid)
                        .collection("conversations")
                        .document(conversationId)
                        .update("title", newTitle)
                }
            }
            mutable
        }
    }

    // =======================
    //  OpenAI + send()
    // =======================

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

        val convId = _currentConversationId.value ?: return

        // 1) Tambah pesan user ke UI
        val userMsg = UiMessage(
            id = "u-${System.nanoTime()}",
            sender = Sender.User,
            text = trimmed
        )
        _messages.update { current -> current + userMsg }

        // 2) Simpan ke Firestore
        saveMessageToFirestore(convId, userMsg)

        // 2b) JIKA perlu, update title dari pesan user pertama
        maybeUpdateConversationTitleFromUserText(convId, trimmed)

        // 2) Call OpenAI di background
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _isLoading.value = true

                val reply = OpenAIClient.chat(
                    apiKey = apiKey,
                    messages = buildApiMessages(trimmed)
                )

                _isLoading.value = false
                val finalText = reply.ifBlank { "Siap! Ada yang lain?" }

                // 3) Tambahkan pesan bot kosong dulu untuk animasi typing
                val botId = "b-${System.nanoTime()}"
                _messages.update { current ->
                    current + UiMessage(
                        id = botId,
                        sender = Sender.Bot,
                        text = ""
                    )
                }

                // 4) Animasi typing per KATA
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

                // 5) Simpan versi final ke Firestore
                val finalBotMsg = UiMessage(
                    id = botId,
                    sender = Sender.Bot,
                    text = finalText
                )
                saveMessageToFirestore(convId, finalBotMsg)

            } catch (e: Exception) {
                _isLoading.value = false

                val errMsg = UiMessage(
                    id = "e-${System.nanoTime()}",
                    sender = Sender.Error,
                    text = "Gagal menghubungi MASAKIN-AI (${e.message ?: "unknown"}). Coba lagi."
                )

                _messages.update { current -> current + errMsg }
                saveMessageToFirestore(convId, errMsg)
            }
        }
    }
}

// =======================
//  Markdown Parser MASAKIN
// =======================

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
            line.startsWith("#### ") -> appendHeader(line.removePrefix("#### "), 4)
            line.startsWith("### ") -> appendHeader(line.removePrefix("### "), 3)
            line.startsWith("## ") -> appendHeader(line.removePrefix("## "), 2)
            line.startsWith("# ") -> appendHeader(line.removePrefix("# "), 1)
            line.startsWith("- ") -> {
                append("• ")
                appendInlineWithBold(line.removePrefix("- "))
            }
            else -> appendInlineWithBold(line)
        }

        if (index != lines.lastIndex) append("\n")
    }
}