package com.example.projetv1.Pages

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.projetv1.Models.Destination
import com.example.projetv1.Components.InLine_RestaurantsCard
import com.example.projetv1.Data.restaurantList


@Composable
fun ScreenSearch( searchQuery :String ,navController:NavController){
    var searchQuery2 by remember { mutableStateOf(searchQuery) }
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
            TextField(
                value = searchQuery2,
                onValueChange = {searchQuery2 = it},
                placeholder = { Text("Search for food") },
                modifier = Modifier
                    .weight(1f) // Use weight to make TextField take all available space
                    .padding(end = 5.dp),// Add space between TextField and Filter Icon

//                    leadingIcon = {
//                        Icon(
//                            imageVector = Icons.Default.Search,
//                            contentDescription = "Search Icon"
//                        )
//                    },
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
                    .size(55.dp).clickable { navController.navigate(Destination.Search.getDestination(searchQuery2))} // Match the height of the TextField
                    .background(color = Color.White, shape = RoundedCornerShape(20)),

                contentAlignment = Alignment.Center
            ) {
//                    Icon(
//                        painter = painterResource(id = R.drawable.filter),
//                        contentDescription = "Filter Icon",
//                        modifier = Modifier.size(28.dp) // Size of the icon
//                    )
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search Icon",

                    )
            }



        }
        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn (verticalArrangement = Arrangement.spacedBy(16.dp)) {
            items(restaurantList) { restaurant ->
                InLine_RestaurantsCard(restaurant,navController)
            }
        }

    }

}