package com.example.projetv1.Models

import com.example.projetv1.Models.TypeOfMenu

// Class Menu
data class Menu(
    val menuId: Int,
    val menuName: String,
    val menuType : TypeOfMenu,
    // val dishPic : Int,
    val menuPrice : Double,
    val menuRating : Float,
    val menuDescription: String,

    )
