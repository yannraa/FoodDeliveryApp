package com.example.tdm.ui.screens.orders

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.tdm.data.model.OrderResponse
import com.example.tdm.data.repository.OrderRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class OrderViewModel(private val repository: OrderRepository) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _orders = MutableStateFlow<List<OrderResponse>>(emptyList())
    val orders: StateFlow<List<OrderResponse>> = _orders

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun fetchOrders(userId: Int) { // Prend en paramètre l'ID de l'utilisateur
        _isLoading.value = true
        _error.value = null

        Log.d("OrderViewModel", "Fetching orders for user $userId")
        viewModelScope.launch {
            try {
                val fetchedOrders = repository.getOrders(userId)
                _orders.value = fetchedOrders
                Log.d("OrderViewModel", "Fetched ${fetchedOrders.size} orders")
            } catch (e: Exception) {
                _error.value = "An error occurred: ${e.message}"
                Log.e("OrderViewModel", "Error fetching orders", e)
            } finally {
                _isLoading.value = false
            }
        }
    }
}

class OrderViewModelFactory(private val repository: OrderRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OrderViewModel::class.java)) {
            return OrderViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
