package com.example.tdm.ui.screens.restaurants

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
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
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background) // Couleur de fond : #F2F2F1
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 16.dp)
        ) {
            // Titre "Option" avec bouton retour
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(
                    onClick = { navController.popBackStack() },
                    colors = IconButtonDefaults.iconButtonColors(contentColor = MaterialTheme.colorScheme.onBackground)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
                Text(
                    text = "Option",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.width(48.dp)) // Placeholder pour équilibrer la ligne
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Carte avec détails de l'item
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
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
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Eggs, 1G Meat, Lettuce, Onion, American Cheese.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Section quantité
            Text(
                text = "Quantity",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                IconButton(
                    onClick = { if (quantity > 1) quantity-- },
                    colors = IconButtonDefaults.iconButtonColors(contentColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text("-", style = MaterialTheme.typography.titleLarge)
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
                    Text("+", style = MaterialTheme.typography.titleLarge)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Champ pour les notes
            Text(
                text = "Notes",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = notes,
                onValueChange = { notes = it },
                placeholder = { Text("Do you have something to say to the restaurant?") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    cursorColor = MaterialTheme.colorScheme.primary
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

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
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text("Add to Cart", color = MaterialTheme.colorScheme.onPrimary, style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}
