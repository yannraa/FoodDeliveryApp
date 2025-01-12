package com.example.projetv1.Models


// All Routes used
sealed class Destination(val route: String) {

    // Start
    object Home : Destination("home")

    // Search results
    object Search : Destination("search_results/{query}") {
        fun getDestination(query: String) = "search_results/$query"
    }

    // View all for popular restaurants
    object PopularRestaurants : Destination("popular_restaurants")

    // View all for nearby restaurants
    object NearbyRestaurants : Destination("nearby_restaurants")

    // Selecting a restaurant
    object RestaurantDetails : Destination("restaurant_details/{id}") {
        fun getDestination(id: Int) = "restaurant_details/$id"
    }

    // Selecting details about a menu
    object MenuDetails : Destination("menu_details/{restaurantId}/{menuId}") {
        fun getDestination(restaurantId: Int, menuId: Int) = "menu_details/$restaurantId/$menuId"
    }

    // Selecting card menu
    object CardDetails : Destination("card_details")
}
