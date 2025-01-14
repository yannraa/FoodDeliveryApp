package com.example.tdm.ui.components

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.compose.ui.res.painterResource
import com.example.tdm.R

@Composable
fun BottomNavigationBar(navController: NavController) {
    // Liste des éléments de la barre de navigation
    val items = listOf(
        BottomNavItem("Home", "home", R.drawable.ic_home),
        BottomNavItem("Order", "order", R.drawable.ic_order),
    )

    // Barre de navigation
    BottomNavigation(
        backgroundColor = MaterialTheme.colorScheme.background, // Couleur de fond de la navbar
        contentColor = MaterialTheme.colorScheme.onBackground   // Couleur des icônes et du texte
    ) {
        val currentRoute = navController.currentBackStackEntryAsState()?.value?.destination?.route
        items.forEach { item ->
            BottomNavigationItem(
                icon = { Icon(painterResource(item.icon), contentDescription = item.title) },
                label = { Text(text = item.title) },
                selected = currentRoute == item.route,
                onClick = {
                    if (currentRoute != item.route) {
                        navController.navigate(item.route)
                    }
                }
            )
        }
    }
}

// Déclaration de la classe BottomNavItem
data class BottomNavItem(
    val title: String,
    val route: String,
    val icon: Int
)
