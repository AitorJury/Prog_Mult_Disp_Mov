package com.example.flashcardsproject.ui.windows

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

/**
 * Ventana de información y créditos del proyecto.
 * Presenta la autoría y el propósito pedagógico de la aplicación con un diseño minimalista.
 *
 * @param navController Controlador de navegación para regresar al menú principal.
 */
@Composable
fun InfoWindow(navController: NavHostController) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {
            // Botón de salida para volver a la pila de navegación anterior
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Volver",
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.height(40.dp))

            // Título de la aplicación con espaciado entre líneas personalizado
            Text(
                text = "Proyecto\nFlashcards",
                style = MaterialTheme.typography.displayMedium,
                fontWeight = FontWeight.Bold,
                lineHeight = 45.sp,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Detalle decorativo: línea de acento horizontal que refuerza la identidad visual (branding)
            Box(
                modifier = Modifier
                    .width(60.dp)
                    .height(4.dp)
                    .background(
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(2.dp)
                    )
            )

            Spacer(modifier = Modifier.height(48.dp))

            /**
             * Tarjeta de créditos del desarrollador.
             * Utiliza 'surfaceVariant' con transparencia para una integración estética con el fondo.
             */
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Etiqueta de función (Label)
                    Text(
                        text = "Desarrollado por",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.primary
                    )

                    // Nombre del autor con énfasis tipográfico
                    Text(
                        text = "Aitor Jury Rodríguez",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // Descripción del propósito de la herramienta
                    Text(
                        text = "Una herramienta educativa diseñada para dominar la teoría que necesiten mediante memoria visual.",
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center,
                        lineHeight = 20.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            // Espaciador flexible (weight 1f) que empuja el texto de versión hacia la parte inferior
            Spacer(modifier = Modifier.weight(1f))

            // Información de versión y control de calidad
            Text(
                text = "Versión 1.0.0",
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.labelSmall,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
            )
        }
    }
}