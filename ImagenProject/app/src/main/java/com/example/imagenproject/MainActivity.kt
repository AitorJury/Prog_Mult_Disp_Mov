package com.example.imagenproject

import android.graphics.Paint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.example.imagenproject.R.drawable.logo
import com.example.imagenproject.ui.theme.ImagenProjectTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ImagenProjectTheme {
                Imagen()
            }
        }
    }
}

@Preview(showBackground = false)
@Composable
fun Imagen() {
    // Declaramos una variable observable para almacenar el color de fondo
    // val colorFondo = remember { mutableStateOf(Color.White) }
    var colorFondo by remember { mutableStateOf(Color.White) }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
            .background(colorFondo)
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Imagen de mi futura empresa",
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxSize()
        )
        Text(
            text = "El Logo de mi Futura Empresa",
            color = Color.Gray,
            fontSize = 23.sp,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Italic,
            textAlign = TextAlign.Center,
            modifier = Modifier.align(Alignment.Center)
        )
        Button(
            onClick = {colorFondo = colorAleatorio()},
            modifier = Modifier.align(Alignment.BottomEnd)

        ) {
            Text(text = "Cambiar fondo")
        }
    }
}

fun colorAleatorio(): Color {
    val rojo = kotlin.random.Random.nextFloat()
    // val rojoN = (0..255).random().toFloat()
    val verde = kotlin.random.Random.nextFloat()
    val azul = kotlin.random.Random.nextFloat()

    return Color(rojo, verde, azul)
}