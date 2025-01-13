package com.example.tdm.ui.screens.tracking

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tdm.R
import com.example.tdm.data.model.OrderStatus

@Composable
fun OrderTrackingScreen(
    deliveryAddress: String,
    currentOrderStatus: OrderStatus
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Delivery Address",
            fontSize = 18.sp,
            color = Color.Black,
            fontFamily = FontFamily(
                Font(R.font.montserrat_regular, FontWeight.Medium)
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        // Adresse de livraison
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            shape = RoundedCornerShape(8.dp),
            elevation = 4.dp
        ) {
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.LocationOn,
                    contentDescription = null,
                    tint = Color(0xFFFF6B35), // Couleur orange
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = deliveryAddress,
                    fontSize = 16.sp,
                    color = Color.Gray,
                    fontFamily = FontFamily(
                        Font(R.font.montserrat_regular, FontWeight.ExtraBold)
                    ),
                )
            }
        }

        // Titre "Order Status"
        Text(
            text = "Order Status",
            fontSize = 18.sp,
            color = Color.Black,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            fontFamily = FontFamily(
                Font(R.font.montserrat_regular, FontWeight.Medium)
            ),
        )

        // Liste des étapes de la commande
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(2.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Étapes de la commande
            StatusItem(
                label = "Order Placed",
                description = "Order Placed Successfully",
                icon = if (currentOrderStatus >= OrderStatus.PLACED) R.drawable.checkcircle else R.drawable.delivery ,
                isChecked = currentOrderStatus >= OrderStatus.PLACED,
                showThread = true
            )
            StatusItem(
                label = "Order Confirmed",
                description = "Order Confirmed",
                icon = if (currentOrderStatus >= OrderStatus.CONFIRMED) R.drawable.checkcircle else R.drawable.confirm ,
                isChecked = currentOrderStatus >= OrderStatus.CONFIRMED,
                showThread = true
            )
            StatusItem(
                label = "Out for Delivery",
                description = "Your Order is Out for Delivery!",
                icon = if (currentOrderStatus >= OrderStatus.OUT_FOR_DELIVERY) R.drawable.checkcircle else R.drawable.delivery ,
                isChecked = currentOrderStatus >= OrderStatus.OUT_FOR_DELIVERY,
                showThread = true
            )
            StatusItem(
                label = "Delivered",
                description = "Your Order has been Delivered!",
                icon = if (currentOrderStatus >= OrderStatus.DELIVERED) R.drawable.checkcircle else R.drawable.delivered ,
                isChecked = currentOrderStatus >= OrderStatus.DELIVERED,
                showThread = true
            )
            StatusItem(
                label = "Order Completed",
                description = "Order Completed",
                icon = if (currentOrderStatus >= OrderStatus.COMPLETED) R.drawable.checkcircle else R.drawable.complete ,
                isChecked = currentOrderStatus >= OrderStatus.COMPLETED,
                showThread = false
            )
        }
    }
}

@Composable
fun StatusItem(
    label: String,
    description: String,
    icon: Int,
    isChecked: Boolean,
    showThread: Boolean
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top ,
        horizontalArrangement = Arrangement.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = null,
                tint = if (isChecked) Color(0xFFFF6B35) else Color.Gray,
                modifier = Modifier.size(32.dp)
            )
            if (showThread) {
                Spacer(modifier = Modifier.height(2.dp))
                Box(
                    modifier = Modifier
                        .width(2.dp)
                        .height(30.dp)
                        .background(Color.Gray)
                )
                Spacer(modifier = Modifier.height(2.dp))
            }
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column(
            modifier = Modifier.fillMaxWidth(0.8f),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = label,
                fontSize = 14.sp,
                color = if (isChecked) Color(0xFFFF6B35) else Color.Black,
                fontFamily = FontFamily(
                    Font(R.font.montserrat_regular, FontWeight.Medium)
                ),
            )
            Text(
                text = description,
                fontSize = 12.sp,
                color = Color.Gray,
                fontFamily = FontFamily(
                    Font(R.font.montserrat_regular, FontWeight.ExtraBold)
                ),
            )
        }
    }
}

