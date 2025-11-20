package com.example.masakin.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.masakin.ui.components.BottomNavBar
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalDensity
import com.example.masakin.R
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.lazy.LazyRow

// Data class untuk menyimpan item makanan (pakai resource ID)
data class FoodItem(
    val imageRes: Int,
    val id: String = ""
)

// Data class untuk grup makanan berdasarkan tanggal/kategori
data class FoodGroup(
    val title: String,
    val foods: List<FoodItem>,
    val isToday: Boolean = false
)

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
            "food" -> {
            }
            "profile" -> onOpenProfile()
        }
    }

    // Sample data - ganti dengan data dari database/API Anda
    val foodGroups = remember {
        listOf(
            FoodGroup(
                title = "Hari ini",
                isToday = true,
                foods = listOf(
                    FoodItem(imageRes = R.drawable.makanan),
                    FoodItem(imageRes = R.drawable.makanan),
                    FoodItem(imageRes = R.drawable.makanan),
                    FoodItem(imageRes = R.drawable.makanan),
                    FoodItem(imageRes = R.drawable.makanan)
                )
            )
        )
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 90.dp)
        ) {
            // Header dengan background image (ganti dengan drawable kamu)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.myfood_background),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Column (
                            modifier = Modifier
                                .padding(start = 8.dp, end = 8.dp, top = 16.dp, bottom = 8.dp)
                        ) {
                            Text(
                                text = "Hi, Richard",
                                color = Color.White,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Bagaimana kabarmu?",
                                color = Color.White,
                                fontSize = 14.sp
                            )
                        }

                        IconButton(onClick = { }) {
                            Icon(
                                imageVector = Icons.Default.Notifications,
                                contentDescription = "Notifications",
                                tint = Color.White
                            )
                        }
                    }
                }
            }


            // Food gallery list
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(foodGroups) { group ->
                    FoodGroupSection(
                        group = group,
                        onSeeDetail = { /* Navigate to detail */ }
                    )
                }

                // Spacing at bottom
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
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
fun FoodGroupSection(
    group: FoodGroup,
    onSeeDetail: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        // Header section
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = group.title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = if (group.isToday) Color(0xFFB71C1C) else Color.Black
            )

            TextButton(onClick = onSeeDetail) {
                Text(
                    text = "Lihat detail",
                    color = Color.Gray,
                    fontSize = 14.sp
                )
            }
        }

        // 🔥 Horizontal Lazy Loading
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(group.foods) { food ->
                FoodImageCard(
                    imageRes = food.imageRes,
                    modifier = Modifier
                        .size(120.dp)     // kotak 120 × 120
                )
            }
        }
    }
}

@Composable
fun FoodImageCard(
    imageRes: Int, // menerima resource ID
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .aspectRatio(1f)
            .widthIn(max = 120.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = "Food image",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
}

// FlowRow custom composable untuk membuat grid yang fleksibel
@Composable
fun FlowRow(
    modifier: Modifier = Modifier,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    maxItemsInEachRow: Int = Int.MAX_VALUE,
    content: @Composable () -> Unit
) {
    val density = LocalDensity.current
    val itemSpacingPx = with(density) { 8.dp.roundToPx() }

    Layout(
        content = content,
        modifier = modifier
    ) { measurables, constraints ->
        val rows = mutableListOf<MeasuredRow>()
        var currentRow = mutableListOf<Placeable>()
        var currentRowWidth = 0

        measurables.forEach { measurable ->
            val placeable = measurable.measure(constraints)

            if (currentRow.size >= maxItemsInEachRow ||
                currentRowWidth + placeable.width + itemSpacingPx > constraints.maxWidth && currentRow.isNotEmpty()
            ) {
                rows.add(MeasuredRow(currentRow.toList(), currentRowWidth))
                currentRow = mutableListOf()
                currentRowWidth = 0
            }

            currentRow.add(placeable)
            currentRowWidth += placeable.width + if (currentRow.size > 1) itemSpacingPx else 0
        }

        if (currentRow.isNotEmpty()) {
            rows.add(MeasuredRow(currentRow, currentRowWidth))
        }

        val width = constraints.maxWidth
        val height = rows.sumOf { it.placeables.maxOf { p -> p.height } + itemSpacingPx }

        layout(width, height) {
            var yPosition = 0
            rows.forEach { row ->
                var xPosition = 0
                val rowHeight = row.placeables.maxOf { it.height }

                row.placeables.forEach { placeable ->
                    placeable.place(x = xPosition, y = yPosition)
                    xPosition += placeable.width + itemSpacingPx
                }

                yPosition += rowHeight + itemSpacingPx
            }
        }
    }
}

private data class MeasuredRow(
    val placeables: List<Placeable>,
    val width: Int
)
