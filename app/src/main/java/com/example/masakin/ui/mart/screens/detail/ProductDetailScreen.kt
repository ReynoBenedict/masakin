package com.example.masakin.ui.mart.screens.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.masakin.R
import com.example.masakin.ui.mart.components.RelatedRecipeCard
import com.example.masakin.ui.mart.data.Product
import com.example.masakin.ui.mart.viewmodel.MartViewModel
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
    productId: Int,
    onBack: () -> Unit,
    onCartClick: () -> Unit,
    viewModel: MartViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val product = uiState.selectedProduct
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(productId) {
        viewModel.loadProductDetail(productId)
    }

    if (product == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = product.name,
                        color = Color(0xFFD32F2F),
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { /* Menu */ }) {
                        Icon(Icons.Default.MoreVert, contentDescription = "Menu")
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.White)
            )
        },
        bottomBar = {
            BottomBar(
                onAddToCart = {
                    viewModel.addToCart(product, 1)
                    scope.launch {
                        snackbarHostState.showSnackbar("Produk berhasil dimasukkan ke Cart!")
                    }
                },
                onChatClick = { /* Chat */ },
                onCartClick = onCartClick
            )
        },
        containerColor = Color.White
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(bottom = 24.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(16.dp))

                // Nutrition & Image Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Nutrition Info (Left side - 2 Columns)
                    Row(
                        modifier = Modifier.weight(1f),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        // Col 1
                        Column {
                            NutritionItemV(label = "Calories", value = "${product.nutrition.calories}", unit = "kcal", icon = "🔥")
                            Spacer(modifier = Modifier.height(16.dp))
                            NutritionItemV(label = "Protein", value = "${product.nutrition.protein}", unit = "g", icon = "🥩")
                            Spacer(modifier = Modifier.height(16.dp))
                            NutritionItemV(label = "Fiber", value = "${product.nutrition.fiber}", unit = "g", icon = "🥦")
                        }
                        // Col 2
                        Column {
                            NutritionItemV(label = "Water", value = "${product.nutrition.water}", unit = "", icon = "💧")
                            Spacer(modifier = Modifier.height(16.dp))
                            NutritionItemV(label = "Carbs", value = "${product.nutrition.carbs}", unit = "g", icon = "🍞")
                            Spacer(modifier = Modifier.height(16.dp))
                            NutritionItemV(label = "Fat", value = "${product.nutrition.fat}", unit = "g", icon = "🧀")
                        }
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    // Image (Right side)
                    Image(
                        painter = painterResource(id = product.image),
                        contentDescription = product.name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(140.dp)
                            .clip(RoundedCornerShape(16.dp))
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Price & Store Info
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    // Store Info
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painter = painterResource(id = R.drawable.mart_ic_cat_daging), // Placeholder store icon
                            contentDescription = "Store",
                            modifier = Modifier.size(20.dp),
                            tint = Color(0xFFD32F2F)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "${product.storeName} | ${product.storeLocation}",
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    }

                    // Price Info
                    Column(horizontalAlignment = Alignment.End) {
                        Text(
                            text = formatRupiah(product.price),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFD32F2F)
                        )
                        Text(
                            text = product.unit,
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    }
                }

                HorizontalDivider(modifier = Modifier.padding(vertical = 20.dp), color = Color(0xFFF3F4F6))

                // Description
                Text(
                    text = "Deskripsi",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF111827)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = product.description,
                    fontSize = 14.sp,
                    color = Color.Gray,
                    lineHeight = 22.sp,
                    textAlign = TextAlign.Justify
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Related Recipes Title
                Text(
                    text = "Related Recipes",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF111827)
                )
                Spacer(modifier = Modifier.height(12.dp))
            }

            // Related Recipes Grid (Chunked rows)
            items(product.relatedRecipes.chunked(2)) { rowItems ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    for (recipe in rowItems) {
                        RelatedRecipeCard(
                            recipe = recipe,
                            onClick = { /* Open recipe */ },
                            modifier = Modifier.weight(1f)
                        )
                    }
                    // Fill empty space if odd number of items
                    if (rowItems.size < 2) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
            }
            
            item {
                 Row(
                    modifier = Modifier.fillMaxWidth().clickable { /* See more */ },
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Lihat lainnya",
                        color = Color(0xFFD32F2F),
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack, // Should be forward arrow, but using back mirrored
                        contentDescription = null,
                        tint = Color(0xFFD32F2F),
                        modifier = Modifier.size(16.dp).rotate(180f)
                    )
                }
            }
        }
    }
}

@Composable
fun BottomBar(
    onAddToCart: () -> Unit,
    onChatClick: () -> Unit,
    onCartClick: () -> Unit
) {
    Surface(
        shadowElevation = 16.dp,
        color = Color.White
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Chat Button
            IconButton(
                onClick = onChatClick,
                modifier = Modifier
                    .border(1.dp, Color(0xFFD32F2F), RoundedCornerShape(8.dp))
                    .size(48.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.ChatBubbleOutline,
                    contentDescription = "Chat",
                    tint = Color(0xFFD32F2F)
                )
            }
            
            // Vertical Divider
            Spacer(modifier = Modifier.width(12.dp))
            Box(
                modifier = Modifier
                    .height(32.dp)
                    .width(1.dp)
                    .background(Color.LightGray)
            )
            Spacer(modifier = Modifier.width(12.dp))

            // Cart Button
            IconButton(
                onClick = onCartClick,
                modifier = Modifier
                    .size(48.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.ShoppingCart,
                    contentDescription = "Cart",
                    tint = Color(0xFFD32F2F),
                    modifier = Modifier.size(28.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Buy Button
            Button(
                onClick = { /* Buy Now - Coming Soon */ },
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD32F2F))
            ) {
                Text(
                    text = "Beli Sekarang",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }
        }
    }
}

@Composable
fun NutritionItemV(label: String, value: String, unit: String, icon: String) {
    Column(horizontalAlignment = Alignment.Start) {
        Text(
            text = label, 
            fontSize = 14.sp, 
            fontWeight = FontWeight.Bold, 
            color = Color(0xFF111827)
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = icon, fontSize = 12.sp)
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "$value$unit", 
                fontSize = 12.sp, 
                color = Color.Gray
            )
        }
    }
}

private fun formatRupiah(amount: Int): String {
    val localeID = Locale.forLanguageTag("id-ID")
    val numberFormat = NumberFormat.getCurrencyInstance(localeID)
    numberFormat.maximumFractionDigits = 0
    return numberFormat.format(amount).replace("Rp", "Rp ")
}

// Extension for rotation
fun Modifier.rotate(degrees: Float) = this.then(
    Modifier.graphicsLayer(rotationZ = degrees)
)
