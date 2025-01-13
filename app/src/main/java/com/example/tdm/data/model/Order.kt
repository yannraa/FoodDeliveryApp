package com.example.tdm.data.model

data class OrderDataRequest(
    val data: OrderRequest
)

data class OrderRequest(
    val restaurant: Int, // ID du restaurant
    val items: List<OrderItem>, // Liste des items commandés
    val totalPrice: Double, // Prix total
    val user: Int,// ID de l'utilisateur (optionnel)
)

data class OrderItem(
    val itemId: Int, // ID de l'item
    val quantity: Int, // Quantité
    val notes: String? = null // Notes optionnelles pour l'item
)

data class OrderDataResponse(
    val data: OrderResponse
)

data class OrderResponse(
    val id: Int, // ID de la commande
    val restaurant: RestaurantDetails, // Restaurant (populated avec le nom)
    val items: List<OrderItem>, // Liste des items
    val totalPrice: Double, // Prix total
    val orderStatus: String // Statut de la commande
)

data class RestaurantDetails(
    val id: Int,
    val name: String // Nom du restaurant
)
