package com.example.projetv1.Models

import com.example.projetv1.Models.Menu

// Class restaurant

data class Restaurant(
    val restId: Int,                        // id
    val restIsOpen: Boolean,
    val restName: String,                   // Nom du restaurant
    val restImg: Int,                // URL du logo (string) ou image drawable (Int)
    val restAddress: String,                // Adresse du restaurant
//    val latLng: Pair<Double, Double>,   // Coordonnées GPS (latitude, longitude)
    val restType: String,            // Type de cuisine (algérienne, japonaise, etc.)
    val restRating: Float,           // Note moyenne (ex. 4.5 sur 5)
    val restPhone: String,                  // Numéro de téléphone
    val restEmail: String, // @ mail
//    val restReviews : List<Review>,
    val restReviewCount: Int,
    val restDistance : String,
    val restMenuList: List<Menu>
)