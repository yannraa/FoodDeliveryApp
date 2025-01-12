package com.example.frontendtdm.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.frontendtdm.utils.LogoutButton
import com.example.frontendtdm.utils.SidebarHeader
import com.example.frontendtdm.utils.SidebarFooter
import com.example.frontendtdm.utils.LogoutButton

@Composable
fun Sidebar(navController: NavHostController) {
    var userName by remember { mutableStateOf("User's Name") }
    var userEmail by remember { mutableStateOf("Example@gmail.com") }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.Gray
    ) {
        Row(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .background(Color.White)
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
                    .weight(0.85f)
            ) {
                SidebarHeader(userName, userEmail, navController)
                Spacer(modifier = Modifier.height(16.dp))
                SidebarFooter(navController)
                Spacer(modifier = Modifier.weight(1f))
                LogoutButton(onClick = { navController.navigate("signup")})
            }

            Spacer(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(8.dp)
                    .background(color = Color(0xFFD9D9D9))
                    .weight(0.15f)
            )
        }
    }
}