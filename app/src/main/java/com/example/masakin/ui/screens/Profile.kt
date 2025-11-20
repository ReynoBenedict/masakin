package com.example.masakin.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.masakin.ui.components.BottomNavBar
import com.example.masakin.R
import com.example.masakin.ui.theme.Red50
import com.example.masakin.ui.theme.Red60

@Composable
fun ProfileScreen(
    onOpenHome: () -> Unit,
    onOpenChat: () -> Unit,
    onOpenChatbot: () -> Unit = {},
    onOpenMyFood: () -> Unit,
    onOpenProfile: () -> Unit,
    onLogout: () -> Unit
) {
    var selectedTab by remember { mutableStateOf("profile") }

    LaunchedEffect(selectedTab) {
        when (selectedTab) {
            "home" -> onOpenHome()
            "chat" -> onOpenChat()
            "Chatbot" -> onOpenChatbot()
            "food" -> onOpenMyFood()
            "profile" -> onOpenProfile()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Header: image background + overlayed content
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(345.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.profile_background),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                )
                // Overlay content (title + card)
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Title
                    Text(
                        text = "Profile",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    // Card Profile
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 0.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            // Profile Info
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                // Avatar
                                Box(
                                    modifier = Modifier
                                        .size(56.dp)
                                        .clip(CircleShape)
                                        .background(Color.Gray),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Person,
                                        contentDescription = "Profile",
                                        tint = Color.White,
                                        modifier = Modifier.size(32.dp)
                                    )
                                }

                                Spacer(modifier = Modifier.width(12.dp))

                                // Name and Email
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = "Richard",
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.Black
                                    )
                                    Text(
                                        text = "richard@gmail.com",
                                        fontSize = 14.sp,
                                        color = Color.Gray
                                    )
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier
                                            .padding(top = 4.dp)
                                            .clickable { /* Edit Profile */ }
                                    ) {
                                        Text(
                                            text = "Edit Profile",
                                            fontSize = 14.sp,
                                            color = Color(0xFFB71C1C),
                                            fontWeight = FontWeight.Medium
                                        )
                                        Icon(
                                            imageVector = Icons.Default.KeyboardArrowRight,
                                            contentDescription = "Edit",
                                            tint = Color(0xFFB71C1C),
                                            modifier = Modifier.size(18.dp)
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            // Pro Member Badge
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { /* Open Membership */ }
                                    .padding(vertical = 8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.CheckCircle,
                                        contentDescription = "Pro Member",
                                        tint = Color(0xFFB71C1C),
                                        modifier = Modifier.size(24.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "Pro Member",
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = Color.Black
                                    )
                                }
                                Icon(
                                    imageVector = Icons.Default.KeyboardArrowRight,
                                    contentDescription = "Arrow",
                                    tint = Color.Gray
                                )
                            }

                            Spacer(modifier = Modifier.height(8.dp))
                            // Quick Access Buttons
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                QuickAccessButton(
                                    icon = Icons.Default.ShoppingCart,
                                    label = "My Order",
                                    onClick = { /* Navigate to My Order */ }
                                )
                                QuickAccessButton(
                                    icon = Icons.Default.Stars,
                                    label = "My Point",
                                    onClick = { /* Navigate to My Point */ }
                                )
                            }
                        }
                    }
                }
            }

            // More Tools Section (di bawah header)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "More Tools",
                    fontSize = 16.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                // Menu Items
                MenuItemRow(
                    icon = Icons.Default.CardMembership,
                    title = "Membership",
                    iconColor = Color(0xFFB71C1C),
                    onClick = { /* Navigate to Membership */ }
                )

                MenuItemRow(
                    icon = Icons.Default.Favorite,
                    title = "Collects",
                    iconColor = Color(0xFFB71C1C),
                    onClick = { /* Navigate to Collects */ }
                )

                MenuItemRow(
                    icon = Icons.Default.CardGiftcard,
                    title = "Invite Friends",
                    iconColor = Color(0xFFB71C1C),
                    onClick = { /* Navigate to Invite Friends */ }
                )

                MenuItemRow(
                    icon = Icons.Default.Logout,
                    title = "Logout",
                    iconColor = Color(0xFFB71C1C),
                    onClick = onLogout
                )
            }

            Spacer(modifier = Modifier.height(72.dp)) // ruang untuk bottom nav
        }

        // Navbar
        BottomNavBar(
            modifier = Modifier.align(Alignment.BottomCenter),
            selectedItem = selectedTab,
            onItemSelected = { selectedTab = it }
        )
    }
}

@Composable
fun QuickAccessButton(
    icon: ImageVector,
    label: String,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(4.dp)
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = Red50,
                modifier = Modifier.size(24.dp)
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = label,
            fontSize = 12.sp,
            color = Red60
        )
    }
}

@Composable
fun MenuItemRow(
    icon: ImageVector,
    title: String,
    iconColor: Color,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = iconColor,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = title,
                    fontSize = 16.sp,
                    color = Color.Black
                )
            }
            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = "Arrow",
                tint = Color.Gray
            )
        }
    }
}
