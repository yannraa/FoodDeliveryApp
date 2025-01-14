package com.example.tdm.ui.screens.cart

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import com.example.tdm.data.model.CartItem

@Composable
fun CartScreen(viewModel: CartViewModel, navController: NavController) {

    val cartItems by viewModel.cartItems.collectAsState()
    val totalCost by viewModel.totalCost.collectAsState()
    val orderResponse by viewModel.orderResponse.collectAsState()
    val orderError by viewModel.orderError.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Title
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            // Bouton Retour
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Go Back",
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            // Titre "My Cart"
            Text(
                text = "My cart",
                style = MaterialTheme.typography.titleLarge, // Typographie existante conservée
            )
        }


        // List of Cart Items
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(bottom = 10.dp)
        ) {
            items(cartItems) { cartItem ->
                CartItemRow(
                    cartItem = cartItem,
                    onRemove = { viewModel.removeItemFromCart(cartItem.id) },
                    onIncrease = { viewModel.updateQuantity(cartItem.id, cartItem.quantity + 1) },
                    onDecrease = { if (cartItem.quantity > 1) viewModel.updateQuantity(cartItem.id, cartItem.quantity - 1) }
                )
            }
        }
        // Divider
        HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))
        // Divider and Summary Section
        SummarySection(totalCost = totalCost)

        Spacer(modifier = Modifier.height(10.dp))

        // Order Button
        Button(
            onClick = {
                    Log.d("CartScreen", "Order button clicked")
                    viewModel.placeOrder(
                        restaurantId = cartItems.firstOrNull()?.restaurantId ?: -1,
                        items = cartItems,
                        totalPrice = totalCost
                    )

            },
            modifier = Modifier
                .fillMaxWidth().height(80.dp)
                .padding(vertical = 16.dp, horizontal = 30.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFFF5722)
            ),
            enabled = !isLoading // Désactive le bouton lorsqu'il est en cours de chargement
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    color = MaterialTheme.colorScheme.onPrimary,
                    strokeWidth = 2.dp
                )
            } else {
                Text(text = "Order", color = Color.White, fontWeight = FontWeight.SemiBold, style = MaterialTheme.typography.titleMedium)
            }
        }
    }
}

@Composable
fun CartItemRow(
    cartItem: CartItem,
    onRemove: () -> Unit,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardColors(
            containerColor = Color.White,
            contentColor = Color.Unspecified,
            disabledContainerColor = Color.Unspecified,
            disabledContentColor = Color.Unspecified
        ),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = cartItem.name,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = cartItem.restaurantName,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.secondary
                )
                Text(
                    text = "\"\$${"%.2f".format(cartItem.price)}\"",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onDecrease) {
                    Icon(Icons.Filled.Close, contentDescription = "Decrease quantity", tint = MaterialTheme.colorScheme.primary)
                }
                Text(
                    text = cartItem.quantity.toString(),
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(horizontal = 8.dp),
                    color = MaterialTheme.colorScheme.onSurface
                )
                IconButton(onClick = onIncrease) {
                    Icon(Icons.Filled.Add, contentDescription = "Increase quantity", tint = MaterialTheme.colorScheme.primary)
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            IconButton(onClick = onRemove) {
                Icon(Icons.Filled.Delete, contentDescription = "Remove item", tint = Color.Red)
            }
        }
    }
}

@Composable
fun SummarySection(totalCost: Double) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(16.dp)
    ) {
        // Sub Total
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Sub total",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "$${"%.2f".format(totalCost)}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Delivery Fee
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Delivery Fee",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "$0.00",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        Spacer(modifier = Modifier.height(8.dp))



        // Total Cost
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Total Cost",
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "$${"%.2f".format(totalCost)}",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary
                )
            )
        }
    }
}
