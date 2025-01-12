package com.example.projetv1.Pages

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import com.example.projetv1.Components.InLine_RestaurantsCard
import com.example.projetv1.Data.restaurantList


@Composable
fun ScreenPopularRestaurants(navController: NavController) {
    Column (
        modifier = Modifier.padding(16.dp)
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
                text = "Popular Restaurants",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn (verticalArrangement = Arrangement.spacedBy(16.dp)) {
            items(restaurantList) { restaurant ->
                InLine_RestaurantsCard(restaurant,navController)
            }
        }

    }
}