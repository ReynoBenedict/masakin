package com.example.masakin.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Tune
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/* ======== Palet lokal untuk screen ini ======== */
private val MasakinRed = Color(0xFFE53935)
private val StarAmber  = Color(0xFFFFC107)
private val Elevation  = 2.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConsultationScreen(onBack: () -> Unit = {}) {
    var query by remember { mutableStateOf("") }
    val all = remember { demoConsultants() }
    val favorites = remember { all.filter { it.favorite } }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MasakinRed,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                ),
                title = { Text("Consultation", fontSize = 18.sp, fontWeight = FontWeight.SemiBold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Kembali")
                    }
                }
            )
        }
    ) { inner ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(inner),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            /* ------------------- Search capsule (dirapikan) ------------------- */
            item {
                // kapsul merah
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .clip(RoundedCornerShape(28.dp))
                        .background(MasakinRed)
                        .padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // pill putih
                    TextField(
                        value = query,
                        onValueChange = { query = it },
                        leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null) },
                        placeholder = { Text("Search", fontSize = 14.sp) }, // placeholder 14
                        textStyle = LocalTextStyle.current.copy(fontSize = 14.sp), // isi 14
                        singleLine = true,
                        shape = RoundedCornerShape(22.dp),
                        modifier = Modifier
                            .weight(1f)
                            .height(44.dp), // tinggi konsisten
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            disabledContainerColor = Color.White,
                            cursorColor = MasakinRed,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent
                        )
                    )
                    Spacer(Modifier.width(10.dp))
                    // tombol filter bulat putih
                    Surface(
                        shape = CircleShape,
                        color = Color.White,
                        tonalElevation = 0.dp,
                        shadowElevation = 0.dp,
                        modifier = Modifier.size(42.dp)
                    ) {
                        IconButton(onClick = { /* TODO: buka filter */ }) {
                            Icon(Icons.Outlined.Tune, contentDescription = "Filter", tint = MasakinRed)
                        }
                    }
                }
            }

            /* ------------------- Favorite Consultant ------------------- */
            if (favorites.isNotEmpty()) {
                item { SectionHeader("Favorite Consultant", onSeeAll = { /* TODO */ }) }
                item {
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(favorites) { c -> FavoriteCard(c) }
                    }
                }
            }

            /* ------------------- Top Consultant ------------------- */
            item { SectionHeader("Top Consultant", onSeeAll = { /* TODO */ }) }
            items(
                all.filter {
                    it.name.contains(query, ignoreCase = true) ||
                            it.specialization.contains(query, ignoreCase = true)
                }
            ) { c ->
                TopConsultantCard(
                    c,
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 4.dp)
                        .fillMaxWidth()
                )
            }

            /* ------------------- Berita ------------------- */
            item { SectionHeader("Berita", onSeeAll = { /* TODO */ }) }
            item {
                NewsCard(
                    title = "BERDAYAKAN MASYARAKAT DESA, MAHASISWA POLIJE UBAH LIMBAH",
                    source = "Detik",
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                )
            }
        }
    }
}

/* ====================== Komponen kecil ====================== */

@Composable
private fun SectionHeader(
    title: String,
    onSeeAll: (() -> Unit)? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            title,
            fontSize = 10.sp,                          // <<< 10sp sesuai permintaan
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.weight(1f)
        )
        if (onSeeAll != null) {
            Text(
                text = "Lihat lainnya ›",
                fontSize = 10.sp,                      // <<< 10sp
                color = MasakinRed,
                modifier = Modifier.clickable { onSeeAll() }
            )
        }
    }
}

@Composable
private fun FavoriteCard(c: Consultant) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        tonalElevation = Elevation,
        modifier = Modifier
            .width(175.dp)
            .height(102.dp)
    ) {
        Box(Modifier.fillMaxSize()) {
            Box(
                Modifier
                    .padding(10.dp)
                    .size(24.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.secondaryContainer)
            )
            Column(
                Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomStart)
                    .padding(10.dp)
            ) {
                Text(c.name, fontSize = 10.sp, fontWeight = FontWeight.Medium) // kecil
                Text(
                    c.specialization,
                    fontSize = 10.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            // badge rating
            Surface(
                shape = RoundedCornerShape(10.dp),
                color = Color.White,
                shadowElevation = 0.dp,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("★", color = StarAmber, fontSize = 11.sp)
                    Spacer(Modifier.width(4.dp))
                    Text(String.format("%.1f", c.rating), fontSize = 10.sp)
                }
            }
        }
    }
}

@Composable
private fun TopConsultantCard(c: Consultant, modifier: Modifier = Modifier) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        tonalElevation = Elevation,
        modifier = Modifier
            .padding(start = 16.dp)
            .width(380.dp)
            .height(82.dp)
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                Modifier
                    .size(52.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.secondaryContainer)
            )
            Spacer(Modifier.width(12.dp))
            Column(Modifier.weight(1f)) {
                Text(                                     // <<< Nama dokter 14sp
                    c.name,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Text(                                     // spesialisasi 10sp
                    c.specialization,
                    fontSize = 10.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(Modifier.height(2.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RatingStarsSmall(c.rating)
                    Spacer(Modifier.width(6.dp))
                    Text(                                 // meta rating 10sp
                        "${String.format("%.1f", c.rating)}   (${c.reviews} Reviews)",
                        fontSize = 10.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            Icon(
                imageVector = Icons.Filled.KeyboardArrowRight,
                contentDescription = null,
                tint = MasakinRed
            )
        }
    }
}

@Composable
private fun RatingStarsSmall(rating: Double) {
    val filled = rating.coerceIn(0.0, 5.0).toInt()
    Row {
        repeat(5) { i ->
            Text(
                if (i < filled) "★" else "☆",
                color = if (i < filled) StarAmber else MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 10.sp
            )
        }
    }
}

@Composable
private fun NewsCard(title: String, source: String, modifier: Modifier = Modifier) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        tonalElevation = Elevation,
        modifier = modifier
    ) {
        Column {
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(132.dp)
                    .background(MaterialTheme.colorScheme.secondaryContainer)
            )
            Column(Modifier.padding(12.dp)) {
                Text(title, fontSize = 12.sp, maxLines = 2, overflow = TextOverflow.Ellipsis)
                Text(source, fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    }
}

/* ====================== Demo data (dummy) ====================== */
private data class Consultant(
    val id: String,
    val name: String,
    val specialization: String,
    val rating: Double,
    val reviews: Int,
    val favorite: Boolean = false
)

private fun demoConsultants() = listOf(
    Consultant("1", "Dr. Trini", "Penilaian Gizi", 4.6, 321, favorite = true),
    Consultant("2", "Dr. Sally", "Pengawasan Makanan", 4.3, 210, favorite = true),
    Consultant("3", "Dr. Lisa", "Penilaian Gizi", 4.7, 567),
    Consultant("4", "Dr. Richard Lee", "Perencanaan Diet", 4.7, 647),
    Consultant("5", "Dr. Sinta", "Pengawasan Makanan", 4.8, 765),
)

@Preview(showBackground = true)
@Composable
private fun PreviewConsultation() {
    ConsultationScreen()
}
