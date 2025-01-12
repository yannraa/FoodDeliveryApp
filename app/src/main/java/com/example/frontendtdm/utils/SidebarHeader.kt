package com.example.frontendtdm.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.frontendtdm.R

@Composable
fun SidebarHeader(userName: String, userEmail: String, navController: NavHostController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
                .background(color = Color(0xFFD9D9D9)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = userName.firstOrNull()?.toString() ?: "U",
                style = TextStyle(color = Color.White, fontSize = 32.sp, fontWeight = FontWeight.Bold),
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = userName,
                style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold)
            )

            Text(
                text = userEmail,
                style = TextStyle(fontSize = 14.sp, color = Color.Gray)
            )
        }

        Icon(
            imageVector = Icons.Default.Edit,
            contentDescription = "Edit",
            tint = Color.Black,
            modifier = Modifier
                .size(24.dp)
                .clickable {
                    navController.navigate("login")
                }
        )
    }
}