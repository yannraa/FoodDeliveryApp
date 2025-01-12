package com.example.frontendtdm.utils


import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import com.example.frontendtdm.utils.MenuItem
import com.example.frontendtdm.R

@Composable
fun SidebarFooter(navController: NavHostController) {
    Column {
        MenuItem(
            painter = painterResource(id = R.drawable.wallet),
            text = "Payment Methods"
        ) {
            navController.navigate("login")
        }
        MenuItem(
            painter = painterResource(id = R.drawable.account),
            text = "Account"
        ) {
            navController.navigate("login")
        }
    }
}