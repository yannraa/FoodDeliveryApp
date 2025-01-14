package com.example.tdm.ui.screens.restaurants

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.tdm.data.model.CartItem
import com.example.tdm.ui.screens.cart.CartViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemDetailsScreen(
    itemId: Int,
    itemName: String,
    itemPrice: Double,
    restaurantId: Int,
    restaurantName: String,
    viewModel: CartViewModel,
    navController: NavController
) {
    var quantity by remember { mutableStateOf(1) }
    var notes by remember { mutableStateOf("") }
    val restaurantConflict by viewModel.restaurantConflict.collectAsState()

    // Gestion des conflits avec un autre restaurant
    if (restaurantConflict) {
        AlertDialog(
            onDismissRequest = { viewModel.clearConflict() },
            title = { Text("Different Restaurant") },
            text = { Text("Your cart contains items from another restaurant. Do you want to clear your cart and add this item?") },
            confirmButton = {
                Button(onClick = {
                    viewModel.clearCartAndAddItem(
                        CartItem(
                            id = itemId,
                            name = itemName,
                            restaurantId = restaurantId,
                            restaurantName = restaurantName,
                            price = itemPrice,
                            quantity = quantity,
                            notes = notes
                        )
                    )
                    navController.popBackStack()
                }) {
                    Text("Yes")
                }
            },
            dismissButton = {
                Button(onClick = { viewModel.clearConflict() }) {
                    Text("No")
                }
            }
        )
    }

    // Contenu principal
    Column(
        modifier = Modifier
            .fillMaxSize().background(color = Color(0xffECECE3))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize().padding(12.dp)
        ) {
            // Titre "Option" avec bouton retour
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = {navController.popBackStack()}) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text("Options", style = MaterialTheme.typography.headlineSmall, color = Color.Black)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Carte avec détails de l'item
            Card(
                modifier = Modifier.fillMaxWidth().shadow(
                    elevation = 10.dp,
                    shape = RoundedCornerShape(15.dp),
                    true,
                    Color.DarkGray,
                    Color.Gray
                ),
                colors = CardColors(
                    containerColor = Color.White,
                    contentColor = Color.Unspecified,
                    disabledContainerColor = Color.Unspecified,
                    disabledContentColor = Color.Unspecified
                ),
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = itemName,
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "$${"%.2f".format(itemPrice)}",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.SemiBold,
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Eggs, 1G Meat, Lettuce, Onion, American Cheese.", // deviens un champs
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Card(

                colors = CardColors(
                    containerColor = Color.White,
                    contentColor = Color.Unspecified,
                    disabledContainerColor = Color.Unspecified,
                    disabledContentColor = Color.Unspecified
                ),
                modifier = Modifier.shadow(
                    elevation = 10.dp,
                    shape = RoundedCornerShape(15.dp),
                    true,
                    Color.DarkGray,
                    Color.Gray
                )
            ) {
                // Section pour la quantité
                Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 18.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Quantity",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(5.dp)
                            .background(Color(0xffECECE3), shape = RoundedCornerShape(30))
                    ) {
                        IconButton(
                            onClick = { if (quantity > 1) quantity-- },
                            colors = IconButtonDefaults.iconButtonColors(contentColor = MaterialTheme.colorScheme.primary)
                        ) {
                            Text("—", fontSize = 25.sp)
                        }
                        Text(
                            text = quantity.toString(),
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.padding(horizontal = 16.dp),
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        IconButton(
                            onClick = { quantity++ },
                            colors = IconButtonDefaults.iconButtonColors(contentColor = MaterialTheme.colorScheme.primary)
                        ) {
                            Text("+", fontSize = 25.sp)
                        }
                    }
                }

            }



            Spacer(modifier = Modifier.height(24.dp))

            Card(

                colors = CardColors(
                    containerColor = Color.White,
                    contentColor = Color.Unspecified,
                    disabledContainerColor = Color.Unspecified,
                    disabledContentColor = Color.Unspecified
                ),
                modifier = Modifier.shadow(
                    elevation = 10.dp,
                    shape = RoundedCornerShape(15.dp),
                    true,
                    Color.DarkGray,
                    Color.Gray
                )
            ) {

            }
            Column() {

                // Notes
                Text(
                    "Notes", style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(start = 20.dp), fontWeight = FontWeight.Bold
                )
                TextField(
                    value = notes,
                    onValueChange = {notes =it},
                    placeholder = { Text("Do you have something to say to the restaurant?") },
                    modifier = Modifier
                        .fillMaxWidth().padding(top = 8.dp).shadow(
                            elevation = 10.dp,
                            shape = RoundedCornerShape(15.dp),
                            true,
                            Color.DarkGray,
                            Color.Gray
                        )
                        .height(170.dp),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.White,
                        focusedContainerColor = Color.White,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                    ),
                    shape = RoundedCornerShape(15.dp)
                )
            }
            Spacer(modifier = Modifier.weight(1f))

            // Bouton Ajouter au panier
            Button(
                onClick = {
                    viewModel.addItemToCart(
                        CartItem(
                            id = itemId,
                            name = itemName,
                            restaurantId = restaurantId,
                            restaurantName = restaurantName,
                            price = itemPrice,
                            quantity = quantity,
                            notes = notes.ifBlank { null } // Ajout des notes seulement si remplies
                        )
                    )
                    navController.popBackStack()
                },
                modifier = Modifier
                    .fillMaxWidth().height(80.dp)
                    .padding(vertical = 16.dp, horizontal = 30.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFF5722)
                )
            ) {
                Text(
                    "Add to Cart",
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold,
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}
