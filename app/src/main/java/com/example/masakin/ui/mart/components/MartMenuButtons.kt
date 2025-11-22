package com.example.masakin.ui.mart.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.masakin.R

@Composable
fun MartMenuButtons(
    onCartClick: () -> Unit,
    onOrderClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        MenuButton(
            icon = R.drawable.mart_ic_cart,
            label = "Cart",
            onClick = onCartClick
        )

        MenuButton(
            icon = R.drawable.mart_ic_order,
            label = "Order",
            onClick = onOrderClick
        )

        MenuButton(
            icon = R.drawable.mart_ic_favorite,
            label = "Favorite",
            onClick = onFavoriteClick
        )
    }
}

@Composable
private fun MenuButton(
    icon: Int,
    label: String,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable { onClick() }
    ) {
        Image(
            painter = painterResource(icon),
            contentDescription = label,
            modifier = Modifier.size(64.dp)
        )
    }
}
