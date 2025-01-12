package com.example.frontendtdm.app

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.frontendtdm.navigation.Destination
import com.example.frontendtdm.screens.*

@Composable
fun MyApp() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Destination.Splash.route) {
        composable(Destination.Splash.route) { SplashScreen(navController) }
        composable(Destination.Login.route) { LoginScreen(navController) }
        composable(Destination.SignUp.route) { SignUpScreen(navController) }
        composable(Destination.Home.route) { HomeScreen(navController) }
        composable(Destination.Sidebar.route) { Sidebar(navController) }
    }
}