package com.example.masakin.ui.mart.components.checkout

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.masakin.R

@Composable
fun CheckoutHeader(
    deliveryAddress: String
) {
    Column {
        Spacer(Modifier.height(16.dp))
        Text(
            text = "Alamat pengiriman",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.height(12.dp))
        
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF9FAFB)),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Placeholder icon, ideally use a location icon
                Icon(
                    painter = painterResource(id = R.drawable.mart_ic_cat_daging), 
                    contentDescription = null,
                    modifier = Modifier.size(20.dp),
                    tint = Color(0xFFD32F2F)
                )
                Spacer(Modifier.width(8.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Rumah",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = deliveryAddress,
                        fontSize = 12.sp,
                        color = Color.Gray,
                        maxLines = 2
                    )
                }
                Icon(
                    painter = painterResource(id = R.drawable.mart_ic_cat_daging),
                    contentDescription = "Change",
                    modifier = Modifier.size(20.dp),
                    tint = Color(0xFFD32F2F)
                )
            }
        }
        Spacer(Modifier.height(16.dp))
    }
}
