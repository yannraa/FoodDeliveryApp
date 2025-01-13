package com.example.tdm.data.model

data class SignUpResponse(
    val jwt: String,
    val user: User
)