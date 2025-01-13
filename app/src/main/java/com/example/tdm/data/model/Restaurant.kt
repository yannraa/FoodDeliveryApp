package com.example.tdm.data.model
import java.io.Serializable


data class MenuItem(
    val id: Int,
    val name: String,
    val category: String, // Catégorie à laquelle appartient l'item
    val price: Double
) : Serializable

data class Restaurant(
    val id: Int,
    val name: String,
    val address: String,
    val imageUrl: String, // URL de l'image
    val distance: String,
    val rating: Float,
    val reviewCount: Int,
    val contact: String, // Numéro de contact
    val email: String, // Email du restaurant
    val menu: List<MenuItem> // Liste des items du menu
) : Serializable