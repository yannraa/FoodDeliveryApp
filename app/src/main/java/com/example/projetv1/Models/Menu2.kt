package com.example.projetv1.Models

// Les Menu Contenu dans Les Paniers ( Each menu has a quantity)
data class Menu2(
    val menuId: Int,
    val menuName: String,
    var menuQtt : Int,
    val menuPrice : Double,
)