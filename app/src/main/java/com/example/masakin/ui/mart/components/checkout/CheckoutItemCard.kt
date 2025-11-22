package com.example.masakin.ui.mart.components.checkout

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.masakin.R
import com.example.masakin.ui.mart.data.CartItem
import com.example.masakin.ui.mart.utils.CurrencyFormatter
import com.example.masakin.ui.mart.viewmodel.ShippingMethod

@Composable
fun CheckoutItemCard(
    cartItem: CartItem,
    selectedShipping: ShippingMethod?,
    onSelectShipping: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            // Store header
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.mart_ic_cat_daging),
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

            // Product row
            Row {
                Image(
                    painter = painterResource(id = cartItem.product.image),
                    contentDescription = cartItem.product.name,
                    modifier = Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )

                Spacer(Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = cartItem.product.name,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = "${cartItem.quantity}x ${CurrencyFormatter.formatRupiah(cartItem.product.price)}",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }

                // Red corner
                Box(
                    modifier = Modifier
                        .width(40.dp)
                        .height(40.dp)
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

            Spacer(Modifier.height(12.dp))

            // Shipping selection button
            if (selectedShipping != null) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(onClick = onSelectShipping),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF3F4F6)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(
                            text = "Bebas Ongkir (Rp${selectedShipping.price})",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Estimasi tiba ${selectedShipping.estimatedDays}",
                            fontSize = 11.sp,
                            color = Color.Gray
                        )
                        Spacer(Modifier.height(4.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                painter = painterResource(id = R.drawable.mart_ic_cat_daging),
                                contentDescription = null,
                                modifier = Modifier.size(16.dp),
                                tint = Color(0xFFD32F2F)
                            )
                            Spacer(Modifier.width(4.dp))
                            Text(
                                text = "Dapatkan asuransi pengiriman Rp 5.000",
                                fontSize = 11.sp,
                                color = Color.Gray
                            )
                        }
                    }
                }
            } else {
                OutlinedButton(
                    onClick = onSelectShipping,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = Color(0xFFD32F2F)
                    ),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFD32F2F))
                ) {
                    Text("Pilih Pengiriman")
                    Spacer(Modifier.weight(1f))
                    Icon(
                        painter = painterResource(id = R.drawable.mart_ic_cat_daging),
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}
