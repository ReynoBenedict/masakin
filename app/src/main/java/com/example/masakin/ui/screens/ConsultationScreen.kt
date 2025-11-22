package com.example.masakin.ui.screens

import androidx.compose.foundation.Image
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.masakin.R
import com.example.masakin.data.DummyData
import com.example.masakin.ui.consultation.Consultant

/* ======== Palet lokal untuk screen ini ======== */
private val MasakinRed = Color(0xFFE53935)
private val StarAmber  = Color(0xFFFFC107)
private val Elevation  = 2.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConsultationScreen(
    onBack: () -> Unit = {},
    onConsultantClick: (String) -> Unit = {}
) {
    var query by remember { mutableStateOf("") }

    // MENGAMBIL DATA DARI DUMMY DATA
    val all = remember { DummyData.consultants }
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
            /* ------------------- Search capsule ------------------- */
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .clip(RoundedCornerShape(28.dp))
                        .background(MasakinRed)
                        .padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextField(
                        value = query,
                        onValueChange = { query = it },
                        leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null) },
                        placeholder = { Text("Search", fontSize = 14.sp) },
                        textStyle = LocalTextStyle.current.copy(fontSize = 14.sp),
                        singleLine = true,
                        shape = RoundedCornerShape(22.dp),
                        modifier = Modifier
                            .weight(1f)
                            .height(44.dp),
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
                        items(favorites) { c -> FavoriteCard(c, onClick = { onConsultantClick(c.id) }) }
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
                    onClick = { onConsultantClick(c.id) },
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
                    imageRes = R.drawable.berita1,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                )
            }

            // Spacer tambahan di bawah
            item { Spacer(modifier = Modifier.height(16.dp)) }
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
            fontSize = 10.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.weight(1f)
        )
        if (onSeeAll != null) {
            Text(
                text = "Lihat lainnya ›",
                fontSize = 10.sp,
                color = MasakinRed,
                modifier = Modifier.clickable { onSeeAll() }
            )
        }
    }
}

@Composable
private fun FavoriteCard(c: Consultant, onClick: () -> Unit) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        tonalElevation = Elevation,
        color = Color.White,
        modifier = Modifier
            .width(160.dp)   // Lebar disesuaikan agar proporsional
            .height(160.dp)  // Tinggi ditambah untuk menampung teks di bawah
            .clickable(onClick = onClick)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // --- Bagian Atas: Gambar & Rating ---
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f) // Mengambil sisa ruang (sekitar 60-70% kartu)
            ) {
                Image(
                    painter = painterResource(id = c.favImageRes),
                    contentDescription = "Foto ${c.name}",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                // Badge rating (Pojok Kanan Atas)
                Surface(
                    shape = RoundedCornerShape(8.dp), // Sudut tumpul tapi tidak bulat penuh
                    color = Color.White,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("★", color = StarAmber, fontSize = 12.sp)
                        Spacer(Modifier.width(4.dp))
                        Text(
                            text = String.format("%.1f", c.rating),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    }
                }
            }

            // --- Bagian Bawah: Informasi Teks ---
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(12.dp) // Padding teks
            ) {
                Text(
                    text = c.name,
                    fontSize = 14.sp, // Ukuran teks nama diperjelas
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = c.specialization,
                    fontSize = 11.sp,
                    color = Color.Gray, // Warna abu-abu untuk spesialisasi
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
private fun TopConsultantCard(c: Consultant, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        tonalElevation = Elevation,
        modifier = modifier
            .height(82.dp)
            .clickable(onClick = onClick)
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = c.imageRes),
                contentDescription = "Avatar ${c.name}",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(52.dp)
                    .clip(RoundedCornerShape(12.dp))
            )

            Spacer(Modifier.width(12.dp))

            Column(Modifier.weight(1f)) {
                Text(
                    c.name,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    c.specialization,
                    fontSize = 10.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(Modifier.height(2.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RatingStarsSmall(c.rating)
                    Spacer(Modifier.width(6.dp))
                    Text(
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
private fun NewsCard(
    title: String,
    source: String,
    imageRes: Int,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        tonalElevation = Elevation,
        modifier = modifier
    ) {
        Column {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(132.dp)
            )

            Column(Modifier.padding(12.dp)) {
                Text(title, fontSize = 12.sp, maxLines = 2, overflow = TextOverflow.Ellipsis)
                Text(source, fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewConsultation() {
    ConsultationScreen()
}
