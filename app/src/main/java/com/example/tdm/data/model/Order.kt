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

enum class OrderStatus {
    PLACED,
    CONFIRMED,
    OUT_FOR_DELIVERY,
    DELIVERED,
    COMPLETED
}

fun String.toOrderStatus(): OrderStatus {
    return when (this.lowercase()) {
        "placed" -> OrderStatus.PLACED
        "confirmed" -> OrderStatus.CONFIRMED
        "out" -> OrderStatus.OUT_FOR_DELIVERY
        "delivered" -> OrderStatus.DELIVERED
        "completed" -> OrderStatus.COMPLETED
        else -> throw IllegalArgumentException("Unknown OrderStatus: $this")
    }
}

data class OrderItem(
    val itemId: Int, // ID de l'item
    val quantity: Int, // Quantité
    val notes: String? = null // Notes optionnelles pour l'item
)

data class OrderDataResponse(
    val data: OrderResponse
)
data class AllOrdersDataResponse(
    val data: List<OrderResponse>
)
data class OrderResponse(
    val id: Int, // ID de la commande
    val restaurant: RestaurantDetails, // Restaurant (populated avec le nom)
    val items: List<OrderItem>, // Liste des items
    val totalPrice: Double, // Prix total
    val orderStatus: String, // Statut de la commande
    val documentId: String // Identifiant unique Strapi
)

data class RestaurantDetails(
    val id: Int,
    val name: String, // Nom du restaurant
    val imageUrl: String // URL de l'image du restaurant
)
