package com.example.masakin.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Text
import com.example.masakin.ui.components.BottomNavBar

@Composable
fun MyFoodScreen(
    onOpenHome: () -> Unit,
    onOpenChat: () -> Unit,
    onOpenChatbot: () -> Unit = {},
    onOpenMyFood: () -> Unit,
    onOpenProfile: () -> Unit
) {
    var selectedTab by remember { mutableStateOf("food") }

    LaunchedEffect(selectedTab) {
        when (selectedTab) {
            "home" -> onOpenHome()
            "chat" -> onOpenChat()
            "Chatbot" -> onOpenChatbot()
            "food" -> onOpenMyFood()      // sudah di sini, bisa dibiarkan kosong juga
            "profile" -> onOpenProfile()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {

        // Konten utama
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 90.dp),
            contentAlignment = Alignment.Center
        ) {
            Text("Halaman MyFood", modifier = Modifier.padding(20.dp))
        }

        // Navbar
        BottomNavBar(
            modifier = Modifier.align(Alignment.BottomCenter),
            selectedItem = selectedTab,
            onItemSelected = { selectedTab = it }
        )
    }
}