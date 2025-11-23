package com.example.masakin.ui.recipe

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Aksen merah default (bisa di-override dari Screen)
val RecipeRed = Color(0xFFE53935)

/* ----------------------------- Search Bar ----------------------------- */
@Composable
fun RecipeSearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    TextField(
        value = query,
        onValueChange = onQueryChange,
        leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "Cari") },
        placeholder = { Text("Search", fontSize = MaterialTheme.typography.labelMedium.fontSize) },
        singleLine = true,
        textStyle = LocalTextStyle.current.copy(fontSize = 12.sp),
        shape = RoundedCornerShape(24.dp),
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color(0xFFF2F2F2),
            focusedContainerColor = Color(0xFFF2F2F2),
            disabledContainerColor = Color(0xFFF2F2F2),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
    )
}

/* --------------------------- Featured Carousel --------------------------- */
@Composable
fun FeaturedCarousel(
    items: List<Recipe>,
    state: LazyListState,
    modifier: Modifier = Modifier
) {
    val config = LocalConfiguration.current
    val screenWidth = config.screenWidthDp.dp
    val horizontalPadding = 16.dp
    val cardWidth = screenWidth - (horizontalPadding * 2) + 1.dp

    LazyRow(
        state = state,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(horizontal = horizontalPadding),
        modifier = modifier
    ) {
        items(items) { r ->
            FeaturedCard(recipe = r, cardWidth = cardWidth)
        }
    }
}

@Composable
fun FeaturedCard(
    recipe: Recipe,
    cardWidth: Dp,
) {
    Surface(
        tonalElevation = 2.dp,
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .width(cardWidth)
            .height(200.dp)
    ) {
        Column {
            Box {
                Image(
                    painter = painterResource(id = recipe.imageRes),
                    contentDescription = recipe.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                )
                RatingPill(
                    text = "${recipe.rating}★",
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(8.dp)
                )
            }
            Column(Modifier.padding(12.dp)) {
                Text(
                    recipe.name,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = MaterialTheme.typography.bodySmall.fontSize,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    "${recipe.servings} Menit",
                    color = Color(0xFF9E9E9E),
                    fontSize = MaterialTheme.typography.labelSmall.fontSize
                )
            }
        }
    }
}

@Composable
private fun RatingPill(text: String, modifier: Modifier = Modifier) {
    Surface(
        color = Color(0x99000000),
        shape = RoundedCornerShape(10.dp),
        modifier = modifier
    ) {
        Text(
            text = text,
            color = Color.White,
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}

/* ------------------------------ Dots ----------------------------------- */
@Composable
fun DotsIndicator(
    listSize: Int,
    selectedIndex: Int,
    accentRed: Color = RecipeRed,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = modifier.fillMaxWidth()
    ) {
        repeat(listSize) { idx ->
            val w = if (idx == selectedIndex) 16.dp else 6.dp
            val h = 6.dp
            Box(
                Modifier
                    .padding(4.dp)
                    .width(w)
                    .height(h)
                    .clip(RoundedCornerShape(50))
                    .background(if (idx == selectedIndex) accentRed else Color(0xFFDDDDDD))
            )
        }
    }
}

/* --------------------------- Category Section --------------------------- */
@Composable
fun CategorySection(
    title: String,
    recipes: List<Recipe>,
    onSeeMore: () -> Unit,
    accentRed: Color = RecipeRed
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(Modifier.weight(1f)) // dorong ke kanan

            Text(
                text = "Lihat lainnya",
                color = accentRed,
                style = MaterialTheme.typography.labelMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .clickable(onClick = onSeeMore)
                    .padding(start = 8.dp)
            )
        }
        Spacer(Modifier.height(8.dp))
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(recipes) { r -> SmallRecipeCard(r) }
        }
    }
}

@Composable
fun SmallRecipeCard(recipe: Recipe) {
    Surface(
        shape = RoundedCornerShape(14.dp),
        tonalElevation = 1.dp,
        modifier = Modifier.width(180.dp)
    ) {
        Column {
            Image(
                painter = painterResource(id = recipe.imageRes),
                contentDescription = recipe.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
            )
            Column(Modifier.padding(10.dp)) {
                Text(
                    recipe.name,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.Medium,
                    fontSize = MaterialTheme.typography.labelMedium.fontSize
                )
                Spacer(Modifier.height(6.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("★ ${recipe.rating}", color = Color(0xFF666666), style = MaterialTheme.typography.labelSmall)
                    Text("${recipe.servings} mnt", color = Color(0xFF888888), style = MaterialTheme.typography.labelSmall)
                }
            }
        }
    }
}
