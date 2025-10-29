package com.example.masakin.ui.mart.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.masakin.R

@Composable
fun MartDagingScreen(onBack: () -> Unit = {}) {
    var selectedCategory by remember { mutableStateOf("Daging") }
    var searchText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Spacer(Modifier.height(24.dp))
        MartHeader(searchText, onSearchChange = { searchText = it }, onBack = onBack)
        Spacer(Modifier.height(12.dp))
        MartMenuRow()
        Spacer(Modifier.height(8.dp))
        MartCategoryRow(selectedCategory) { selectedCategory = it }
        Spacer(Modifier.height(14.dp))
        MartProductSection()
    }
}

@Composable
fun MartHeader(searchText: String, onSearchChange: (String) -> Unit, onBack: () -> Unit) {
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {

            Image(
                painter = painterResource(R.drawable.mart_ic_location_red),
                contentDescription = "Back",
                modifier = Modifier
                    .size(18.dp)
                    .clickable { onBack() }
            )
            Spacer(Modifier.width(6.dp))
            Column {
                Text("Delivery address", fontSize = 10.sp, color = Color.Gray)
                Text(
                    "Malang, Jawa Timur",
                    color = Color.Red,
                    fontWeight = FontWeight.Medium,
                    fontSize = 12.sp
                )
            }
            Spacer(Modifier.weight(1f))
            Image(
                painter = painterResource(R.drawable.mart_ic_notification),
                contentDescription = "Notification",
                modifier = Modifier
                    .size(22.dp)
                    .clickable { }
            )
        }

        Spacer(Modifier.height(10.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .border(1.dp, Color.Gray.copy(0.4f), RoundedCornerShape(10.dp))
                .clip(RoundedCornerShape(10.dp))
                .padding(horizontal = 12.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            BasicTextField(
                value = searchText,
                onValueChange = onSearchChange,
                singleLine = true,
                textStyle = LocalTextStyle.current.copy(
                    fontSize = 13.sp,
                    color = Color.Black
                ),
                decorationBox = { innerTextField ->
                    if (searchText.isEmpty()) {
                        Text(
                            text = "Search",
                            color = Color.Gray.copy(alpha = 0.6f),
                            fontSize = 13.sp
                        )
                    }
                    innerTextField()
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun MartMenuRow() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        MenuIcon(R.drawable.mart_ic_cart)
        MenuIcon(R.drawable.mart_ic_order)
        MenuIcon(R.drawable.mart_ic_favorite)
    }
}

@Composable
fun MenuIcon(icon: Int) {
    Image(
        painter = painterResource(icon),
        contentDescription = null,
        modifier = Modifier
            .size(55.dp)
            .clickable { }
    )
}

@Composable
fun MartCategoryRow(selected: String, onSelect: (String) -> Unit) {
    val categories = listOf(
        "Daging" to R.drawable.mart_ic_cat_daging,
        "Buah" to R.drawable.mart_ic_cat_buah,
        "Sayur" to R.drawable.mart_ic_cat_sayur,
        "Bumbu" to R.drawable.mart_ic_cat_bumbu
    )

    Column {
        Text(
            text = "Kategori",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            categories.forEach { (name, icon) ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.clickable { onSelect(name) }
                ) {
                    Image(
                        painter = painterResource(icon),
                        contentDescription = name,
                        modifier = Modifier.size(60.dp)
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = name,
                        color = if (selected == name) Color.Red else Color.Black,
                        fontWeight = if (selected == name) FontWeight.Bold else FontWeight.Medium,
                        fontSize = 13.sp
                    )
                }
            }
        }
    }
}

@Composable
fun MartProductSection() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Recent Product",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(8.dp))
                .clickable {},
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(R.drawable.mart_ic_filter),
                contentDescription = "Filter",
                modifier = Modifier.size(28.dp)
            )
        }
    }

    Spacer(Modifier.height(12.dp))
    MartProductGrid()
}

data class DummyProduct(val name: String, val price: String, val image: Int)

@Composable
fun MartProductGrid() {
    val products = listOf(
        DummyProduct("Daging Ayam", "Rp25.000", R.drawable.mart_ayam),
        DummyProduct("Daging Sapi", "Rp60.000", R.drawable.mart_sapi),
        DummyProduct("Daging Bebek", "Rp45.000", R.drawable.mart_bebek),
        DummyProduct("Daging Domba", "Rp55.000", R.drawable.mart_domba)
    )

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.padding(bottom = 60.dp)
    ) {
        items(products) { product -> ProductCard(product) }
    }
}

@Composable
fun ProductCard(product: DummyProduct) {
    Column(
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .fillMaxWidth()
    ) {
        Image(
            painter = painterResource(product.image),
            contentDescription = product.name,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .height(160.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
        )
        Spacer(Modifier.height(6.dp))
        Text(
            product.name,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            modifier = Modifier.padding(horizontal = 4.dp)
        )
        Text(
            product.price,
            color = Color.Red,
            fontWeight = FontWeight.Medium,
            fontSize = 13.sp,
            modifier = Modifier.padding(start = 4.dp, bottom = 6.dp)
        )
    }
}
