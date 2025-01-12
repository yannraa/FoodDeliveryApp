package com.example.projetv1.Models

import androidx.compose.ui.graphics.vector.ImageVector

// Structure of bottom Navigation Bar
data class NavItem(
    val label:String,
    val icon:ImageVector,
    val onClick : () -> Unit,
)