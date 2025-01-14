package com.example.tdm.ui.screens.Lists

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.tdm.data.model.Restaurant
import com.example.tdm.ui.components.RestaurantCard


@Composable
fun ScreenNearbyRestaurants(
    navController: NavController,
    nearbyRestaurants: List<Restaurant>) {
    Column (
        modifier = Modifier.padding(16.dp).fillMaxHeight()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding( vertical = 8.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically

        ) {
            // Menu Icon on the left
            Icon(
                imageVector = Icons.Default.ArrowBack, // Replace with your menu icon
                contentDescription = "Go Back",
                modifier = Modifier.size(25.dp).clickable { navController.popBackStack() }
            )
            Text(
                text = "Nearby Restaurants",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(nearbyRestaurants) { restaurant ->
                RestaurantCard(
                    restaurant = restaurant,
                    onCardClick = {
                        navController.currentBackStackEntry?.savedStateHandle?.set("restaurant", restaurant)
                        navController.navigate("restaurantDetails")
                    }
                )
            }
        }

    }
}