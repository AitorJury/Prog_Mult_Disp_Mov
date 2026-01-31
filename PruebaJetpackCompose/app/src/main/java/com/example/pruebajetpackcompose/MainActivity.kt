package com.example.pruebajetpackcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pruebajetpackcompose.ui.theme.PruebaJetpackComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            // MiPrimeraComposable()
            // SegundaComposable()
            // Tercera()
            Cuarta()
        }
    }
}

/*@Composable
fun MiPrimeraComposable() {
    Row(modifier = Modifier.fillMaxSize().padding(top=40.dp)) {
        Text(text = "Primer fila")
        Spacer(modifier = Modifier.width(35.dp))
        Text(text = "Segunda fila")
    }
    Column(modifier = Modifier.fillMaxSize().padding(top=60.dp)) {
        Text(text = "Esta es mi primera función")
        Text(text = "Aquí escribimos la segunda línea")
    }
}*/

/*@Composable
fun SegundaComposable() {
    Column(modifier = Modifier.fillMaxSize().padding(top=150.dp)) {
        Row() {
            Text(text = "Primer fila")
            Spacer(modifier = Modifier.width(35.dp))
            Text(text = "Segunda fila")
        }
        Spacer(modifier = Modifier.height(35.dp))
        Text(text = "Esta es mi primera función")
        Text(text = "Aquí escribimos la segunda línea")
    }
}*/

/*@Composable
fun Tercera() {
    Box(modifier = Modifier.fillMaxSize().padding(50.dp)){
        Text(text = "Pepe", modifier = Modifier.align(Alignment.TopStart))
        Text(text = "Jose Luis Torrente", modifier = Modifier.align(Alignment.Center))
        Text(text = "Mondongo", modifier = Modifier.align(Alignment.BottomEnd))
    }
}*/

@Composable
fun Cuarta() {
    Box(modifier = Modifier.fillMaxSize().padding(40.dp)) {
        Text(text = "Pepe", modifier = Modifier.align(Alignment.TopStart))
    }
}

/*
// @Preview(showBackground = true, name="Primera y Segunda")
@Composable
fun MiPrimeraySegundaComposablePreview() {
    // MiPrimeraComposable()
    SegundaComposable()
}

// @Preview(showBackground = true, name="Tercera")
@Composable
fun TerceraPreview() {
    Tercera()
}
*/

@Preview(showBackground = true, name="Tabla disposiciones")
@Composable
fun CuartaPreview() {
    Cuarta()
}