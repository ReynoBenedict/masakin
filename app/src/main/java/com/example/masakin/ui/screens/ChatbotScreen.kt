package com.example.masakin.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MicNone
import androidx.compose.material.icons.rounded.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.masakin.ui.chat.ChatbotViewModel
import com.example.masakin.ui.chat.Sender
import com.example.masakin.ui.chat.UiMessage
import com.example.masakin.ui.theme.Black
import com.example.masakin.ui.theme.Red50

// ====== HARDCODED API KEY (dev only) ======
private const val API_KEY =
    "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"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatbotScreen(onBack: () -> Unit = {}) {
    val vm: ChatbotViewModel = viewModel()
    val messages by vm.messages.collectAsState()
    var input by remember { mutableStateOf("") }

    // state untuk list + auto-scroll ke bawah saat ada pesan baru
    val listState = rememberLazyListState()
    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) listState.animateScrollToItem(messages.lastIndex)
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("ChatBot", color = Red50, fontSize = 22.sp) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        // ⬇️ Input bar sticky di bawah & ikut naik saat keyboard tampil
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .imePadding()                 // naik mengikuti keyboard
                    .navigationBarsPadding()
                    .padding(20.dp)
                    .height(56.dp)
                    .clip(RoundedCornerShape(28.dp))
                    .background(Color.White),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    value = input,
                    onValueChange = { input = it },
                    placeholder = { Text("Masukan Text", fontSize = 12.sp, color = Black) },
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 16.dp),
                    textStyle = LocalTextStyle.current.copy(fontSize = 12.sp),
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        cursorColor = Red50
                    )
                )
                IconButton(onClick = { /* future: voice */ }) {
                    Icon(Icons.Filled.MicNone, contentDescription = "Mic", tint = Color.Gray)
                }
                FilledIconButton(
                    onClick = {
                        val text = input.trim()
                        if (text.isNotEmpty()) {
                            vm.send(text, API_KEY)
                            input = ""
                        }
                    },
                    colors = IconButtonDefaults.filledIconButtonColors(containerColor = Red50),
                    modifier = Modifier.padding(end = 8.dp)
                ) {
                    Icon(Icons.Rounded.Send, contentDescription = "Kirim", tint = Color.White)
                }
            }
        }
    ) { inner ->
        // Konten utama TIDAK diberi imePadding -> header & chat tetap sticky di atas
        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .padding(inner)
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(top = 16.dp, bottom = 16.dp)
        ) {
            items(messages, key = { it.id }) { m ->
                when (m.sender) {
                    Sender.Bot, Sender.Error -> BotRow(m)
                    Sender.User -> UserRow(m)
                }
                Spacer(Modifier.height(10.dp))
            }
        }
    }
}

@Composable
private fun BotRow(m: UiMessage) {
    Row(verticalAlignment = Alignment.Top) {
        Box(
            modifier = Modifier
                .size(28.dp)
                .clip(CircleShape)
                .background(Red50.copy(alpha = 0.15f)),
            contentAlignment = Alignment.Center
        ) { Text(if (m.sender == Sender.Error) "⚠️" else "🤖", fontSize = 12.sp) }
        Spacer(Modifier.width(8.dp))
        Surface(color = Color(0xFFF6F6F8), shape = RoundedCornerShape(14.dp)) {
            Text(
                m.text,
                modifier = Modifier.padding(14.dp),
                fontSize = 14.sp,
                color = Color.Black.copy(alpha = 0.9f)
            )
        }
    }
}

@Composable
private fun UserRow(m: UiMessage) {
    Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
        Surface(color = Red50.copy(alpha = 0.10f), shape = RoundedCornerShape(14.dp)) {
            Text(
                m.text,
                modifier = Modifier.padding(14.dp),
                fontSize = 14.sp,
                color = Color.Black.copy(alpha = 0.9f)
            )
        }
    }
}
