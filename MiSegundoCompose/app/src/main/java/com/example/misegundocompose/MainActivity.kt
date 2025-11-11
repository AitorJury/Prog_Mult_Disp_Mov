package com.example.misegundocompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.misegundocompose.ui.theme.MiSegundoComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MiSegundoComposeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Antonio",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(text = "Hello $name!", modifier = modifier)
}

@Composable
fun Funcion1() {
    Box (modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center) {
        Greeting(
            name = "Antonio",
        modifier = Modifier.background(Color.Yellow))
        Greeting(
            name = "Android",
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .background(Color.Gray))
    }
}

@Composable
fun Funcion2() {
    Column (
        modifier = Modifier.fillMaxWidth().height(180.dp),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Greeting(
            name = "Aitor",
            modifier = Modifier
                .background(Color.Yellow)
                .weight(4f))
        Greeting(
            name = "Android",
            modifier = Modifier
                .background(Color.Gray)
                .weight(2f))
        Greeting(
            name = "Antonio",
            modifier = Modifier
                .background(Color.Cyan)
                .weight(1f))
    }
}

@Composable
fun Funcion3() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Greeting(
            name = "Aitor",
            modifier = Modifier
                .background(Color.DarkGray))
        Greeting(
            name = "Android",
            modifier = Modifier
                .background(Color.Magenta))
    }
}
@Preview(showBackground = true, widthDp = 200, heightDp = 100)
@Composable
fun Boton() {
    Box (
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Pulsa Aquí",
            modifier = Modifier
                .background(Color.Green)
                .border(
                    width = 2.dp,
                    color = Color.Gray
                )
                .padding(
                    horizontal = 20.dp,
                    vertical = 10.dp
                )
        )
    }
}


// @Preview(showBackground = true, name = "Antonio Preview")
@Composable
fun GreetingPreview() {
    MiSegundoComposeTheme {
        Funcion1()
        Funcion2()
        Funcion3()
    }
}