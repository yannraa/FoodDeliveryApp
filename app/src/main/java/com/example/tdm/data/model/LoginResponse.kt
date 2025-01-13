package com.example.tdm.data.model

data class LoginResponse(
    val jwt: String, // Jeton JWT renvoyé par Strapi
    val user: User
)