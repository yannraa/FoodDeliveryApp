package com.example.projetv1.Pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.projetv1.Models.Destination
import com.example.projetv1.Data.restaurantList


@Composable
fun ScreenRestaurant(restaurantId: Int,navController: NavController) {
    // Fetch restaurant details using the id
    val restaurant = remember { restaurantList.find { it.restId == restaurantId } }
    if (restaurant != null) {
    Column(
        modifier = Modifier
            .fillMaxSize()

    ) {
        // Top Section with Image
        Box {

                Image(
                    painter = painterResource(id = restaurant.restImg), // Replace with your actual image
                    contentDescription = "${restaurant.restName}'s Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentScale = ContentScale.Crop
                )
            // Back Button
            IconButton(
                onClick = {navController.popBackStack() },
                modifier = Modifier
                    .padding(16.dp)
                    .size(40.dp)
                    .background(
                        color = Color.White,
                        shape = CircleShape
                    )
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back Icon",
                    tint = Color.Black
                )
            }
        }

        // Restaurant Info
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = "${restaurant.restName}",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "${restaurant.restType} · ${restaurant.restAddress}",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.DarkGray
            )
            if (restaurant.restIsOpen) {
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

            Row(
                modifier = Modifier.padding(top = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
//                Icon(
//                    imageVector = Icons.Default.Star,
//                    contentDescription = "Star Icon",
//                    tint = Color(0xFFFFD700), // Gold color for the star
//                    modifier = Modifier.size(16.dp)
//                )
                Text(
                    text = "⭐ ${restaurant.restRating} (${restaurant.restReviewCount}+)",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.DarkGray
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "Reviews",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFFFF5722), // Orange for "Reviews"
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.clickable { /* Navigate to reviews */ }

                )
            }
        }

        HorizontalDivider(color = Color.LightGray, thickness = 1.dp)

        // Menu Section
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = "Menu",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 16.dp, bottom = 8.dp).align(Alignment.CenterHorizontally)
            )
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()

            ) {
                val groupedMenu = restaurant.restMenuList.groupBy { it.menuType }
                groupedMenu.forEach { (menuType, menuItems) ->
                    // Menu category header
                    item {
                        Text(
                            text = menuType.name,
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(vertical = 5.dp)
                        )
                    }
                    // Menu items for the category
                    items(menuItems) { menuItem ->
                        MenuItem2(restaurantId = restaurant.restId,menuId = menuItem.menuId,name = menuItem.menuName, price = "${menuItem.menuPrice} DZD", navController)
                    }
                }
            }

        }
    }
    }else{
        Column {
            Text(
                text = "No result found"
            )
        }
    }
}



@Composable
fun MenuItem2(restaurantId: Int,menuId:Int ,name: String, price: String, navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 1.dp, vertical = 8.dp)
            .background(
                color = Color.White,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(vertical = 8.dp, horizontal = 8.dp)
            .clickable { navController.navigate(Destination.MenuDetails.getDestination(restaurantId, menuId))
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Left Column for Name and Price
        Column(
            modifier = Modifier.weight(1f).padding(start = 10.dp)
        ) {
            Text(
                text = name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF333333)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "$price",
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFFFF5722),
                fontWeight = FontWeight.Medium
            )
        }

        // Right Column for the Add Icon Button
        IconButton(
            onClick = { /* Add to cart action */ },
            modifier = Modifier
                .size(40.dp).padding(end = 10.dp)
                .background(
                    color = Color(0xFFF5F5F5),
                    shape = CircleShape
                )
                .border(
                    width = 1.dp,
                    color = Color.LightGray,
                    shape = CircleShape
                )
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add to Cart",
                tint = Color(0xFF333333)
            )
        }
    }
}

