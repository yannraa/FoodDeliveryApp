package com.example.tdm.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.tdm.data.model.Restaurant

@Composable
fun RestaurantCard(
    restaurant: Restaurant,
    onCardClick: (Restaurant) -> Unit, // Gestion du clic externe
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(130.dp)
            .clickable{ onCardClick(restaurant) },
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
                painter = rememberAsyncImagePainter(restaurant.imageUrl),
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
                        text = restaurant.name,
                        style = MaterialTheme.typography.titleSmall.copy(fontSize = 20.sp),
//                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = restaurant.address,
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
                            text = restaurant.distance,
                            fontWeight = FontWeight.SemiBold,
                        )
                    }

                    Text(
                        text = "${restaurant.rating} ⭐ (${restaurant.reviewCount}+)",
                        fontWeight = FontWeight.SemiBold,

                        )
                }
            }

        }
    }
}