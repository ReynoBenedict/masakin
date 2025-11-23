package com.example.masakin.ui.mart.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.FavoriteBorder
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
import com.example.masakin.ui.mart.data.CartItem
import com.example.masakin.ui.mart.utils.CurrencyFormatter

@Composable
fun CartItemCard(
    cartItem: CartItem,
    isSelected: Boolean,
    onSelectionChange: (Boolean) -> Unit,
    onQuantityChange: (Int) -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            // Store header with checkbox
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, top = 8.dp, end = 12.dp, bottom = 0.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Checkbox
                Checkbox(
                    checked = isSelected,
                    onCheckedChange = onSelectionChange,
                    colors = CheckboxDefaults.colors(
                        checkedColor = Color(0xFFD32F2F),
                        uncheckedColor = Color.Gray
                    ),
                    modifier = Modifier.size(24.dp)
                )

                Spacer(Modifier.width(4.dp))

                // Store badge
                Icon(
                    painter = painterResource(id = com.example.masakin.R.drawable.mart_ic_cat_daging),
                    contentDescription = null,
                    modifier = Modifier.size(14.dp),
                    tint = Color(0xFFD32F2F)
                )
                Spacer(Modifier.width(4.dp))
                Text(
                    text = "Selvi's Mart",
                    fontSize = 11.sp,
                    color = Color(0xFF6B7280)
                )
            }

            // Product row with red corner
            Box(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 48.dp, end = 12.dp, top = 4.dp, bottom = 12.dp)
                ) {
                    // Product Image
                    Image(
                        painter = painterResource(id = cartItem.product.image),
                        contentDescription = cartItem.product.name,
                        modifier = Modifier
                            .size(80.dp)
                            .clip(RoundedCornerShape(8.dp)),
                        contentScale = ContentScale.Crop
                    )

                    Spacer(Modifier.width(12.dp))

                    // Product info column
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = cartItem.product.name,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            maxLines = 2,
                            lineHeight = 18.sp
                        )

                        Spacer(Modifier.height(4.dp))

                        // Unit/price
                        Text(
                            text = "${cartItem.quantity}x ${CurrencyFormatter.formatRupiah(cartItem.product.price)}",
                            fontSize = 12.sp,
                            color = Color(0xFF6B7280)
                        )

                        Spacer(Modifier.weight(1f))

                        // Price and quantity controls row
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = CurrencyFormatter.formatRupiah(cartItem.product.price * cartItem.quantity),
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF111827)
                            )

                            // Quantity controls
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                // Decrease button
                                Box(
                                    modifier = Modifier
                                        .size(24.dp)
                                        .border(1.dp, Color(0xFFD32F2F), RoundedCornerShape(4.dp))
                                        .clickable {
                                            if (cartItem.quantity > 1) {
                                                onQuantityChange(cartItem.quantity - 1)
                                            }
                                        },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "−",
                                        color = Color(0xFFD32F2F),
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }

                                // Quantity display
                                Text(
                                    text = cartItem.quantity.toString(),
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Medium,
                                    modifier = Modifier.widthIn(min = 16.dp)
                                )

                                // Increase button
                                Box(
                                    modifier = Modifier
                                        .size(24.dp)
                                        .background(Color(0xFFD32F2F), RoundedCornerShape(4.dp))
                                        .clickable { onQuantityChange(cartItem.quantity + 1) },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Add,
                                        contentDescription = "Increase",
                                        tint = Color.White,
                                        modifier = Modifier.size(14.dp)
                                    )
                                }

                                // Delete button
                                IconButton(
                                    onClick = onDelete,
                                    modifier = Modifier.size(24.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Outlined.Delete,
                                        contentDescription = "Delete",
                                        tint = Color(0xFF6B7280),
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                            }
                        }
                    }
                }

                // Red corner triangle
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .align(Alignment.TopEnd)
                ) {
                    androidx.compose.foundation.Canvas(modifier = Modifier.fillMaxSize()) {
                        val path = androidx.compose.ui.graphics.Path().apply {
                            moveTo(size.width, 0f)
                            lineTo(size.width, size.height)
                            lineTo(0f, 0f)
                            close()
                        }
                        drawPath(path, Color(0xFFD32F2F))
                    }
                }
            }
        }
    }
}
