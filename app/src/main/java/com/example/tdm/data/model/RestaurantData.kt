package com.example.tdm.data.model

import java.io.Serializable

// Représente la réponse globale de Strapi
data class StrapiRestaurantResponse(
    val data: List<RestaurantData>, // Liste des restaurants
    val meta: Meta // Métadonnées comme la pagination
)

// Représente un restaurant dans la liste "data"
data class RestaurantData(
    val id: Int, // ID du restaurant
    val documentId: String, // Identifiant unique Strapi
    val name: String,
    val address: String,
    val imageUrl: String,
    val distance: String,
    val rating: Float,
    val reviewCount: Int,
    val contact: String,
    val email: String,
    val menu: List<MenuItemData>
) : Serializable

// Représente un item de menu
data class MenuItemData(
    val id: Int,
    val name: String,
    val category: String,
    val price: Double
) : Serializable

// Métadonnées (pagination)
data class Meta(
    val pagination: Pagination
)

data class Pagination(
    val page: Int,
    val pageSize: Int,
    val pageCount: Int,
    val total: Int
)
