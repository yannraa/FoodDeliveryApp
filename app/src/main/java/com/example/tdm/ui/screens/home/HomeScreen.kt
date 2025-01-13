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
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
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
            CategorySection()

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
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Menu Icon on the left
        Icon(
            imageVector = Icons.Default.Menu, // Replace with your menu icon
            contentDescription = "Menu",
            modifier = Modifier.size(30.dp)
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
                modifier = Modifier.size(30.dp),

                )

            // Badge for the cart count
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(16.dp)
                    .offset(x = 4.dp, y = (-4).dp)
                    .background(
                        color = Color.White,
                        shape = CircleShape
                    )
                    .align(Alignment.TopEnd)

            ) {
                Text(
                    text = "01",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = Color.Black,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    ),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun SearchBar() {
    var searchQuery by remember { mutableStateOf("") }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .padding(end = 5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Search TextField
        TextField(
            value = searchQuery,
            onValueChange = {searchQuery = it},
            placeholder = { Text("Search for food") },
            modifier = Modifier
                .weight(1f) // Use weight to make TextField take all available space
                .padding(end = 5.dp),// Add space between TextField and Filter Icon

            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
            ),

            shape = RoundedCornerShape(15.dp), // Add rounded corners
            singleLine = true // Ensure the TextField stays on a single line
        )
        // Filter Icon
        Box(
            modifier = Modifier
                .size(55.dp)// Match the height of the TextField
                .background(color = Color.White, shape = RoundedCornerShape(25)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search Icon",

                )
        }
    }
}

@Composable
fun CategoryChip(category: String) {
    Card(
        //modifier = Modifier.clickable { navController.navigate(Destination.Search.getDestination(category)) },
        colors = CardColors(
            containerColor = Color.White,
            contentColor = Color.Unspecified,
            disabledContainerColor = Color.Unspecified,
            disabledContentColor =Color.Unspecified
        ),
        shape = RoundedCornerShape(30.dp),

        ) {
        Text(
            text = category,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
        )
    }
}

@Composable
fun CategorySection() {
    LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        items(listOf("Italian", "Traditional", "Asian", "Mexican", "FastFood","Pizza")) { category ->
            CategoryChip(category)
        }
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

            shape = RoundedCornerShape(10),
            colors = CardColors(
                containerColor = Color.White,
                contentColor = Color.Unspecified,
                disabledContainerColor = Color.Unspecified,
                disabledContentColor =Color.Unspecified
            ),
            modifier = Modifier.size(width = 185.dp, height = 195.dp),

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
