package com.example.masakin.ui.mart.viewmodel

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.masakin.ui.mart.data.Product
import com.example.masakin.ui.mart.data.ProductCategory
import com.example.masakin.ui.mart.data.ProductRepository
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.Locale

data class MartUiState(
    val selectedCategory: ProductCategory = ProductCategory.DAGING,
    val products: List<Product> = emptyList(),
    val searchQuery: String = "",
    val deliveryAddress: String = "Malang, Jawa Timur",
    val isLoadingLocation: Boolean = false,
    val locationError: String? = null
)

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

    fun requestLocationUpdate(
        context: Context,
        fusedLocationClient: FusedLocationProviderClient
    ) {
        viewModelScope.launch {
            try {
                // Check permission
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

                // Use getCurrentLocation instead of lastLocation
                val location = com.example.masakin.ui.mart.utils.getCurrentLocation(fusedLocationClient)

                if (location != null) {
                    val address = com.example.masakin.ui.mart.utils.getAddressFromLocation(
                        context,
                        location.latitude,
                        location.longitude
                    )

                    _uiState.update {
                        it.copy(
                            deliveryAddress = address,
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