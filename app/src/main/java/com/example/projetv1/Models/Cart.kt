package com.example.projetv1.Models

import com.example.projetv1.Models.Menu2

// Class Cart

data class Cart(
    val cartId:Int,
    val cartResto : String,
    val cartItems : List<Menu2>,
)
