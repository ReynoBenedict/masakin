package com.example.masakin.ui.mart.data

import com.example.masakin.ui.mart.viewmodel.Order
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

object OrderRepository {
    private val _orders = MutableStateFlow<List<Order>>(emptyList())
    val orders: StateFlow<List<Order>> = _orders.asStateFlow()

    fun addOrder(order: Order) {
        _orders.update { currentOrders ->
            currentOrders + order
        }
    }

    fun getOrders(): List<Order> {
        return _orders.value
    }
}
