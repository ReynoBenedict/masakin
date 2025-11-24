package com.example.masakin.ui.screens

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MicNone
import androidx.compose.material.icons.rounded.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.masakin.ui.chat.ChatbotViewModel
import com.example.masakin.ui.chat.Sender
import com.example.masakin.ui.chat.UiConversation
import com.example.masakin.ui.chat.UiMessage
import com.example.masakin.ui.chat.masakinMarkdown
import com.example.masakin.ui.theme.Black
import com.example.masakin.ui.theme.Red50
import kotlinx.coroutines.launch

// DEV ONLY
private const val API_KEY =
    "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"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatbotScreen(onBack: () -> Unit = {}) {
    val vm: ChatbotViewModel = viewModel()
    val messages by vm.messages.collectAsState()
    val isLoading by vm.isLoading.collectAsState()
    val conversations by vm.conversations.collectAsState()
    val currentConvId by vm.currentConversationId.collectAsState()

    var input by remember { mutableStateOf("") }

    val listState = rememberLazyListState()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    // Auto-scroll ke bawah saat pesan baru atau loading berubah
    LaunchedEffect(messages.size, isLoading) {
        if (messages.isNotEmpty()) {
            listState.animateScrollToItem(messages.lastIndex)
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(modifier = Modifier.width(280.dp)) {
                Text(
                    "Percakapan",
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.titleMedium
                )

                Button(
                    onClick = { vm.createNewConversation() },
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Red50,
                        contentColor = Color.White
                    )
                ) {
                    Text("Chat baru")
                }

                Spacer(Modifier.height(8.dp))
                Divider()
                Spacer(Modifier.height(8.dp))

                LazyColumn(
                    modifier = Modifier.fillMaxHeight()
                ) {
                    items(conversations, key = { it.id }) { conv ->
                        ConversationItem(
                            conversation = conv,
                            selected = conv.id == currentConvId,
                            onClick = {
                                vm.selectConversation(conv.id)
                                scope.launch { drawerState.close() }
                            }
                        )
                    }
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = { Text("ChatBot", color = Red50, fontSize = 22.sp) },
                    navigationIcon = {
                        Row {
                            IconButton(
                                onClick = { scope.launch { drawerState.open() } }
                            ) {
                                Icon(
                                    Icons.Filled.Menu,
                                    contentDescription = "Menu"
                                )
                            }
                            IconButton(onClick = onBack) {
                                Icon(
                                    Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = "Back"
                                )
                            }
                        }
                    }
                )
            },
            bottomBar = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .imePadding()
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
                        textStyle = TextStyle(fontSize = 12.sp),
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            cursorColor = Red50
                        )
                    )
                    IconButton(onClick = { }) {
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
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(inner)
                    .padding(horizontal = 16.dp),
                contentPadding = PaddingValues(top = 16.dp, bottom = 16.dp)
            ) {
                items(messages, key = { it.id }) { m ->
                    if (m.sender == Sender.User) {
                        UserRow(m)
                    } else {
                        BotRow(m)
                    }
                    Spacer(Modifier.height(10.dp))
                }

                if (isLoading) {
                    item {
                        TypingIndicatorRow()
                        Spacer(Modifier.height(10.dp))
                    }
                }
            }
        }
    }
}

// =======================
//  Drawer item (conversation)
// =======================

@Composable
private fun ConversationItem(
    conversation: UiConversation,
    selected: Boolean,
    onClick: () -> Unit
) {
    val bg = if (selected) Red50.copy(alpha = 0.1f) else Color.Transparent

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .background(bg)
            .padding(horizontal = 16.dp, vertical = 10.dp)
    ) {
        Text(
            conversation.title,
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Black
        )
        if (conversation.lastMessage.isNotBlank()) {
            Text(
                conversation.lastMessage,
                style = MaterialTheme.typography.bodySmall,
                maxLines = 1,
                color = Color.DarkGray
            )
        }
    }
}

// =======================
//  Chat bubbles & typing
// =======================

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
                text = masakinMarkdown(m.text),
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

/**
 * 3-dot typing indicator
 */
@Composable
private fun TypingIndicatorRow() {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(28.dp)
                .clip(CircleShape)
                .background(Red50.copy(alpha = 0.15f)),
            contentAlignment = Alignment.Center
        ) {
            Text("🤖", fontSize = 12.sp)
        }

        Spacer(Modifier.width(8.dp))

        Surface(
            color = Color(0xFFF6F6F8),
            shape = RoundedCornerShape(14.dp)
        ) {
            Row(
                modifier = Modifier
                    .padding(horizontal = 14.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                val transition = rememberInfiniteTransition(label = "typingDots")
                repeat(3) { index ->
                    val alpha = transition.animateFloat(
                        initialValue = 0.3f,
                        targetValue = 1f,
                        animationSpec = infiniteRepeatable(
                            animation = tween(durationMillis = 600, delayMillis = index * 150),
                            repeatMode = RepeatMode.Reverse
                        ),
                        label = "dot$index"
                    ).value

                    Box(
                        modifier = Modifier
                            .size(6.dp)
                            .clip(CircleShape)
                            .background(Color.Gray)
                            .alpha(alpha)
                    )
                }
            }
        }
    }
}