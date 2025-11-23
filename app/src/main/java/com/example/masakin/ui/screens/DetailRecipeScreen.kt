@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.masakin.ui.screens.recipe

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.example.masakin.ui.recipe.Recipe
import com.example.masakin.R
data class StepItemModel(val number: Int, val text: String)

private val defaultSteps = listOf(
    StepItemModel(1, "Panaskan sedikit minyak goreng, tumis bawang putih dan bawang merah hingga harum."),
    StepItemModel(2, "Masukkan potongan ayam, aduk hingga berubah warna."),
    StepItemModel(3, "Tambahkan kecap manis, kecap asin, saus tiram, minyak wijen, merica, dan garam. Aduk rata."),
    StepItemModel(4, "Tuang kaldu ayam, masak hingga bumbu meresap. Angkat dan sisihkan."),
    StepItemModel(5, "Bagi mie yang sudah direbus ke dalam beberapa mangkuk saji."),
    StepItemModel(6, "Tuang ayam dan kuah di atas mie. Tambahkan sawi hijau, bawang goreng, dan kerupuk pangsit."),
    StepItemModel(7, "Sajikan mie ayam dalam keadaan hangat.")
)

private val defaultIngredients = listOf(
    IngredientData("Daging Ayam", "165 cal", "Rp25.000", R.drawable.daging_ayam),
    IngredientData("Mie", "100 cal", "Rp5.000", R.drawable.mie_mentah),
    IngredientData("Selada", "100 cal", "Rp8.000", R.drawable.selada),
    IngredientData("Bawang Putih", "50 cal", "Rp10.000", R.drawable.bawang_putih),
    IngredientData("Kecap Manis", "20 cal", "Rp15.000", R.drawable.kecap_manis)
)

private val defaultAlat = listOf(
    "Panci besar",
    "Wajan",
    "Pisau",
    "Talenan",
    "Sendok kayu atau spatula",
    "Saringan",
    "Mangkok saji"
)

data class IngredientData(val name: String, val cal: String, val price: String = "", val imageRes: Int = 0)

@Composable
fun RecipeDetailScreen(
    recipe: Recipe,
    onBack: () -> Unit,
    steps: List<StepItemModel> = defaultSteps,
    ingredients: List<IngredientData> = defaultIngredients,
    alat: List<String> = defaultAlat
) {
    val RedPrimary = Color(0xFFD32F2F)
    val RedLight = Color(0xFFFFEBEE)
    val GrayText = Color(0xFF757575)
    val GreenRating = Color(0xFF4CAF50)

    // State untuk menyimpan status setiap langkah (checked atau tidak)
    val checkedSteps = remember { mutableStateOf(List(steps.size) { false }) }

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 100.dp)
        ) {
            // Header Image dengan overlay icons
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(280.dp)
                ) {
                    Image(
                        painter = painterResource(id = recipe.imageRes),
                        contentDescription = recipe.name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )

                    // Top bar icons
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .statusBarsPadding(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        IconButton(
                            onClick = onBack,
                            modifier = Modifier
                                .size(40.dp)
                                .background(Color.White, CircleShape)
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Back",
                                tint = Color.Black
                            )
                        }

                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            IconButton(
                                onClick = { },
                                modifier = Modifier
                                    .size(40.dp)
                                    .background(Color.White, CircleShape)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Share,
                                    contentDescription = "Share",
                                    tint = Color.Black
                                )
                            }
                            IconButton(
                                onClick = { },
                                modifier = Modifier
                                    .size(40.dp)
                                    .background(Color.White, CircleShape)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.FavoriteBorder,
                                    contentDescription = "Favorite",
                                    tint = Color.Black
                                )
                            }
                        }
                    }
                }
            }

            // Title + Rating Badge
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .offset(y = (-30).dp)
                        .background(
                            Color.White,
                            RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)
                        )
                        .padding(horizontal = 16.dp)
                        .padding(top = 20.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Top
                    ) {
                        Text(
                            text = recipe.name,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.weight(1f),
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                        Spacer(Modifier.width(8.dp))
                        // Rating badge hijau
                        Box(
                            modifier = Modifier
                                .background(GreenRating, RoundedCornerShape(4.dp))
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = String.format("%.1f", recipe.rating),
                                color = Color.White,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = "${recipe.servings} Menit",
                        fontSize = 13.sp,
                        color = GrayText
                    )
                }
            }

            // Chef info
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .padding(top = 4.dp, bottom = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Avatar chef
                    Image(
                        painter = painterResource(id = R.drawable.chef_juna),
                        contentDescription = "Chef Avatar",
                        modifier = Modifier
                            .size(44.dp)
                            .clip(CircleShape)
                            .background(Color.LightGray),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(Modifier.width(4.dp))
                    Column {
                        Text("Juna Rorimpadey", fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                        Text("Chef", fontSize = 12.sp, color = GrayText)
                    }
                }
            }

            // Alat dan Bahan section
            item {
                Column(modifier = Modifier.padding(top = 16.dp)) {
                    Text(
                        text = "Alat dan Bahan",
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = RedPrimary,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                    Spacer(Modifier.height(12.dp))

                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        contentPadding = PaddingValues(horizontal = 16.dp)
                    ) {
                        items(ingredients) { ingredient ->
                            Box(modifier = Modifier.width(120.dp)) {
                                IngredientCard(
                                    name = ingredient.name,
                                    cal = ingredient.cal,
                                    price = ingredient.price,
                                    imageRes = ingredient.imageRes,
                                    redColor = RedPrimary
                                )
                            }
                        }
                    }
                }
            }

            // Alat list
            item {
                Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)) {
                    Text(
                        text = "Alat",
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = RedPrimary
                    )
                    Spacer(Modifier.height(8.dp))
                    alat.forEach { item ->
                        Text(
                            text = "- $item",
                            fontSize = 14.sp,
                            modifier = Modifier.padding(vertical = 2.dp)
                        )
                    }
                }
            }

            // Cara memasak section
            item {
                Text(
                    text = "Cara memasak",
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = RedPrimary,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }

            // Steps dengan timeline dan checkbox
            itemsIndexed(steps) { index, step ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    // Timeline column dengan checkbox
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.width(40.dp)
                    ) {
                        // Circle dengan nomor atau centang
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .border(2.dp, RedPrimary, CircleShape)
                                .background(
                                    if (checkedSteps.value[index]) RedPrimary else Color.Transparent,
                                    CircleShape
                                )
                                .clickable {
                                    // Toggle checked state
                                    checkedSteps.value = checkedSteps.value.toMutableList().apply {
                                        this[index] = !this[index]
                                    }
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            if (checkedSteps.value[index]) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = "Checked",
                                    tint = Color.White,
                                    modifier = Modifier.size(20.dp)
                                )
                            } else {
                                Text(
                                    text = " ",
                                    color = RedPrimary,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp
                                )
                            }
                        }
                        // Line ke bawah (kecuali item terakhir)
                        if (index < steps.lastIndex) {
                            Box(
                                modifier = Modifier
                                    .width(2.dp)
                                    .height(60.dp)
                                    .background(RedPrimary)
                            )
                        }
                    }

                    Spacer(Modifier.width(12.dp))

                    // Step content
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(bottom = if (index < steps.lastIndex) 16.dp else 0.dp)
                    ) {
                        Text(
                            text = "Langkah ${step.number}",
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 14.sp
                        )
                        Spacer(Modifier.height(4.dp))
                        Text(
                            text = step.text,
                            fontSize = 13.sp,
                            color = GrayText,
                            lineHeight = 18.sp
                        )
                    }
                }
            }

            item { Spacer(Modifier.height(24.dp)) }
        }

        // Bottom button
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .background(Color.White)
                .padding(16.dp)
        ) {
            Button(
                onClick = { /* aksi selesai */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = RedPrimary),
                shape = RoundedCornerShape(25.dp)
            ) {
                Text("Selesai", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
            }
        }
    }
}

@Composable
fun IngredientCard(name: String, cal: String, price: String, imageRes: Int, redColor: Color) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Gambar bahan
            if (imageRes != 0) {
                Image(
                    painter = painterResource(id = imageRes),
                    contentDescription = name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0xFFF5F5F5))
                )
            }
            Spacer(Modifier.height(8.dp))
            Text(
                text = name,
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = cal,
                fontSize = 11.sp,
                color = Color.Gray
            )
            Spacer(Modifier.height(6.dp))
            Button(
                onClick = { /* Beli action */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(32.dp),
                colors = ButtonDefaults.buttonColors(containerColor = redColor),
                shape = RoundedCornerShape(8.dp),
                contentPadding = PaddingValues(0.dp)
            ) {
                Text(
                    text = "Beli",
                    color = Color.White,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}