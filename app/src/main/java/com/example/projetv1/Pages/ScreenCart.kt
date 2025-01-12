package com.example.projetv1.Pages

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.projetv1.Models.Cart
import com.example.projetv1.Models.Menu2

@Composable
fun CartScreen(
    cart: Cart,
    navController: NavController
) {
    val deliveryFee: Double = 500.00
    // State for cart items and subtotal
    val cartItems = remember { mutableStateListOf(*cart.cartItems.toTypedArray()) }
    val subTotal = remember { derivedStateOf { cartItems.sumOf { it.menuPrice * it.menuQtt } } }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {
        // Header
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }
            Text(
                text = "My Cart",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        // List of Cart Items
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(cartItems) { menu ->
                CartItemRow(
                    menu = menu,
                    onQuantityIncrease = {
                        val updatedMenu = menu.copy(menuQtt = menu.menuQtt + 1)
                        val index = cartItems.indexOf(menu)
                        if (index != -1) {
                            cartItems[index] = updatedMenu
                        }
                    },
                    onQuantityDecrease = {
                        if (menu.menuQtt > 1) {
                            val updatedMenu = menu.copy(menuQtt = menu.menuQtt - 1)
                            val index = cartItems.indexOf(menu)
                            if (index != -1) {
                                cartItems[index] = updatedMenu
                            }
                        }
                    },
                    onRemoveItem = {
                        cartItems.remove(menu) // Remove item from the list
                    }
                )
            }
        }
        // Divider
        HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))

        // Summary
        SummarySection(
            subTotal = subTotal.value,
            deliveryFee = deliveryFee
        )

        // Confirm Button
        Button(
            onClick = { /* Confirm action */ },
            modifier = Modifier
                .fillMaxWidth().height(80.dp)
                .padding(vertical = 16.dp, horizontal = 30.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFFF5722)
            )
        ) {
            Text(text = "Commander", color = Color.White, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
        }
    }
}

@Composable
fun CartItemRow(
    menu: Menu2,
    onQuantityIncrease: () -> Unit,
    onQuantityDecrease: () -> Unit,
    onRemoveItem: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(Color.White, shape = RoundedCornerShape(8.dp))
            .padding(16.dp),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // Menu Name and Price
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = menu.menuName,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "${"%.2f".format(menu.menuPrice)} DZD",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFFFF5722)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        // Quantity Controls and Delete Button
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Quantity Controls
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .border(1.dp, Color(0xffECECE3), shape = RoundedCornerShape(30))
                    .padding(horizontal = 8.dp, vertical = 4.dp).height(40.dp)
            ) {
                IconButton(onClick = onQuantityDecrease, modifier = Modifier.size(24.dp)) {
                    Text("—", fontSize = 18.sp)
                }
                Text(
                    text = menu.menuQtt.toString(),
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
                IconButton(onClick = onQuantityIncrease, modifier = Modifier.size(20.dp)) {
                    Icon(
                        Icons.Default.Add,
                        contentDescription = "Increase Quantity",
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Remove Button
            IconButton(onClick = onRemoveItem) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Remove Item",
                    tint = Color.Red
                )
            }
        }
    }

}

@Composable
fun SummarySection(
    subTotal: Double,
    deliveryFee: Double
) {

    val totalCost = subTotal + deliveryFee

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, shape = RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        Text(text = "Order Summary", fontWeight = FontWeight.SemiBold, style = MaterialTheme.typography.headlineSmall, modifier = Modifier.padding(bottom = 8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Sub total", style = MaterialTheme.typography.bodyLarge)
            Text(text = "${"%.2f".format(subTotal)} DZD", style = MaterialTheme.typography.bodyLarge,  fontWeight = FontWeight.SemiBold,)
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Delivery Fee", style = MaterialTheme.typography.bodyLarge)
            Text(text = "${"%.2f".format(deliveryFee)} DZD", style = MaterialTheme.typography.bodyLarge,   fontWeight = FontWeight.SemiBold,)
        }

        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Total Cost", style = MaterialTheme.typography.titleMedium)
            Text(
                text = "${"%.2f".format(totalCost)} DZD",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFFF5722)
            )
        }
    }
}
