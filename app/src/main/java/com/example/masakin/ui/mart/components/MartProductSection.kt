package com.example.masakin.ui.mart.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.masakin.ui.mart.data.Product
import com.example.masakin.ui.mart.data.ProductCategory

fun LazyListScope.MartProductSection(
    searchQuery: String,
    products: List<Product>,
    groupedProducts: Map<ProductCategory, List<Product>>,
    onProductClick: (Int) -> Unit,
    onAddToCart: (Product) -> Unit,
    onCategoryPositionCalculated: (ProductCategory, Int) -> Unit = { _, _ -> }
) {
    if (searchQuery.isNotEmpty()) {
        // Search Results
        items(products.chunked(2)) { rowProducts ->
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                rowProducts.forEach { product ->
                    Box(modifier = Modifier.weight(1f)) {
                        ProductCard(
                            product = product,
                            onClick = { id -> onProductClick(id) },
                            onAddToCart = { onAddToCart(product) }
                        )
                    }
                }
                if (rowProducts.size == 1) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
            Spacer(modifier = Modifier.height(14.dp))
        }
    } else {
        // Category Sections with position tracking
        ProductCategory.entries.forEachIndexed { categoryIndex, category ->
            val categoryProducts = groupedProducts[category] ?: emptyList()
            if (categoryProducts.isNotEmpty()) {
                item(key = "category_$categoryIndex") {
                    // Calculate and report position
                    onCategoryPositionCalculated(category, categoryIndex + 1)
                    
                    Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                        Text(
                            text = category.name.lowercase().replaceFirstChar { it.uppercase() },
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = Color(0xFF111827),
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                        
                        val chunkedProducts = categoryProducts.chunked(2)
                        chunkedProducts.forEach { rowProducts ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                rowProducts.forEach { product ->
                                    Box(modifier = Modifier.weight(1f)) {
                                        ProductCard(
                                            product = product,
                                            onClick = { id -> onProductClick(id) },
                                            onAddToCart = { onAddToCart(product) }
                                        )
                                    }
                                }
                                if (rowProducts.size == 1) {
                                    Spacer(modifier = Modifier.weight(1f))
                                }
                            }
                            Spacer(modifier = Modifier.height(14.dp))
                        }
                    }
                }
            }
        }
    }
}
