package com.example.tdm.data.repository

import com.example.tdm.data.model.Restaurant
import com.example.tdm.data.model.MenuItem
import com.example.tdm.data.network.ApiService

class RestaurantRepository(private val api: ApiService) {

    suspend fun getRestaurants(): List<Restaurant> {
        val response = api.getRestaurants()

        // Vérifie si "data" est nul ou vide
        val restaurantDataList = response.data ?: return emptyList()

        // Mappe chaque "RestaurantData" en "Restaurant" en gérant les champs nullables
        return restaurantDataList.mapNotNull { restaurantData ->
            try {
                Restaurant(
                    id = restaurantData.id,
                    name = restaurantData.name ?: "Unknown",
                    address = restaurantData.address ?: "No address available",
                    imageUrl = restaurantData.imageUrl ?: "",
                    distance = restaurantData.distance ?: "Unknown distance",
                    rating = restaurantData.rating ?: 0.0f,
                    reviewCount = restaurantData.reviewCount ?: 0,
                    contact = restaurantData.contact ?: "No contact",
                    email = restaurantData.email ?: "No email",
                    menu = restaurantData.menu?.mapNotNull { menuItemData ->
                        try {
                            MenuItem(
                                id = menuItemData.id,
                                name = menuItemData.name ?: "Unnamed item",
                                category = menuItemData.category ?: "Uncategorized",
                                price = menuItemData.price ?: 0.0
                            )
                        } catch (e: Exception) {
                            null // Ignore les erreurs individuelles des items de menu
                        }
                    } ?: emptyList() // Utilise une liste vide si "menu" est nul
                )
            } catch (e: Exception) {
                null // Ignore les erreurs individuelles des restaurants
            }
        }
    }
}
