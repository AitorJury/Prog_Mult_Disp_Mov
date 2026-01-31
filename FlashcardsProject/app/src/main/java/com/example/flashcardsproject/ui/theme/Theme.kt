package com.example.flashcardsproject.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

/**
 * Esquema de colores para el Modo Oscuro.
 */
private val DarkColorScheme = darkColorScheme(
    // Color principal de la marca.
    primary = BrandGreen,

    // Color de fondo.
    background = DarkBackground,

    // Color de contraste para textos y elementos gráficos sobre el fondo principal.
    onBackground = DarkText,

    // Superficie base para componentes de interfaz.
    surface = DarkBackground,

    // Color de contraste para elementos.
    onSurface = DarkText,

    // Variante de superficie para elevar visualmente las tarjetas.
    surfaceVariant = DarkCardSurface,

    // Contenido dentro de contenedores surfaceVariant.
    onSurfaceVariant = DarkText,

    // Color para señalización de errores o acciones de borrado.
    error = AlertCoral,

    // Color para elementos decorativos o de menor jerarquía visual.
    secondary = ColdGray
)

/**
 * Esquema de colores para el Modo Claro.
 */
private val LightColorScheme = lightColorScheme(
    // Color de marca.
    primary = BrandGreen,

    // Color de fondo.
    background = LightBackground,

    // Color de contraste para textos y elementos gráficos sobre el fondo principal.
    onBackground = LightText,

    // Superficie base para componentes de interfaz.
    surface = LightBackground,

    // Color de contraste para elementos.
    onSurface = LightText,

    // Variante de superficie con una opacidad reducida del 10%.
    surfaceVariant = ColdGray.copy(alpha = 0.1f),

    // Contenido dentro de contenedores surfaceVariant.
    onSurfaceVariant = LightText,

    // Color para señalización de errores o acciones de borrado.
    error = AlertCoral,

    // Color para elementos decorativos o de menor jerarquía visual.
    secondary = ColdGray
)

/**
 * Componente que define el tema visual de la aplicación. Aplica el sistema de diseño Material 3 de forma global.
 * @param darkTheme Indica si debe forzarse el tema oscuro. Por defecto, utiliza la configuración del sistema.
 * @param content Bloque de código composable que se renderiza bajo este tema.
 */
@Composable
fun FlashcardsProjectTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    // Selección del esquema de colores en función de la preferencia de brillo del usuario.
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        // Configuración de fuentes y pesos de texto.
        typography = Typography,
        content = content
    )
}

object AppSizes {
    val screenPadding = 16.dp
    // Usaremos 16.dp o 20.dp para un look moderno
}