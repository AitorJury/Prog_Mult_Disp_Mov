package com.example.flashcardsproject.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

/**
 * Configuración de la paleta de colores para el modo noche.
 * Mapea los colores definidos en Color.kt a los roles semánticos de Material 3.
 */
private val DarkColorScheme = darkColorScheme(
    primary = BrandGreen,           // Color para botones y elementos destacados
    background = DarkBackground,    // Color de fondo general de las pantallas
    onBackground = DarkText,        // Color de texto sobre el fondo general
    surface = DarkBackground,       // Superficie de componentes base
    onSurface = DarkText,           // Texto sobre superficies base
    surfaceVariant = DarkCardSurface, // Fondo específico para tarjetas (Cards)
    onSurfaceVariant = DarkText,    // Texto dentro de las tarjetas
    error = AlertCoral,             // Color para acciones destructivas o errores
    secondary = ColdGray            // Color para elementos de menor énfasis
)

/**
 * Configuración de la paleta de colores para el modo claro.
 */
private val LightColorScheme = lightColorScheme(
    primary = BrandGreen,
    background = LightBackground,
    onBackground = LightText,
    surface = LightBackground,
    onSurface = LightText,
    // Se usa una variante del gris con transparencia para fondos suaves de tarjetas
    surfaceVariant = ColdGray.copy(alpha = 0.1f),
    onSurfaceVariant = LightText,
    error = AlertCoral,
    secondary = ColdGray
)

/**
 * Función principal del tema de la aplicación.
 * Envuelve la jerarquía de la UI para aplicar los estilos globales.
 * * @param darkTheme Determina si se aplica el esquema de colores oscuro. Por defecto detecta el sistema.
 * @param content El contenido de la aplicación que recibirá estos estilos.
 */
@Composable
fun FlashcardsProjectTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    // Selección dinámica del esquema de colores según el estado del sistema o preferencia
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography, // Aplica la configuración tipográfica definida en Type.kt
        content = content
    )
}