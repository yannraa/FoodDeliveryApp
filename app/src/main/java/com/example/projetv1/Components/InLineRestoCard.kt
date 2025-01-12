package com.example.projetv1.Components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.projetv1.Models.Destination
import com.example.projetv1.Models.Restaurant

@Composable
fun InLine_RestaurantsCard(restaurant: Restaurant, navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(130.dp)
            .clickable { navController.navigate(Destination.RestaurantDetails.getDestination(restaurant.restId)) },
        shape = RoundedCornerShape(10),
        colors = CardColors(
            containerColor = Color.White,
            contentColor = Color.Unspecified,
            disabledContainerColor = Color.Unspecified,
            disabledContentColor = Color.Unspecified
        ),

        ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(12.dp)
        ) {
            Image(
                painter = painterResource(id = restaurant.restImg ),
                contentDescription = null,
                modifier = Modifier
                    .height(100.dp)
                    .width(160.dp)
                    .clip(RoundedCornerShape(10)),
                contentScale = ContentScale.Crop ,// Remplir l'espace de l'image
            )
            Column( verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .padding(start = 12.dp)
                    .fillMaxHeight()
                    .padding(vertical = 8.dp)) {
                Column {
                    Text(
                        text = restaurant.restName,
                        style = MaterialTheme.typography.titleMedium,
                    )
                    Text(
                        text = restaurant.restAddress,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xff696969)
                    )
                }
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
                            text = restaurant.restDistance,
                            fontWeight = FontWeight.SemiBold,
                        )
                    }

                    Text(
                        text = "${restaurant.restRating} ⭐ (${restaurant.restReviewCount}+)",
                        fontWeight = FontWeight.SemiBold,

                        )
                }
            }

        }
    }
}

