@file:OptIn(ExperimentalMaterial3Api::class)
package com.example.masakin.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.masakin.ui.recipe.*
import androidx.compose.material.icons.Icons

@Composable
fun RecipeRoute(
    viewModel: RecipeViewModel,
    onBack: () -> Unit
) {
    val ui by viewModel.ui.collectAsStateWithLifecycle()
    RecipeScreen(
        ui = ui,
        onQueryChange = viewModel::updateQuery,
        onBack = onBack
    )
}

@Composable
private fun RecipeScreen(
    ui: RecipeUiState,
    onQueryChange: (String) -> Unit,
    onBack: () -> Unit
) {
    val featuredState = rememberLazyListState()
    val selectedIndex by remember { derivedStateOf { featuredState.firstVisibleItemIndex } }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Recipe", color = Color.Red, fontSize = 16.sp, fontWeight = FontWeight.SemiBold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { inner ->

        if (ui.isLoading) {
            Box(Modifier.fillMaxSize().padding(inner), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
            return@Scaffold
        }

        // ==== GANTI Column -> LazyColumn ====
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(inner),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(bottom = 24.dp) // ruang di bawah agar tidak mentok
        ) {
            // 1) Greeting + Search (SATU item)
            item {
                Column(Modifier.padding(horizontal = 16.dp)) {
                    Text("Hi, Richard", fontSize = 13.sp, color = Color.Gray)
                    Text("Mau memasak apa hari ini?", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                    Spacer(Modifier.height(12.dp))
                    RecipeSearchBar(
                        query = ui.query,
                        onQueryChange = onQueryChange,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            // 2) Featured Carousel (SATU item)
            item {
                FeaturedCarousel(items = ui.featured, state = featuredState)
            }

            // 3) Dots Indicator (SATU item)
            item {
                DotsIndicator(
                    listSize = ui.featured.size,
                    selectedIndex = selectedIndex,
                    accentRed = RecipeRed
                )
            }

            // 4) Sections per kategori (PAKAI items{}, jangan forEach)
            items(ui.categories.entries.toList()) { (category, list) ->
                CategorySection(
                    title = category,
                    recipes = list,
                    onSeeMore = { /* TODO */ },
                    accentRed = RecipeRed
                )
            }
        }
    }
}
