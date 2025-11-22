package com.example.masakin.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Tune
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.masakin.R
import com.example.masakin.ui.components.BottomNavBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatListScreen(
    onNavigateToHome: () -> Unit,
    onNavigateToMyFood: () -> Unit,
    onNavigateToProfile: () -> Unit,
    onNavigateToChatDetail: (String) -> Unit
) {
    // State untuk BottomNavBar agar tab "Chat" aktif
    var selectedTab by remember { mutableStateOf("chat") }

    // Handle navigasi BottomNavBar
    LaunchedEffect(selectedTab) {
        when (selectedTab) {
            "home" -> onNavigateToHome()
            "food" -> onNavigateToMyFood()
            "profile" -> onNavigateToProfile()
            // "chat" -> diam di sini
        }
    }

    // Data Dummy Chat List
    val chatList = listOf(
        ChatItemData(
            id = "1", // ID Dokter Trini
            name = "Dokter Trini", // Sesuai permintaan
            message = "Halo, Richard. Perkenalkan saya...",
            time = "18.00",
            unreadCount = 2,
            avatarRes = R.drawable.topconsult1, // Pastikan ada gambar ini
            isDrTrini = true
        ),
        ChatItemData(
            id = "2",
            name = "Dapur Mamachi",
            message = "Halo barang ready nih kak!",
            time = "18.00",
            unreadCount = 2,
            avatarRes = R.drawable.consult2 // Ganti icon placeholder jika perlu
        ),
        ChatItemData(
            id = "3",
            name = "Selvi's Mart",
            message = "Baik segera saya pesan!",
            time = "18.00",
            unreadCount = 0,
            avatarRes = R.drawable.mart_button
        ),
        ChatItemData(
            id = "4",
            name = "TDI Meat",
            message = "Halo barang ready nih kak!",
            time = "18.00",
            unreadCount = 0,
            avatarRes = R.drawable.consult_button
        ),
        ChatItemData(
            id = "5",
            name = "Mister Mart",
            message = "Halo barang ready nih kak!",
            time = "18.00",
            unreadCount = 0,
            avatarRes = R.drawable.mart_button
        )
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Chat",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(imageVector = Icons.Default.Search, contentDescription = "Search", tint = Color.Gray)
                    }
                    IconButton(onClick = {}) {
                        Icon(imageVector = Icons.Outlined.Tune, contentDescription = "Filter", tint = Color.Gray)
                    }
                    IconButton(onClick = {}) {
                        Icon(imageVector = Icons.Default.Settings, contentDescription = "Settings", tint = Color.Gray)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                )
            )
        },
        bottomBar = {
            BottomNavBar(
                selectedItem = selectedTab,
                onItemSelected = { selectedTab = it }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color.White)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(chatList) { chat ->
                    ChatItem(
                        data = chat,
                        onClick = {
                            // Jika Dr. Trini diklik, navigasi ke ChatScreen2
                            // Anda juga bisa menghapus if(chat.isDrTrini) jika semua chat ingin bisa diklik
                            // namun untuk demo ini kita fokus ke Dr Trini.
                            if (chat.isDrTrini) {
                                onNavigateToChatDetail(chat.id)
                            } else {
                                // Default behavior atau navigasi ke chat umum lain
                            }
                        }
                    )
                    Divider(color = Color.LightGray.copy(alpha = 0.3f), thickness = 1.dp)
                }
            }
        }
    }
}

// Data Class & Components Helper
data class ChatItemData(
    val id: String,
    val name: String,
    val message: String,
    val time: String,
    val unreadCount: Int,
    val avatarRes: Int,
    val isDrTrini: Boolean = false
)

@Composable
fun ChatItem(
    data: ChatItemData,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .background(if (data.unreadCount > 0 && data.name == "Dapur Mamachi") Color(0xFFFFF0F0) else Color.White) // Highlight contoh
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = data.avatarRes),
            contentDescription = data.name,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(56.dp)
                .clip(CircleShape)
                .background(Color.LightGray)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = data.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.Black
                )
                Text(
                    text = data.time,
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = data.message,
                    fontSize = 14.sp,
                    color = Color.Gray,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )

                if (data.unreadCount > 0) {
                    Box(
                        modifier = Modifier
                            .size(20.dp)
                            .clip(CircleShape)
                            .background(Color.Red),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = data.unreadCount.toString(),
                            color = Color.White,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}