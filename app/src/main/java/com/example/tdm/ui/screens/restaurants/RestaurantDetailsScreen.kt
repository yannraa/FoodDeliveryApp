package com.example.tdm.ui.screens.restaurants

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.example.tdm.R
import com.example.tdm.data.model.MenuItem
import com.example.tdm.data.model.Restaurant


@Composable
fun RestaurantDetailsScreen(restaurant: Restaurant, navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        // Header avec image et bouton retour
        Box {
            Image(
                painter = rememberAsyncImagePainter(model = restaurant.imageUrl),
                contentDescription = "Restaurant Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop
            )
            androidx.compose.material3.IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier
                    .padding(16.dp)
                    .size(40.dp)
                    .background(
                        color = Color.White,
                        shape = CircleShape
                    )
            ) {
                androidx.compose.material3.Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back Icon",
                    tint = Color.Black
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Restaurant details
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            Text(
                text = "${restaurant.name}",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "${restaurant.cuisine} · ${restaurant.address}",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.DarkGray
            )
            if (restaurant.open) {
                Text(
                    text = "Open",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF4CAF50) // Green color for "Opening"
                )
            }else{
                Text(
                    text = "Closed",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Red // Green color for "Opening"
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Rating and reviews
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "⭐ ${restaurant.rating} (${restaurant.reviewCount}+)",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.DarkGray
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "Reviews",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFFFF5722), // Orange for "Reviews"
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.clickable {
                        navController.navigate("reviews")
                    }

                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Contact information
            Text(
                text = "Contact us:",
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Phone,
                    contentDescription = "Phone Icon",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = restaurant.contact, style = MaterialTheme.typography.bodySmall)

                Spacer(modifier = Modifier.width(18.dp))

                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = "Email Icon",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = restaurant.email, style = MaterialTheme.typography.bodySmall)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Menu Section
        Text(
            text = "Menu",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(horizontal = 16.dp).align(Alignment.CenterHorizontally),
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        // Group items by category and display
        val groupedMenu = restaurant.menu.groupBy { it.category }
        groupedMenu.forEach { (category, items) ->
            MenuCategorySection(category = category, items = items, onItemClick = {
                navController.navigate("itemDetails/${it.id}/${it.name}/${it.price}/${restaurant.name}/${restaurant.id}")
            })
        }
    }
}

@Composable
fun MenuCategorySection(category: String, items: List<MenuItem>, onItemClick: (MenuItem) -> Unit) {
    Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
        Text(
            text = category,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        items.forEach { item ->
            MenuItemCard(item, onItemClick)
        }
    }
}

@Composable
fun MenuItemCard(item: MenuItem, onItemClick: (MenuItem) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp).clickable { onItemClick(item)  },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(text = item.name, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold)
                Text(
                    text = "$${item.price}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFFFF5722),
                    fontWeight = FontWeight.Medium
                )
            }
            IconButton(
                onClick = { /* */}
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add to cart"
                )
            }
        }
    }
}
