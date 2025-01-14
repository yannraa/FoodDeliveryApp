package com.example.tdm.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
import com.example.tdm.utils.TokenManager
import kotlinx.coroutines.launch

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
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
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
            SearchBar(
                navController = navController,
                restaurants = restaurants
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Restaurant Nearby
            SectionTitle(title = "Restaurant Nearby", onViewAllClick = {
                navController.currentBackStackEntry?.savedStateHandle?.set("restaurants", restaurants)
                navController.navigate("nearby")
            })
            RestaurantNearbyList(
                nearbyRestaurants = restaurants,
                navController = navController
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Popular Restaurants
            SectionTitle(title = "Popular Restaurant", onViewAllClick = {
                navController.currentBackStackEntry?.savedStateHandle?.set("restaurants", restaurants)
                navController.navigate("popular")
            })
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
    val context = LocalContext.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Menu Icon on the left
        val coroutineScope = rememberCoroutineScope()

        Icon(
            imageVector = Icons.Default.Close,
            contentDescription = "Logout",
            modifier = Modifier
                .size(30.dp)
                .clickable {
                    coroutineScope.launch {
                        TokenManager.clearToken(context)
                        navController.navigate("login") {
                            popUpTo(0) // Supprime toutes les destinations précédentes
                        }
                    }
                }
        )

        // Address Section in the center
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Location Icon
            Icon(
                imageVector = Icons.Default.LocationOn, // Replace with your location icon
                contentDescription = "Location",
                modifier = Modifier.size(25.dp),
//                tint = MaterialTheme.colorScheme.primary
            )
            // Address Text
            Text(
                text = "Rivertown ", // Dynamically passed address
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
            // Edit Icon (Pen)
            Icon(
                imageVector = Icons.Outlined.Edit, // Replace with your pen/edit icon
                contentDescription = "Edit Address",
                modifier = Modifier
                    .size(25.dp)
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(30)
                    )
//                        .clickable {
//                            edit personnal address page
//                        },

            )
        }
        // Cart Icon with Badge on the right
        Box(
            contentAlignment = Alignment.TopEnd,
            modifier = Modifier.size(32.dp).clickable { "lien vers Panier"}
        ) {
            // Cart Icon
            Icon(
                imageVector = Icons.Outlined.ShoppingCart, // Replace with your cart icon
                contentDescription = "Cart",
                modifier = Modifier.size(30.dp).clickable { navController.navigate("cart") },
                )

            // Badge for the cart count
        }
    }
}

@Composable
fun SearchBar(
    navController: NavController,
    restaurants: List<Restaurant>
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .padding(end = 5.dp)
            .clickable {
                navController.currentBackStackEntry?.savedStateHandle?.set("restaurants", restaurants)
                navController.navigate("search")
            }
            .background(Color.White, RoundedCornerShape(15.dp)) // Add background with rounded corners
            .padding(horizontal = 16.dp, vertical = 12.dp), // Add padding inside the clickable area
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = "Search Icon",
            tint = Color.Gray,
            modifier = Modifier.size(20.dp)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = "Search for food",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray,
            modifier = Modifier.weight(1f) // Ensures the text takes up the remaining space
        )
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
    nearbyRestaurants: List<Restaurant>,
    navController: NavController
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        items(nearbyRestaurants) { restaurant ->
            RestaurantNearbyCard(restaurant, navController)
        }
    }
}

@Composable
fun RestaurantNearbyCard(restaurant: Restaurant, navController: NavController) {
    Card(

        shape = RoundedCornerShape(10),
        colors = CardColors(
            containerColor = Color.White,
            contentColor = Color.Unspecified,
            disabledContainerColor = Color.Unspecified,
            disabledContentColor =Color.Unspecified
        ),
        modifier = Modifier.size(width = 185.dp, height = 195.dp).clickable {
            navController.currentBackStackEntry?.savedStateHandle?.set("restaurant", restaurant)
            navController.navigate("restaurantDetails")
        },

        ) {
        Column {
            Image(
                painter = rememberAsyncImagePainter(restaurant.imageUrl),
                contentDescription = restaurant.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp)
                    .padding(horizontal = 12.dp)
                    .clip(RoundedCornerShape(10))
                    .height(100.dp), // Hauteur de l'image
                contentScale = ContentScale.Crop ,// Remplir l'espace de l'image

            )
            Column (modifier = Modifier
                .padding(horizontal = 12.dp)
                .padding(top = 8.dp, bottom = 12.dp)
                .fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween){
                Text(
                    text = restaurant.name,
                    style = MaterialTheme.typography.titleSmall.copy(fontSize = 16.sp),
                    modifier = Modifier.padding(vertical = 2.dp),
                    fontWeight = FontWeight.SemiBold,
                )
                Row (
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ){

                    Row (
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(2.dp)
                    ){
                        Icon(
                            imageVector = Icons.Default.LocationOn, // Replace with your location icon
                            contentDescription = "Location",
                            modifier = Modifier.size(18.dp),
                        )
                        Text(
                            text = restaurant.distance,
                            fontWeight = FontWeight.SemiBold,
                        )
                    }

                    Text(
                        text = "⭐ ${restaurant.rating}",
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(end = 8.dp)
                    )
                }
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