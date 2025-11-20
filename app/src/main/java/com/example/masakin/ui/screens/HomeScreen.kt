package com.example.masakin.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.masakin.R
import com.example.masakin.ui.components.BottomNavBar
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.example.masakin.ui.theme.Red20
import com.example.masakin.ui.theme.Red50
import androidx.compose.foundation.layout.offset
import androidx.compose.ui.Alignment


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onOpenChatbot: () -> Unit = {},
    onOpenRecipe: () -> Unit = {},
    onOpenCommunity: () -> Unit = {},
    onOpenMart: () -> Unit = {},
    onOpenConsultation: () -> Unit = {},
    onOpenProfile: () -> Unit,
    onOpenMyFood: () -> Unit,
    onOpenChat: () -> Unit
) {
    var selectedTab by remember { mutableStateOf("home") }

    LaunchedEffect(selectedTab) {
        when (selectedTab) {
            "home" -> {}
            "chat" -> onOpenChat()
            "Chatbot" -> onOpenChatbot()
            "food" -> onOpenMyFood()
            "profile" -> onOpenProfile()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 80.dp)
        ) {
            // Header Section
            item {
                TopAppBar(
                    title = {},
                    navigationIcon = {
                        IconButton(onClick = { /* Search action */ }) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search",
                                tint = Color.Gray
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = { /* Notification action */ }) {
                            Icon(
                                imageVector = Icons.Default.Notifications,
                                contentDescription = "Notifications",
                                tint = Red50
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.White
                    )
                )
            }

            // Banner Section with Auto Slide
            item {
                AutoSlidingBannerSection()
                Spacer(modifier = Modifier.height(16.dp))
            }

            // Points Section
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(2.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(CircleShape)
                                    .background(Red50),
                                contentAlignment = Alignment.Center
                            ) {

                                Text(
                                    text = "$",
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 20.sp
                                )
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = "150.000",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp
                                )
                                Text(
                                    text = "point",
                                    fontSize = 12.sp,
                                    color = Color.Gray
                                )
                            }
                        }
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            IconButton(
                                onClick = { /* Exchange */ },
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(CircleShape)
                                    .background(Red50)
                            ) {
                                Icon(
                                    painter = painterResource(id = android.R.drawable.ic_menu_rotate),
                                    contentDescription = "Exchange",
                                    tint = Color.White
                                )
                            }
                            Spacer(Modifier.width(4.dp))
                            IconButton(
                                onClick = { /* History */ },
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(CircleShape)
                                    .background(Red50)
                            ) {
                                Icon(
                                    painter = painterResource(id = android.R.drawable.ic_menu_recent_history),
                                    contentDescription = "History",
                                    tint = Color.White
                                )
                            }
                            Spacer(Modifier.width(4.dp))
                            IconButton(
                                onClick = { /* Others */ },
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(CircleShape)
                                    .background(Red50)
                            ) {
                                Icon(
                                    painter = painterResource(id = android.R.drawable.ic_menu_more),
                                    contentDescription = "Others",
                                    tint = Color.White
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
            }

            // Menu Grid Section (Mart, Consult, Recipe, Community)
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    MenuButton(
                        label = "Mart",
                        iconRes = R.drawable.mart_button,
                        onClick = onOpenMart
                    )
                    MenuButton(
                        label = "Consult",
                        iconRes = R.drawable.consult_button,
                        onClick = onOpenConsultation
                    )
                    MenuButton(
                        label = "Recipe",
                        iconRes = R.drawable.recipe_button,
                        onClick = onOpenRecipe
                    )
                    MenuButton(
                        label = "Community",
                        iconRes = R.drawable.community_button,
                        onClick = onOpenCommunity
                    )
                }
                Spacer(modifier = Modifier.height(24.dp))
            }

            // Popular Product Section

            val products = listOf(
                Triple("Bumbu Masak", "Rp20.000", "12 RB terjual"),
                Triple("Mie Kimbo", "Rp5.000", "4.5 RB terjual"),
                Triple("Susu Murni", "Rp15.000", "9.6 RB terjual"),
                Triple("Daging Burger", "Rp45.000", "12 RB terjual")
            )

            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 20.dp)
                    ,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Popular Product",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                    TextButton(onClick = { /* See more */ }) {
                        Text("See more", color = Red50)
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }

            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp) // Jarak antar baris (atas-bawah)
                ) {
                    // Logika: Bagi list produk menjadi kelompok berisi 2 (chunked(2))
                    products.chunked(2).forEach { rowItems ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp) // Jarak antar kolom (kiri-kanan)
                        ) {
                            // Loop setiap item dalam baris ini
                            rowItems.forEach { product ->
                                Box(modifier = Modifier.weight(1f)) { // weight(1f) agar lebar terbagi rata 50:50
                                    ProductCard(
                                        productName = product.first,
                                        price = product.second,
                                        sold = product.third
                                    )
                                }
                            }

                            // Trik: Jika jumlah produk ganjil, tambahkan Spacer kosong agar kartu tidak melar
                            if (rowItems.size < 2) {
                                Spacer(modifier = Modifier.weight(1f))
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
            }

            // Recipes of The Week Section
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Recipes of The Week",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                    TextButton(onClick = { /* See more */ }) {
                        Text("See more", color = Red50)
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
            }

            // Recipe Cards
            items(3) { index ->
                RecipeCard(
                    recipeName = if (index == 0) "Empuk, Meresap, Tahan Lama! Resep Rendang Daging"
                    else if (index == 1) "Gurih Berempah! Resep Sate Padang"
                    else "Tanpa Ungkep! Tapi Enak! Resep Ayam Goreng Bawang",
                    author = if (index == 0) "Devina Hermawan"
                    else if (index == 1) "Juna Rorimpandey"
                    else "Benedict Khoelieck"
                )
                Spacer(modifier = Modifier.height(12.dp))
            }

            // Bottom spacing
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        // Bottom Navigation Bar
        BottomNavBar(
            modifier = Modifier.align(Alignment.BottomEnd),
            selectedItem = selectedTab,
            onItemSelected = { selectedTab = it }
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AutoSlidingBannerSection() {
    // Daftar banner - ganti dengan gambar dari resource Anda
    val banners = listOf(
        BannerData("Banner 1", Color(0xFFFFC107)),
        BannerData("Banner 2", Color(0xFFFF5722)),
        BannerData("Banner 3", Color(0xFF4CAF50)),
        BannerData("Banner 4", Color(0xFF2196F3))
    )

    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { banners.size }
    )
    val coroutineScope = rememberCoroutineScope()

    // Auto-slide effect
    LaunchedEffect(pagerState.currentPage) {
        delay(3000) // Delay 3 detik
        val nextPage = (pagerState.currentPage + 1) % banners.size
        coroutineScope.launch {
            pagerState.animateScrollToPage(nextPage)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .padding(horizontal = 16.dp)
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(12.dp))
                    .background(banners[page].backgroundColor)
            ) {
                // Placeholder - ganti dengan Image dari resource
                // Contoh:
                // Image(
                //     painter = painterResource(id = R.drawable.banner_1),
                //     contentDescription = "Banner ${page + 1}",
                //     modifier = Modifier.fillMaxSize(),
                //     contentScale = ContentScale.Crop
                // )

                Text(
                    text = banners[page].title,
                    modifier = Modifier.align(Alignment.Center),
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        // Indicator dots
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            repeat(banners.size) { index ->
                Box(
                    modifier = Modifier
                        .size(if (pagerState.currentPage == index) 24.dp else 8.dp, 8.dp)
                        .clip(CircleShape)
                        .background(
                            if (pagerState.currentPage == index)
                                Color.White
                            else
                                Color.White.copy(alpha = 0.5f)
                        )
                )
            }
        }
    }
}

data class BannerData(
    val title: String,
    val backgroundColor: Color
)

@Composable
fun MenuButton(
    label: String,
    iconRes: Int, // Resource ID untuk gambar PNG
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable(onClick = onClick)
    ) {
        Box(
            modifier = Modifier
                .size(70.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Red20),
            contentAlignment = Alignment.Center
        ) {
            // Menggunakan gambar PNG dari resource
            Image(
                painter = painterResource(id = iconRes),
                contentDescription = label,
                modifier = Modifier.size(40.dp),
                contentScale = ContentScale.Fit,
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = label,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun ProductCard(
    productName: String,
    price: String,
    sold: String
) {
    Card(
        modifier = Modifier
            .width(160.dp)
            .height(220.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .background(Color(0xFFFFE5E5)),
                contentAlignment = Alignment.Center
            ) {
                // Placeholder - ganti dengan Image produk dari resource
                Text("🍲", fontSize = 40.sp)
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp)
            ) {
                Text(
                    text = productName,
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp,
                    maxLines = 2
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = price,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Red50
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = sold,
                    fontSize = 11.sp,
                    color = Color.Gray
                )
            }
        }
    }
}

@Composable
fun RecipeCard(
    recipeName: String,
    author: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // Background image placeholder
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFF8B4513))
            )

            // Dark overlay
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.3f))
            )

            // Content
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Bottom
            ) {
                Text(
                    text = recipeName,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape)
                            .background(Color.White)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = author,
                        color = Color.White,
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}