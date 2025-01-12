package com.example.frontendtdm.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun FrontendTDMTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = MaterialTheme.colorScheme.copy(
            primary = PrimaryColor,
            secondary = SecondaryColor,
            background = BackgroundColor
        ),
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}