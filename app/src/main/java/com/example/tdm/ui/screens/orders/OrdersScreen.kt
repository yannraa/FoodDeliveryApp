package com.example.tdm.ui.screens.orders

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.tdm.data.model.OrderResponse
import com.example.tdm.data.model.toOrderStatus
import com.example.tdm.data.repository.OrderRepository
import com.example.tdm.ui.screens.home.HomeViewModel
import com.example.tdm.ui.screens.home.HomeViewModelFactory
import java.util.*

@Composable
fun OrdersScreen(navController: NavController, repository: OrderRepository) {
    val factory = OrderViewModelFactory(repository)
    val viewModel: OrderViewModel = viewModel(factory = factory)

    val orders = viewModel.orders.collectAsState()
    val isLoading = viewModel.isLoading.collectAsState()
    val error = viewModel.error.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchOrders(
            userId = 1 // Remplacer par l'ID de l'utilisateur connecté
        )
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Go Back")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "My Orders",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
            )
        }

        when {
            isLoading.value -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            error.value != null -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = error.value ?: "Unknown Error", color = Color.Red)
                }
            }
            else -> {
                LazyColumn(modifier = Modifier.padding(horizontal = 16.dp)) {
                    items(orders.value) { order ->
                        OrderRow(order, navController)
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun OrderRow(order: OrderResponse, navController: NavController) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        onClick = {
            val orderStatusString = order.orderStatus // Ceci vient du backend
            val orderStatus = try {
                orderStatusString.toOrderStatus()
            } catch (e: IllegalArgumentException) {
                // Gérer une erreur si le statut est inconnu
                Log.e("OrderStatus", "Unknown status: $orderStatusString")
                null // ou une valeur par défaut, par exemple `OrderStatus.PLACED`
            }
            navController.navigate("orderTracking/${"Test berk"}/${orderStatus!!.name}")
        }
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            // Image du restaurant
            Image(
                painter = rememberAsyncImagePainter(order.restaurant),
                contentDescription = null,
                modifier = Modifier
                    .size(64.dp)
                    .padding(end = 16.dp)
            )
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = order.restaurant.name,
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = "OrderId: #${order.documentId}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = order.orderStatus.capitalize(Locale.ROOT),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "$${"%.2f".format(order.totalPrice)}",
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}
