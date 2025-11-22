package com.example.masakin.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.AttachFile
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.masakin.R
import com.example.masakin.data.DummyData
import java.text.SimpleDateFormat
import java.util.*

private val MasakinRed = Color(0xFFD32F2F)
private val ChatBubbleUser = Color(0xFFEF5350)
private val ChatBubbleDoctor = Color(0xFFFFFFFF)
// Warna background solid sedikit diturunkan agar pattern terlihat lebih jelas,
// atau bisa tetap pakai ChatBgColor jika patternnya sudah transparan.
private val ChatBgColor = Color(0xFFF5F5F5)

data class ChatMessage(
    val id: String,
    val text: String,
    val isUser: Boolean,
    val timestamp: String
)

@Composable
fun ChatScreen2(
    consultantId: String = "1",
    onBackToHome: () -> Unit = {}
) {
    val consultant = DummyData.consultants.find { it.id == consultantId } ?: DummyData.consultants.first()
    var messageText by remember { mutableStateOf("") }

    // Dummy Initial Chat Data sesuai gambar
    val initialMessages = listOf(
        ChatMessage("1", "Halo, Richard. Perkenalkan saya Dokter Trini. Untuk masalah tersebut tidak boleh ya.. karena dapat mengganggu pencernaan kalian yee...", false, "20:10"),
        ChatMessage("2", "Oh begitu, baik terimakasih dok!", true, "20:11")
    )

    var messages by remember { mutableStateOf(initialMessages) }

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp)
                    .background(MasakinRed)
                    .padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBackToHome) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                }

                Spacer(modifier = Modifier.width(4.dp))

                Image(
                    painter = painterResource(id = consultant.favImageRes),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                )

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = consultant.name,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                    Text(
                        text = "Online",
                        color = Color(0xFFCCFF90),
                        fontSize = 12.sp
                    )
                }

                IconButton(onClick = { }) {
                    Icon(Icons.Default.MoreVert, contentDescription = "Menu", tint = Color.White)
                }
            }
        },
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MasakinRed)
                    .padding(horizontal = 8.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Voice Note Icon (Non-functional)
                IconButton(onClick = { }, enabled = false) {
                    Icon(Icons.Default.Mic, contentDescription = null, tint = Color.White)
                }

                // Attachment Icon (Non-functional)
                IconButton(onClick = { }, enabled = false) {
                    Icon(Icons.Default.AttachFile, contentDescription = null, tint = Color.White)
                }

                // Text Field
                TextField(
                    value = messageText,
                    onValueChange = { messageText = it },
                    textStyle = LocalTextStyle.current.copy(fontSize = 14.sp),
                    placeholder = { Text("Message", fontSize = 14.sp) },
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp),
                    shape = RoundedCornerShape(25.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    singleLine = true
                )

                // Send Button
                IconButton(
                    onClick = {
                        if (messageText.isNotBlank()) {
                            val newMessage = ChatMessage(
                                id = UUID.randomUUID().toString(),
                                text = messageText,
                                isUser = true,
                                timestamp = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())
                            )
                            messages = messages + newMessage
                            messageText = ""
                        }
                    }
                ) {
                    Icon(Icons.AutoMirrored.Filled.Send, contentDescription = "Send", tint = Color.White)
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(ChatBgColor) // Warna dasar background
        ) {
            // --- BAGIAN INI TELAH DI-UNCOMMENT ---
            // Pastikan file chat_pattern.png ada di res/drawable
            Image(
                painter = painterResource(id = R.drawable.chat_pattern),
                contentDescription = null,
                contentScale = ContentScale.Crop, // Atur agar memenuhi layar (tile/crop)
                modifier = Modifier.fillMaxSize(),
                alpha = 0.1f // Transparansi agar tidak terlalu dominan (sesuaikan jika perlu)
            )
            // -------------------------------------

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                reverseLayout = false,
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(top = 16.dp, bottom = 16.dp)
            ) {
                item {
                    DateHeader("Sab, 03/03")
                }

                items(messages) { msg ->
                    ChatBubble(message = msg)
                }
            }
        }
    }
}

@Composable
fun DateHeader(date: String) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Surface(
            color = MasakinRed,
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.padding(vertical = 16.dp)
        ) {
            Text(
                text = date,
                color = Color.White,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
            )
        }
    }
}

@Composable
fun ChatBubble(message: ChatMessage) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = if (message.isUser) Alignment.End else Alignment.Start
    ) {
        Surface(
            color = if (message.isUser) ChatBubbleUser else ChatBubbleDoctor,
            shape = if (message.isUser) {
                RoundedCornerShape(topStart = 16.dp, topEnd = 0.dp, bottomStart = 16.dp, bottomEnd = 16.dp)
            } else {
                RoundedCornerShape(topStart = 0.dp, topEnd = 16.dp, bottomStart = 16.dp, bottomEnd = 16.dp)
            },
            shadowElevation = 2.dp,
            modifier = Modifier.widthIn(max = 280.dp)
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Text(
                    text = message.text,
                    color = if (message.isUser) Color.White else Color.Black,
                    fontSize = 14.sp,
                    lineHeight = 20.sp
                )
                Text(
                    text = message.timestamp,
                    color = if (message.isUser) Color.White.copy(alpha = 0.7f) else Color.Gray,
                    fontSize = 10.sp,
                    modifier = Modifier.align(Alignment.End)
                )
            }
        }
    }
}