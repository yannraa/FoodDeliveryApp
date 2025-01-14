package com.example.tdm.ui.screens.splash

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.tdm.R
import com.example.tdm.utils.TokenManager
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {
    // State for animation
    var scale by remember { mutableStateOf(0f) }
    val infiniteTransition = rememberInfiniteTransition()
    val animatedScale = infiniteTransition.animateFloat(
        initialValue = 0.8f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    val context = LocalContext.current

    // Navigate to the next screen after a delay
    LaunchedEffect(key1 = true) {
        val token = TokenManager.getToken(context)
        scale = 1f
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

    // UI Content
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background) // Background color from theme
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Logo with animation
            Box(
                modifier = Modifier
                    .size(150.dp)
                    .scale(animatedScale.value),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_food_logo), // Your logo
                    contentDescription = "App Logo",
                    modifier = Modifier
                        .size(120.dp)
                        .background(Color.White, shape = CircleShape)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // App name or loading text
            androidx.compose.material3.Text(
                text = "Welcome to Foodash",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}
