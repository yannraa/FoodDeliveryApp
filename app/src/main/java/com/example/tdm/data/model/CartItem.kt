package com.example.tdm.data.model

data class CartItem(
    val id: Int, // ID du MenuItem
    val name: String, // Nom de l'article
    val restaurantId: Int, // ID du restaurant associé
    val restaurantName: String, // Nom du restaurant associé
    val price: Double, // Prix de l'article
    var quantity: Int, // Quantité d'articles dans le panier
    val notes: String? = null // Nouvelle propriété pour les notes (facultatif)
)
