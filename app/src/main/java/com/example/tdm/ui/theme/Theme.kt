package com.example.tdm.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme

private val LightColorPalette = lightColorScheme(
    primary = PrimaryColor,
    onPrimary = WhiteColor,
    background = BackgroundColor,
    onBackground = BlackColor,
    surface = WhiteColor,
    onSurface = BlackColor,
    secondary = SecondaryTextColor
)

private val DarkColorPalette = darkColorScheme(
    primary = PrimaryColor,
    onPrimary = WhiteColor,
    background = BlackColor,
    onBackground = WhiteColor,
    surface = BlackColor,
    onSurface = WhiteColor,
    secondary = SecondaryTextColor
)

@Composable
fun TDMTheme(
    darkTheme: Boolean = false, // Tu peux configurer cela en fonction des préférences système
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColorPalette else LightColorPalette

    MaterialTheme(
        colorScheme = colors,
        typography = AppTypography,
        shapes = AppShapes,
        content = content
    )
}
