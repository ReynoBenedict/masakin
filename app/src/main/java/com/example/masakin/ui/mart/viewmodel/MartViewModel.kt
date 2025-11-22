package com.example.masakin.ui.mart.viewmodel

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.masakin.ui.mart.data.CartItem
import com.example.masakin.ui.mart.data.Product
import com.example.masakin.ui.mart.data.ProductCategory
import com.example.masakin.ui.mart.data.ProductRepository
import com.example.masakin.ui.mart.utils.getAddressFromLocation
import com.example.masakin.ui.mart.utils.getCurrentLocation
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class MartUiState(
    val selectedCategory: ProductCategory = ProductCategory.DAGING,
    val products: List<Product> = emptyList(),
    val searchQuery: String = "",
    val deliveryAddress: String = "Malang, Jawa Timur",
    val latitude: Double? = null,
    val longitude: Double? = null,
    val isLoadingLocation: Boolean = false,
    val locationError: String? = null,
    val selectedProduct: Product? = null,
    val cartItems: List<CartItem> = emptyList(),
    val selectedCartItems: Set<Int> = emptySet(),
    val selectedShipping: ShippingMethod? = null,
    val selectedPayment: PaymentMethod? = null
)

enum class ShippingMethod(val displayName: String, val price: Int, val estimatedDays: String) {
    REGULAR("Pengiriman Reguler", 10000, "10 - 13 Agustus"),
    PREMIUM("Pengiriman Premium", 20000, "10 - 12 Agustus")
}

enum class PaymentMethod(val displayName: String, val details: String) {
    CREDIT_CARD("Kartu Kredit", "**** 6754"),
    BANK_BCA("Bank BCA", "**2123424343"),
    CASH("Tunai", "")
}

class MartViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(MartUiState())
    val uiState: StateFlow<MartUiState> = _uiState.asStateFlow()

    init {
        loadProducts(ProductCategory.DAGING)
    }

    fun onCategorySelected(category: ProductCategory) {
        _uiState.update { it.copy(selectedCategory = category) }
        loadProducts(category)
    }

    fun onSearchQueryChanged(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
        if (query.isNotEmpty()) {
            val filtered = ProductRepository.searchProducts(query)
            _uiState.update { it.copy(products = filtered) }
        } else {
            loadProducts(_uiState.value.selectedCategory)
        }
    }

    private fun loadProducts(category: ProductCategory) {
        val products = ProductRepository.getProductsByCategory(category)
        _uiState.update { it.copy(products = products) }
    }
    
    fun loadProductDetail(productId: Int) {
        val product = ProductRepository.getProductById(productId)
        _uiState.update { it.copy(selectedProduct = product) }
    }

    fun loadProductById(id: Int) {
        loadProductDetail(id)
    }

    fun addToCart(product: Product, quantity: Int) {
        _uiState.update { currentState ->
            val existingItem = currentState.cartItems.find { it.product.id == product.id }
            val updatedItems = if (existingItem != null) {
                currentState.cartItems.map {
                    if (it.product.id == product.id) {
                        it.copy(quantity = it.quantity + quantity)
                    } else {
                        it
                    }
                }
            } else {
                currentState.cartItems + CartItem(product, quantity)
            }
            currentState.copy(cartItems = updatedItems)
        }
    }

    fun removeFromCart(productId: Int) {
        _uiState.update { currentState ->
            currentState.copy(cartItems = currentState.cartItems.filter { it.product.id != productId })
        }
    }

    fun updateCartQuantity(productId: Int, newQuantity: Int) {
        if (newQuantity <= 0) {
            removeFromCart(productId)
            return
        }
        _uiState.update { currentState ->
            val updatedItems = currentState.cartItems.map {
                if (it.product.id == productId) {
                    it.copy(quantity = newQuantity)
                } else {
                    it
                }
            }
            currentState.copy(cartItems = updatedItems)
        }
    }

    fun toggleCartItemSelection(productId: Int) {
        _uiState.update { currentState ->
            val selected = currentState.selectedCartItems.toMutableSet()
            if (selected.contains(productId)) {
                selected.remove(productId)
            } else {
                selected.add(productId)
            }
            currentState.copy(selectedCartItems = selected)
        }
    }

    fun selectAllCartItems(select: Boolean) {
        _uiState.update { currentState ->
            val selected = if (select) {
                currentState.cartItems.map { it.product.id }.toSet()
            } else {
                emptySet()
            }
            currentState.copy(selectedCartItems = selected)
        }
    }

    fun getCartTotal(): Int {
        return _uiState.value.cartItems.sumOf { it.product.price * it.quantity }
    }

    fun getSelectedCartTotal(): Int {
        return _uiState.value.cartItems
            .filter { _uiState.value.selectedCartItems.contains(it.product.id) }
            .sumOf { it.product.price * it.quantity }
    }

    fun selectShippingMethod(method: ShippingMethod) {
        _uiState.update { it.copy(selectedShipping = method) }
    }

    fun selectPaymentMethod(method: PaymentMethod) {
        _uiState.update { it.copy(selectedPayment = method) }
    }

    fun getShippingCost(): Int {
        return _uiState.value.selectedShipping?.price ?: 0
    }

    fun getProtectionCost(): Int {
        return 5000
    }

    fun getCheckoutTotal(): Int {
        return getSelectedCartTotal() + getShippingCost() + getProtectionCost()
    }

    fun requestLocationUpdate(
        context: Context,
        fusedLocationClient: FusedLocationProviderClient
    ) {
        viewModelScope.launch {
            try {
                if (ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    _uiState.update {
                        it.copy(
                            locationError = "Location permission required",
                            isLoadingLocation = false
                        )
                    }
                    return@launch
                }

                _uiState.update { it.copy(isLoadingLocation = true, locationError = null) }

                val location = getCurrentLocation(fusedLocationClient)

                if (location != null) {
                    val address = getAddressFromLocation(
                        context,
                        location.latitude,
                        location.longitude
                    )

                    _uiState.update {
                        it.copy(
                            deliveryAddress = address,
                            latitude = location.latitude,
                            longitude = location.longitude,
                            isLoadingLocation = false,
                            locationError = null
                        )
                    }
                } else {
                    _uiState.update {
                        it.copy(
                            locationError = "Unable to get location. Using default.",
                            isLoadingLocation = false
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        locationError = "Location error: ${e.message}",
                        isLoadingLocation = false
                    )
                }
            }
        }
    }
}