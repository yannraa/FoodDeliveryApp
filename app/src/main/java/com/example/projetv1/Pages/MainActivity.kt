package com.example.projetv1.Pages

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.projetv1.ui.theme.ProjetV1Theme
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.projetv1.Models.Destination
import com.example.projetv1.Components.InLine_RestaurantsCard
import com.example.projetv1.Models.NavItem
import com.example.projetv1.Models.Restaurant
import com.example.projetv1.Models.User
import com.example.projetv1.Data.cartList
import com.example.projetv1.Data.restaurantList


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Box(
                modifier = Modifier
                    .fillMaxSize()

                    .windowInsetsPadding(WindowInsets.systemBars)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = Color(0xffECECE3))

                ){
//
                    ProjetV1Theme {
                        Home()
                    }

                }
            }

        }
    }
}


// bottom bar navigation
@Composable
fun BottomNavigationBar(navController: NavController) {

    val navItemList = listOf(
        NavItem( "Home", Icons.Default.Home, {navController.navigate(Destination.Home.route)}) ,
        NavItem( "Cart", Icons.Default.ShoppingCart,  {navController.navigate(Destination.CardDetails.route)}),
        NavItem( "Message", Icons.Default.Notifications,  {navController.navigate(Destination.Home.route)}),
        NavItem( "Track", Icons.Default.LocationOn, {navController.navigate(Destination.Home.route)}),
    )


    Row (modifier = Modifier.fillMaxWidth().height(65.dp).background(Color(0xffF9F9F9)),
           verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround){
        navItemList.forEachIndexed { index, navItem ->
            Column (modifier = Modifier.clickable{navItem.onClick()},
                    verticalArrangement = Arrangement.SpaceAround, horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(imageVector = navItem.icon, contentDescription = navItem.label)
                    Text(text = navItem.label )
            }
        }
    }

}



@Composable
fun Home(){

        val navController = rememberNavController()

        Scaffold(
            bottomBar = { BottomNavigationBar(navController) }
        ) { paddingValues ->
            NavHost(
                navController = navController,
                startDestination = Destination.Home.route,
                modifier = Modifier.padding(paddingValues)
            ) {
                composable(Destination.Home.route) {
                    Column(modifier = Modifier.background(Color(0xffECECE3))) {
                        HomeScreen(
                            user = User(1, "13th Street, NYC", 3),
                            navController = navController
                        )
                    }
                }
                composable(Destination.Search.route) { backStackEntry ->
                    val searchQuery = backStackEntry.arguments?.getString("query")

                    if (searchQuery != null) {
                        Column(modifier = Modifier.background(Color(0xffECECE3))) {
                            ScreenSearch(searchQuery = searchQuery, navController = navController)
                        }
                    } else {
                        Text(text = "No search query provided.")
                    }
                }
                composable(Destination.NearbyRestaurants.route) {
                    Column(modifier = Modifier.background(Color(0xffECECE3))) {
                        ScreenNearbyRestaurants(navController)
                    }
                }
                composable(Destination.PopularRestaurants.route) {
                    Column(modifier = Modifier.background(Color(0xffECECE3))) {
                        ScreenPopularRestaurants(navController)
                    }
                }
                composable(Destination.RestaurantDetails.route) { backStackEntry ->
                    val restaurantId = backStackEntry.arguments?.getString("id")?.toIntOrNull()
                    restaurantId?.let { id ->
                        Column(modifier = Modifier.background(Color(0xffECECE3))) {
                            ScreenRestaurant(restaurantId = id, navController)
                        }
                    }
                }
                composable(Destination.CardDetails.route){
                    Column(modifier = Modifier.background(Color(0xffECECE3))) {
                        CartScreen(cartList.get(0), navController)
                    }
                }

                composable(Destination.MenuDetails.route) { backStackEntry ->
                    val restaurantId = backStackEntry.arguments?.getString("restaurantId")?.toIntOrNull()
                    val menuId = backStackEntry.arguments?.getString("menuId")?.toIntOrNull()

                    if (restaurantId != null && menuId != null) {
                        Column(modifier = Modifier.background(Color(0xffECECE3))) {
                            ScreenMenuDetail(
                                restaurantId = restaurantId,
                                menuId = menuId,
                                navController = navController
                            )

                        }
                    }
                }
            }
        }
    }



@Composable
fun HomeScreen(user: User, navController : NavController) {
    var searchQuery by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {
        // Barre de menu en haut
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
                    text = user.userAddress, // Dynamically passed address
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
                modifier = Modifier.size(32.dp).clickable { navController.navigate(Destination.CardDetails.route) }
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
                        text = user.userNbOfElementsInCart.toString(),
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

        Spacer(modifier = Modifier.height(1.dp))

        // Barre de recherche

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
                        .size(55.dp).clickable { navController.navigate(Destination.Search.getDestination(searchQuery))} // Match the height of the TextField
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


        Spacer(modifier = Modifier.height(7.dp))

        // Catégories de nourriture
        CategorySection(navController)

        Spacer(modifier = Modifier.height(16.dp))

        // Restaurants en vedette
        //row fiha text a gauche et boutton  view all extreme droite
        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            // Title Text
            Text(
                text = "Restaurants Nearby",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.SemiBold
            )
            // Button with Text
            Text(
                text = AnnotatedString("View All"),
                modifier =Modifier.clickable { navController.navigate(Destination.PopularRestaurants.route) }
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            items(restaurantList) { restaurant ->
                InBlock_RestaurantCard(restaurant,navController)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Articles populaires
        // row avec view all a droite
        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            // Title Text
            Text(
                text = "Popular Restaurants",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.SemiBold
            )

            // Button with Text
           Text(
                text = AnnotatedString("View All"),
               modifier =Modifier.clickable { navController.navigate(Destination.PopularRestaurants.route) }
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


@Composable
fun CategoryChip(category: String,navController: NavController) {
    Card(
        modifier = Modifier.clickable { navController.navigate(Destination.Search.getDestination(category)) },
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
fun CategorySection(navController: NavController) {
    // Title Text
    Text(
        text = "By Category",
        style = MaterialTheme.typography.headlineSmall,
        fontWeight = FontWeight.SemiBold
    )
    Spacer(modifier = Modifier.height(8.dp))
    LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        items(listOf("Italian", "Traditional", "Asian", "Mexican", "FastFood","Pizza")) { category ->
            CategoryChip(category,navController)
        }
    }
}

@Composable
fun InBlock_RestaurantCard(restaurant: Restaurant, navController: NavController) {
    Card (
        shape = RoundedCornerShape(10),
        colors = CardColors(
            containerColor = Color.White,
            contentColor = Color.Unspecified,
            disabledContainerColor = Color.Unspecified,
            disabledContentColor =Color.Unspecified
        ),
        modifier = Modifier.size(width = 185.dp, height = 195.dp)
                           .shadow(elevation = 10.dp, shape = RoundedCornerShape(15.dp),true,Color.DarkGray,Color.DarkGray)
                           .clickable {
                               navController.navigate(
                                   Destination.RestaurantDetails.getDestination(
                                       restaurant.restId
                                   )
                               )
                                      } ,


    ) {
        Column{
            //Image du restaurant
            Image(
                painter = painterResource(id = restaurant.restImg), // Ressource d'image
                contentDescription = null, // Description (non nécessaire ici)
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
                    text = restaurant.restName,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(vertical = 2.dp),
                    fontWeight = FontWeight.Bold,
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
                            text = restaurant.restDistance,
                            fontWeight = FontWeight.SemiBold,
                        )
                    }

                    Text(
                        text = "⭐ ${restaurant.restRating}",
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(end = 8.dp)
                        )
                }
            }





        }
    }
}




