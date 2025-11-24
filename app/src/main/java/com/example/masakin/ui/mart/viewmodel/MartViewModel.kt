package com.example.masakin.ui.mart.viewmodel

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.masakin.ui.mart.data.CartItem
import com.example.masakin.ui.mart.data.OrderRepository
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
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MartViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(MartUiState())
    val uiState: StateFlow<MartUiState> = _uiState.asStateFlow()

    init {
        // Initial load
        loadProducts(ProductCategory.DAGING)
        // Load orders from repository
        _uiState.update { it.copy(orders = OrderRepository.getOrders()) }
    }

    // --- Product & Category Logic ---

    fun selectCategory(category: ProductCategory) {
        _uiState.update { it.copy(selectedCategory = category) }
        loadProducts(category)
    }

    private fun loadProducts(category: ProductCategory?) {
        val products = ProductRepository.getProductsByCategory(category)
        _uiState.update { it.copy(products = products) }
    }

    fun loadProductDetail(productId: Int) {
        val product = ProductRepository.getProductById(productId)
        _uiState.update { it.copy(selectedProduct = product) }
    }

    fun updateSearchQuery(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
        if (query.isNotEmpty()) {
            val filtered = ProductRepository.searchProducts(query)
            _uiState.update { it.copy(products = filtered) }
        } else {
            loadProducts(_uiState.value.selectedCategory)
        }
    }

    // --- Cart Logic ---

    fun addToCart(product: Product, quantity: Int = 1) {
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
        calculateCheckoutTotals()
    }

    fun removeFromCart(product: Product) {
        _uiState.update { currentState ->
            currentState.copy(cartItems = currentState.cartItems.filter { it.product.id != product.id })
        }
        calculateCheckoutTotals()
    }

    fun updateCartQuantity(product: Product, newQuantity: Int) {
        if (newQuantity <= 0) {
            removeFromCart(product)
            return
        }
        _uiState.update { currentState ->
            val updatedItems = currentState.cartItems.map {
                if (it.product.id == product.id) {
                    it.copy(quantity = newQuantity)
                } else {
                    it
                }
            }
            currentState.copy(cartItems = updatedItems)
        }
        calculateCheckoutTotals()
    }

    fun toggleCartItemSelection(product: Product) {
        _uiState.update { currentState ->
            val selected = currentState.selectedCartItems.toMutableList()
            if (selected.contains(product.id)) {
                selected.remove(product.id)
            } else {
                selected.add(product.id)
            }
            currentState.copy(selectedCartItems = selected)
        }
        calculateCheckoutTotals()
    }

    fun selectAllCartItems(select: Boolean) {
        _uiState.update { currentState ->
            val selected = if (select) {
                currentState.cartItems.map { it.product.id }
            } else {
                emptyList()
            }
            currentState.copy(selectedCartItems = selected)
        }
        calculateCheckoutTotals()
    }

    // --- Checkout Logic ---

    fun selectShipping(option: ShippingMethod) {
        _uiState.update { it.copy(selectedShipping = option) }
        calculateCheckoutTotals()
    }

    fun selectShippingMethod(option: ShippingMethod) = selectShipping(option)

    fun selectPayment(method: PaymentMethod) {
        _uiState.update { it.copy(selectedPayment = method) }
    }

    fun selectPaymentMethod(method: PaymentMethod) = selectPayment(method)

    private fun calculateCheckoutTotals() {
        _uiState.update { currentState ->
            val selectedItems = currentState.cartItems.filter { currentState.selectedCartItems.contains(it.product.id) }
            val subtotal = selectedItems.sumOf { it.product.price * it.quantity }
            val shippingCost = currentState.selectedShipping?.price ?: 0
            val insurance = if (shippingCost > 0) 5000 else 0
            
            currentState.copy(
                checkoutSubtotal = subtotal,
                checkoutShippingCost = shippingCost,
                checkoutInsurance = insurance
            )
        }
    }
    
    fun getSubtotal(): Int = _uiState.value.checkoutSubtotal
    
    fun getShippingCost(): Int = _uiState.value.checkoutShippingCost
    
    fun getInsuranceCost(): Int = _uiState.value.checkoutInsurance
    
    fun getProtectionCost(): Int = _uiState.value.checkoutProtection
    
    fun getCheckoutTotal(): Int {
        val state = _uiState.value
        return state.checkoutSubtotal + state.checkoutShippingCost + state.checkoutInsurance + state.checkoutProtection
    }

    fun createOrder() {
        val currentState = _uiState.value
        val orderId = "ORD-${System.currentTimeMillis().toString().takeLast(6)}"
        
        val selectedItems = currentState.cartItems.filter { currentState.selectedCartItems.contains(it.product.id) }
        
        if (selectedItems.isEmpty()) return

        val newOrder = Order(
            id = orderId,
            date = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.forLanguageTag("id-ID")).format(Date()),
            status = "Selesai",
            total = getCheckoutTotal(),
            items = selectedItems,
            subtotal = getSubtotal(),
            shippingCost = getShippingCost(),
            insuranceCost = getInsuranceCost(),
            protectionCost = getProtectionCost(),
            paymentMethod = currentState.selectedPayment,
            shippingMethod = currentState.selectedShipping,
            address = currentState.deliveryAddress
        )

        // Save to repository
        OrderRepository.addOrder(newOrder)

        // Update state: clear purchased items, reset selection, update order list
        _uiState.update { state ->
            state.copy(
                orders = OrderRepository.getOrders(),
                cartItems = state.cartItems.filter { !state.selectedCartItems.contains(it.product.id) },
                selectedCartItems = emptyList(),
                lastCreatedOrderId = orderId,
                selectedPayment = null,
                selectedShipping = null,
                checkoutSubtotal = 0,
                checkoutShippingCost = 0
            )
        }
    }

    // --- Location Logic ---

    fun updateDeliveryAddress() {
        // This might be triggered manually or via location update
        // For now, it's handled by requestLocationUpdate
    }

    fun requestLocationUpdate(
        context: Context,
        fusedLocationClient: FusedLocationProviderClient
    ) {
        viewModelScope.launch {
            try {
                // Check if either FINE or COARSE location permission is granted
                val hasFineLocation = ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
                
                val hasCoarseLocation = ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
                
                if (!hasFineLocation && !hasCoarseLocation) {
                    _uiState.update {
                        it.copy(
                            deliveryAddress = "Location permission required",
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
                            deliveryAddress = "Unable to get location",
                            locationError = "Unable to get location. Using default.",
                            isLoadingLocation = false
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        deliveryAddress = "Location error",
                        locationError = "Location error: ${e.message}",
                        isLoadingLocation = false
                    )
                }
            }
        }
    }
}