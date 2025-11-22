package com.example.masakin.ui.mart.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.masakin.ui.mart.data.NutritionInfo
import com.example.masakin.ui.mart.data.RelatedRecipe

@Composable
fun QuantityCounter(
    quantity: Int,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        IconButton(
            onClick = onDecrease,
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(Color(0xFFF3F4F6))
        ) {
            Icon(
                imageVector = Icons.Default.Remove,
                contentDescription = "Decrease",
                tint = Color.Black,
                modifier = Modifier.size(16.dp)
            )
        }

        Text(
            text = quantity.toString(),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        IconButton(
            onClick = onIncrease,
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(Color(0xFFF3F4F6))
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Increase",
                tint = Color.Black,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}

@Composable
fun NutrisiInfoRow(nutrition: NutritionInfo) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        NutritionItem(label = "Calories", value = "${nutrition.calories}", unit = "kcal", icon = "🔥")
        NutritionItem(label = "Water", value = "${nutrition.water}", unit = "g", icon = "💧")
        NutritionItem(label = "Protein", value = "${nutrition.protein}", unit = "g", icon = "🥩")
        NutritionItem(label = "Carbs", value = "${nutrition.carbs}", unit = "g", icon = "🍞")
        NutritionItem(label = "Fiber", value = "${nutrition.fiber}", unit = "g", icon = "🥦")
        NutritionItem(label = "Fat", value = "${nutrition.fat}", unit = "g", icon = "🧀")
    }
}

@Composable
fun NutritionItem(label: String, value: String, unit: String, icon: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = label, fontSize = 12.sp, color = Color.Gray, fontWeight = FontWeight.Medium)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = "$icon $value$unit", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF111827))
    }
}

@Composable
fun RelatedRecipeCard(recipe: RelatedRecipe, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .width(160.dp)
            .height(200.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column {
            Image(
                painter = painterResource(id = recipe.image),
                contentDescription = recipe.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                contentScale = ContentScale.Crop
            )
            Column(modifier = Modifier.padding(8.dp)) {
                Text(
                    text = recipe.title,
                    maxLines = 2,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 16.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    // Placeholder avatar
                    Box(
                        modifier = Modifier
                            .size(16.dp)
                            .clip(CircleShape)
                            .background(Color.Gray)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = recipe.chef,
                        fontSize = 10.sp,
                        color = Color.Gray,
                        maxLines = 1
                    )
                }
            }
        }
    }
}

@Composable
fun BottomCTAButton(
    text: String,
    onClick: () -> Unit,
    price: String? = null,
    enabled: Boolean = true
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFD32F2F), // Red color from Figma
            disabledContainerColor = Color(0xFFFFCDD2)
        ),
        enabled = enabled
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = if (price != null) Arrangement.SpaceBetween else Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = text,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            if (price != null) {
                Text(
                    text = price,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun CustomSearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "Search"
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp),
        shape = RoundedCornerShape(24.dp), // More rounded
        color = Color(0xFFF3F4F6),
        shadowElevation = 0.dp
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search",
                tint = Color.Gray
            )
            Spacer(modifier = Modifier.width(8.dp))
            BasicTextField(
                value = query,
                onValueChange = onQueryChange,
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                decorationBox = { innerTextField ->
                    if (query.isEmpty()) {
                        Text(
                            text = placeholder,
                            color = Color.Gray,
                            fontSize = 14.sp
                        )
                    }
                    innerTextField()
                }
            )
        }
    }
}
