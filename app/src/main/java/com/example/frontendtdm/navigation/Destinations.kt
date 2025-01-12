package com.example.frontendtdm.navigation

sealed class Destination(val route: String) {
    object Splash : Destination("splash")
    object Login : Destination("login")
    object SignUp : Destination("signup")
    object Home : Destination("home")
    object Sidebar : Destination("sidebar")
}