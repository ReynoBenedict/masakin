package com.example.masakin.ui.mart.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.masakin.R

@Composable
fun MartHeader(
    deliveryAddress: String,
    searchText: String,
    isLoadingLocation: Boolean = false,
    onSearchChange: (String) -> Unit,
    onLocationClick: () -> Unit,
    onNotificationClick: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {

        // 🔹 Tambahan Back Button (HANYA DITAMBAHKAN)
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {

            IconButton(onClick = { onBackClick() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color(0xFFEF4444)
                )
            }

            // Location + Notification (code existing, tidak diubah)
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .weight(1f)
                    .clickable { onLocationClick() }
            ) {
//                Image(
//                    painter = painterResource(R.drawable.mart_ic_location_red),
//                    contentDescription = "Location",
//                    modifier = Modifier.size(20.dp)
//                )

                Spacer(Modifier.width(8.dp))

                Column {
                    Text(
                        text = "Delivery address",
                        fontSize = 11.sp,
                        color = Color(0xFF6B7280),
                        fontWeight = FontWeight.Normal
                    )

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = deliveryAddress,
                            color = Color(0xFFEF4444),
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 13.sp
                        )

                        if (isLoadingLocation) {
                            Spacer(Modifier.width(6.dp))
                            CircularProgressIndicator(
                                modifier = Modifier.size(12.dp),
                                strokeWidth = 1.5.dp,
                                color = Color(0xFFEF4444)
                            )
                        }
                    }
                }
            }

            Image(
                painter = painterResource(R.drawable.mart_ic_notification),
                contentDescription = "Notification",
                modifier = Modifier
                    .size(24.dp)
                    .clickable { onNotificationClick() }
            )
        }

        Spacer(Modifier.height(14.dp))

        // Search Bar (existing code — tidak diubah)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(44.dp)
                .border(1.dp, Color(0xFFE5E7EB), RoundedCornerShape(12.dp))
                .clip(RoundedCornerShape(12.dp))
                .padding(horizontal = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search",
                modifier = Modifier.size(20.dp),
                tint = Color(0xFF9CA3AF)
            )

            Spacer(Modifier.width(10.dp))

            BasicTextField(
                value = searchText,
                onValueChange = onSearchChange,
                singleLine = true,
                textStyle = LocalTextStyle.current.copy(
                    fontSize = 14.sp,
                    color = Color(0xFF111827)
                ),
                decorationBox = { innerTextField ->
                    if (searchText.isEmpty()) {
                        Text(
                            text = "Cari produk...",
                            color = Color(0xFF9CA3AF),
                            fontSize = 14.sp
                        )
                    }
                    innerTextField()
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
