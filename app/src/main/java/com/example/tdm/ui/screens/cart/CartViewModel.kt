package com.example.tdm.ui.screens.cart

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.tdm.data.cart.CartManager
import com.example.tdm.data.model.CartItem
import com.example.tdm.data.model.OrderItem
import com.example.tdm.data.model.OrderRequest
import com.example.tdm.data.model.OrderResponse
import com.example.tdm.data.repository.AuthRepository
import com.example.tdm.data.repository.OrderRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CartViewModel(private val repository: OrderRepository) : ViewModel() {

    private val _cartItems = MutableStateFlow(CartManager.getCartItems())
    val cartItems: StateFlow<List<CartItem>> = _cartItems

    private val _totalCost = MutableStateFlow(CartManager.calculateTotalCost())
    val totalCost: StateFlow<Double> = _totalCost

    private val _restaurantConflict = MutableStateFlow(false)
    val restaurantConflict: StateFlow<Boolean> = _restaurantConflict

    private val _orderResponse = MutableStateFlow<OrderResponse?>(null)
    val orderResponse: StateFlow<OrderResponse?> = _orderResponse

    private val _orderError = MutableStateFlow<String?>(null)
    val orderError: StateFlow<String?> = _orderError

    private val _isLoading = MutableStateFlow(false) // Ajout de l'état de chargement
    val isLoading: StateFlow<Boolean> = _isLoading

    fun placeOrder(restaurantId: Int, items: List<CartItem>, totalPrice: Double) {
        viewModelScope.launch {
            Log.d("CartViewModel", "Order button clicked")

            val orderItems = items.map { cartItem ->
                OrderItem(
                    itemId = cartItem.id,
                    quantity = cartItem.quantity,
                    notes = cartItem.notes
                )
            }

            val orderRequest = OrderRequest(
                restaurant = restaurantId,
                items = orderItems,
                totalPrice = totalPrice,
                user = 2
            )

            _isLoading.value = true // Début du chargement

            repository.createOrder(orderRequest)
                .onSuccess { response ->
                    _orderResponse.value = response
                    _orderError.value = null
                    Log.d("CartViewModel", "Order created successfully")
                }
                .onFailure { exception ->
                    _orderResponse.value = null
                    _orderError.value = exception.message
                    Log.e("CartViewModel", "Order creation failed: ${exception.localizedMessage} ")
                }

            _isLoading.value = false
        }
    }

    fun addItemToCart(item: CartItem) {
        val success = CartManager.addItemToCart(item)
        if (!success) {
            _restaurantConflict.value = true // Déclenche un conflit
        } else {
            refreshCart()
        }
    }

    fun clearConflict() {
        _restaurantConflict.value = false
    }

    fun clearCartAndAddItem(item: CartItem) {
        CartManager.clearCart()
        CartManager.addItemToCart(item)
        refreshCart()
        clearConflict()
    }

    fun removeItemFromCart(itemId: Int) {
        CartManager.removeItemFromCart(itemId)
        refreshCart()
    }

    fun updateQuantity(itemId: Int, newQuantity: Int) {
        CartManager.updateQuantity(itemId, newQuantity)
        refreshCart()
    }

    private fun refreshCart() {
        _cartItems.value = CartManager.getCartItems()
        _totalCost.value = CartManager.calculateTotalCost()
    }
}


class CartViewModelFactory(private val repository: OrderRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CartViewModel::class.java)) {
            return CartViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}