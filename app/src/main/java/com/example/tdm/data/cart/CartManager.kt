package com.example.tdm.data.cart

import com.example.tdm.data.model.CartItem

object CartManager {
    private val cartItems = mutableListOf<CartItem>()
    private var currentRestaurant: String? = null

    fun getCartItems(): List<CartItem> = cartItems

    fun addItemToCart(item: CartItem): Boolean {
        // Si le panier est vide, on initialise le restaurant
        if (cartItems.isEmpty()) {
            currentRestaurant = item.restaurantName
        }

        // Vérifie si l'article appartient au même restaurant
        if (currentRestaurant != item.restaurantName) {
            return false // Retourne "false" si le restaurant ne correspond pas
        }

        // Ajoute ou met à jour l'article
        val existingItem = cartItems.find { it.id == item.id }
        if (existingItem != null) {
            existingItem.quantity += item.quantity
        } else {
            cartItems.add(item)
        }
        return true
    }

    fun removeItemFromCart(itemId: Int) {
        cartItems.removeAll { it.id == itemId }
        if (cartItems.isEmpty()) {
            currentRestaurant = null // Réinitialise le restaurant si le panier est vide
        }
    }

    fun updateQuantity(itemId: Int, newQuantity: Int) {
        val item = cartItems.find { it.id == itemId }
        item?.let {
            if (newQuantity > 0) {
                it.quantity = newQuantity
            } else {
                cartItems.remove(it)
                if (cartItems.isEmpty()) {
                    currentRestaurant = null
                }
            }
        }
    }

    fun calculateTotalCost(): Double {
        return cartItems.sumOf { it.price * it.quantity }
    }

    fun clearCart() {
        cartItems.clear()
        currentRestaurant = null
    }
}
