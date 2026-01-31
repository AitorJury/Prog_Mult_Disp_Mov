package com.example.flashcardsproject.ui.windows

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import com.example.flashcardsproject.ui.theme.AppSizes

/**
 * Pantalla de información técnica, descripción del proyecto y créditos.
 * @param navController Controlador de navegación para gestionar el retorno a la pantalla de origen.
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
                // Márgenes superiores para evitar la superposición.
                .verticalScroll(rememberScrollState())
                .padding(AppSizes.screenPadding)
        ) {
            // Botón de navegación hacia atrás.
            IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier.offset(x = (-12).dp)) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Volver",
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Título de la aplicación.
            Text(
                text = "Proyecto\nFlashcards",
                style = MaterialTheme.typography.titleLarge.copy(fontSize = 32.sp),
                fontWeight = FontWeight.Bold,
                lineHeight = 38.sp,
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Separador.
            Box(
                modifier = Modifier
                    .width(60.dp)
                    .height(4.dp)
                    .background(
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(2.dp)
                    )
            )

            Spacer(modifier = Modifier.height(40.dp))

            // Tarjeta de presentación de autoría y misión del proyecto.
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                ),
                shape = RoundedCornerShape(24.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Desarrollado por",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Text(
                        text = "Aitor Jury Rodríguez",
                        style = MaterialTheme.typography.titleLarge,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Resumen del propósito de la aplicación.
                    Text(
                        text = "Una herramienta educativa diseñada para aprender teoría mediante memoria visual.",
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.height(60.dp))

            // Identificador de versión.
            Text(
                text = "Versión 1.7.3",
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.labelSmall,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.4f)
            )
        }
    }
}