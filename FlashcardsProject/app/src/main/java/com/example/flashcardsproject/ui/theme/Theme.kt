package com.example.flashcardsproject.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = BrandGreen,
    background = DarkBackground,
    onBackground = DarkText,
    surface = DarkBackground,
    onSurface = DarkText,
    surfaceVariant = DarkCardSurface,
    onSurfaceVariant = DarkText,
    error = AlertCoral,
    secondary = ColdGray
)

private val LightColorScheme = lightColorScheme(
    primary = BrandGreen,
    background = LightBackground,
    onBackground = LightText,
    surface = LightBackground,
    onSurface = LightText,
    surfaceVariant = ColdGray.copy(alpha = 0.1f),
    onSurfaceVariant = LightText,
    error = AlertCoral,
    secondary = ColdGray
)

@Composable
fun FlashcardsProjectTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}