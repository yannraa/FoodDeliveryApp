package com.example.tdm.ui.screens.Lists

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.tdm.data.model.Restaurant
import com.example.tdm.ui.components.RestaurantCard

@Composable
fun SearchScreen(
    navController: NavController,
    nearbyRestaurants: List<Restaurant>
) {
    var searchQuery by remember { mutableStateOf("") }
    val filteredRestaurants = remember(searchQuery, nearbyRestaurants) {
        nearbyRestaurants.filter { restaurant ->
            restaurant.name.contains(searchQuery, ignoreCase = true) ||
                    restaurant.address.contains(searchQuery, ignoreCase = true)
        }
    }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxHeight()
    ) {
        // Header Row with Back Button and Title
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Go Back",
                modifier = Modifier
                    .size(25.dp)
                    .clickable { navController.popBackStack() }
            )
            Text(
                text = "Nearby Restaurants",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Search Bar
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = { Text("Search restaurants...") },
            modifier = Modifier
                .fillMaxWidth(),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                cursorColor = MaterialTheme.colorScheme.primary
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // List of Restaurants
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(filteredRestaurants) { restaurant ->
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
