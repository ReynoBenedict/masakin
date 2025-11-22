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
import androidx.compose.ui.text.style.TextDecoration
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
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.Top
        ) {
            // Checkbox
            Checkbox(
                checked = isSelected,
                onCheckedChange = onSelectionChange,
                colors = CheckboxDefaults.colors(
                    checkedColor = Color(0xFFD32F2F),
                    uncheckedColor = Color.Gray
                ),
                modifier = Modifier.padding(top = 8.dp)
            )

            Spacer(Modifier.width(8.dp))

            // Store name header (top of card)
            Column(modifier = Modifier.fillMaxWidth()) {
                // Store name
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = com.example.masakin.R.drawable.mart_ic_cat_daging),
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = Color(0xFFD32F2F)
                    )
                    Spacer(Modifier.width(4.dp))
                    Text(
                        text = "Selvi's Mart",
                        fontSize = 12.sp,
                        color = Color(0xFF6B7280)
                    )
                }

                Spacer(Modifier.height(8.dp))

                // Product details row
                Row(modifier = Modifier.fillMaxWidth()) {
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
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                    ) {
                        Text(
                            text = cartItem.product.name,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            maxLines = 2
                        )

                        Spacer(Modifier.height(8.dp))

                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Edit and favorite icons
                            Icon(
                                painter = painterResource(id = com.example.masakin.R.drawable.ic_launcher_foreground),
                                contentDescription = "Edit",
                                modifier = Modifier.size(20.dp),
                                tint = Color.Gray
                            )
                            Spacer(Modifier.width(8.dp))
                            Icon(
                                imageVector = Icons.Outlined.FavoriteBorder,
                                contentDescription = "Favorite",
                                modifier = Modifier.size(20.dp),
                                tint = Color.Gray
                            )
                        }
                    }

                    // Red corner accent
                    Box(
                        modifier = Modifier
                            .width(40.dp)
                            .height(40.dp)
                            .offset(x = 12.dp, y = (-12).dp)
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

                Spacer(Modifier.height(8.dp))

                // Price and quantity controls
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = CurrencyFormatter.formatRupiah(cartItem.product.price),
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
                                .size(28.dp)
                                .border(1.dp, Color(0xFFD32F2F), RoundedCornerShape(6.dp))
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
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        // Quantity display
                        Text(
                            text = cartItem.quantity.toString(),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.widthIn(min = 20.dp)
                        )

                        // Increase button
                        Box(
                            modifier = Modifier
                                .size(28.dp)
                                .background(Color(0xFFD32F2F), RoundedCornerShape(6.dp))
                                .clickable { onQuantityChange(cartItem.quantity + 1) },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Increase",
                                tint = Color.White,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
