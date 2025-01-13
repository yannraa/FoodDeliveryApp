package com.example.tdm.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.tdm.R
import com.example.tdm.data.model.Category
import com.example.tdm.data.model.MenuItem
import com.example.tdm.data.model.Restaurant
import com.example.tdm.data.repository.RestaurantRepository
import com.example.tdm.ui.components.*

@Composable
fun HomeScreen(
    repository: RestaurantRepository,
    navController : NavController,
) {
    val factory = HomeViewModelFactory(repository)
    val viewModel: HomeViewModel = viewModel(factory = factory)

    val isLoading by viewModel.isLoading.collectAsState()
    val restaurants by viewModel.restaurants.collectAsState()
    val error by viewModel.error.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchRestaurants()
    }

    if (isLoading) {
        CircularProgressIndicator(modifier = Modifier.fillMaxSize())
    } else if (error != null) {
        Text(
            text = error ?: "Unknown error",
            color = MaterialTheme.colorScheme.error,
            modifier = Modifier.fillMaxSize().padding(16.dp)
        )
    } else {
        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            // Header Section
            HeaderSection(
                navController = navController
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Search Bar
            SearchBar()

            Spacer(modifier = Modifier.height(16.dp))

            // Categories Section
            CategoriesSection()

            Spacer(modifier = Modifier.height(16.dp))

            // Restaurant Nearby
            SectionTitle(title = "Restaurant Nearby", onViewAllClick = { /* TODO: View all */ })
            RestaurantNearbyList(
                nearbyRestaurants = restaurants,
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Popular Restaurants
            SectionTitle(title = "Popular Restaurant", onViewAllClick = { /* TODO: View all */ })
            PopularRestaurantList(
                popularRestaurants = restaurants,
                navController = navController,
            )
        }
    }
}

@Composable
fun HeaderSection(
    navController: NavController
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = "Rivertown Haven",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            )
            Text(
                text = "Your location",
                style = MaterialTheme.typography.bodySmall.copy(
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.primary
                )
            )
        }

        Row {
            IconButton(onClick = {
                navController.navigate("cart")
            }) {
                Icon(
                    imageVector = Icons.Default.ShoppingCart,
                    contentDescription = "Cart Icon",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
            IconButton(onClick = { /* TODO: Notifications action */ }) {
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = "Notification Icon",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}

@Composable
fun SearchBar() {
    OutlinedTextField(
        value = "",
        onValueChange = { /* TODO: Update search query */ },
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text("Search your food") },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search Icon"
            )
        },
        trailingIcon = {
            IconButton(onClick = { /* TODO: Filter action */ }) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Filter Icon"
                )
            }
        },
        shape = RoundedCornerShape(12.dp)
    )
}

@Composable
fun CategoriesSection() {
    val categories = listOf(
        Category("Meat", R.drawable.ic_food_logo),
        Category("Cold drinks", R.drawable.ic_food_logo),
        Category("Dessert", R.drawable.ic_food_logo),
        Category("Burger", R.drawable.ic_food_logo)
    )

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(horizontal = 8.dp)
    ) {
        items(categories) { category ->
            CategoryItem(name = category.name, iconRes = category.iconRes)
        }
    }
}

@Composable
fun CategoryItem(name: String, iconRes: Int) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Card(
            modifier = Modifier.size(60.dp),
            shape = RoundedCornerShape(10.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Image(
                painter = painterResource(id = iconRes),
                contentDescription = name,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = name, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
fun SectionTitle(title: String, onViewAllClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
        )
        TextButton(onClick = { onViewAllClick() }) {
            Text(text = "View all", color = MaterialTheme.colorScheme.primary)
        }
    }
}

@Composable
fun RestaurantNearbyList(
    nearbyRestaurants: List<Restaurant>
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(horizontal = 8.dp)
    ) {
        items(nearbyRestaurants) { restaurant ->
            RestaurantNearbyCard(restaurant)
        }
    }
}

@Composable
fun RestaurantNearbyCard(restaurant: Restaurant) {
    Card(
        modifier = Modifier.size(width = 150.dp, height = 200.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column {
            Image(
                painter = rememberAsyncImagePainter(restaurant.imageUrl),
                contentDescription = "Restaurant Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier.padding(8.dp)
            ) {
                Text(
                    text = restaurant.name,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1
                )
                Text(
                    text = "${restaurant.distance} · ${restaurant.rating} ⭐",
                    style = MaterialTheme.typography.bodySmall.copy(color = Color.Gray),
                    maxLines = 1
                )
            }
        }
    }
}

@Composable
fun PopularRestaurantList(
    navController: NavController,
    popularRestaurants: List<Restaurant>
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        items(popularRestaurants) { restaurant ->
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
