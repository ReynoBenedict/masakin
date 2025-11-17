package com.example.masakin.ui.components

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.AutoAwesome
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.RamenDining
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BottomNavBar(
    modifier: Modifier = Modifier,
    selectedItem: String,
    onItemSelected: (String) -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .shadow(8.dp, RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                .background(Color.White, RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                .padding(horizontal = 35.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            AnimatedNavItem(
                label = "Home",
                icon = Icons.Filled.Home,
                isSelected = selectedItem == "home",
                onClick = { onItemSelected("home") }
            )

            AnimatedNavItem(
                label = "Chat",
                icon = Icons.Outlined.ChatBubbleOutline,
                isSelected = selectedItem == "chat",
                onClick = { onItemSelected("chat") }
            )

            Spacer(modifier = Modifier.width(60.dp))

            AnimatedNavItem(
                label = "My Food",
                icon = Icons.Outlined.RamenDining,
                isSelected = selectedItem == "food",
                onClick = { onItemSelected("food") }
            )

            AnimatedNavItem(
                label = "Profile",
                icon = Icons.Outlined.Person,
                isSelected = selectedItem == "profile",
                onClick = { onItemSelected("profile") }
            )
        }

        AnimatedFloatingScanButton(
            isSelected = selectedItem == "Chatbot",
            onClick = { onItemSelected("Chatbot") }
        )
    }
}

@Composable
fun AnimatedNavItem(
    label: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val targetScale = if (isSelected) 1.25f else 1f

    val scale by animateFloatAsState(
        targetValue = targetScale,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = ""
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .scale(scale)
            .clickable { onClick() }
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = if (isSelected) Color(0xFFFF3D3D) else Color.Gray,
            modifier = Modifier.size(26.dp)
        )
        Text(
            text = label,
            fontSize = 12.sp,
            color = if (isSelected) Color(0xFFFF3D3D) else Color.Gray
        )
    }
}

@Composable
fun AnimatedFloatingScanButton(
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val targetScale = if (isSelected) 1.2f else 1f

    val scale by animateFloatAsState(
        targetValue = targetScale,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = ""
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .offset(y = (-25).dp)
            .scale(scale)
            .clickable { onClick() }
    ) {
        Box(
            modifier = Modifier
                .size(65.dp)
                .background(Color(0xFFFF3D3D), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Outlined.AutoAwesome,
                contentDescription = "Chabot",
                tint = Color.White,
                modifier = Modifier.size(32.dp)
            )
        }

        Text(
            text = "Chatbot",
            fontSize = 12.sp,
            color = if (isSelected) Color(0xFFFF3D3D) else Color.Gray
        )
    }
}
