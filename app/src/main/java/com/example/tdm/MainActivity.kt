package com.example.tdm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.rememberNavController
import com.example.tdm.data.network.ApiService
import com.example.tdm.data.network.RetrofitInstance
import com.example.tdm.data.repository.*
import com.example.tdm.navigation.AppNavigation
import com.example.tdm.ui.components.BottomNavigationBar
import com.example.tdm.ui.theme.TDMTheme
import com.example.tdm.utils.TokenManager

class MainActivity : ComponentActivity() {
    private val apiService by lazy { RetrofitInstance.api }
    private val restaurantRepository by lazy { RestaurantRepository(
        api = apiService
    ) }
    private val authRepository by lazy { AuthRepository(
        api = apiService
    ) }
    private val orderRepository by lazy { OrderRepository(
        apiService = apiService
    ) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TDMTheme {
                val navController = rememberNavController()

                // Vérifier le token au démarrage
                LaunchedEffect(Unit) {
                    val token = TokenManager.getToken(applicationContext)
                    if (token.isNullOrEmpty()) {
                        navController.navigate("login") {
                            popUpTo(0) // Supprime toutes les destinations précédentes
                        }
                    } else {
                        navController.navigate("home") {
                            popUpTo(0)
                        }
                    }
                }

                Scaffold(
                    bottomBar = {
                        BottomNavigationBar(navController = navController)
                    }
                ) { paddingValues ->
                    AppNavigation(
                        navController = navController,
                        authRepository = authRepository,
                        restaurantRepository = restaurantRepository,
                        orderRepository = orderRepository,
                        modifier = Modifier.padding(paddingValues).background(color = Color(0xffECECE3))

                    )
                }
            }
        }
    }
}