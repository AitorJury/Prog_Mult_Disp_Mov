package com.example.myfoto

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myfoto.ui.theme.MyFhotoTheme
import kotlin.random.Random


//Para que funcione el Offset se define un objeto Saver
val OffsetSaver = Saver<Offset, Pair<Float, Float>>(
    save = { Pair(it.x, it.y) }, //guardar como pares de float
    restore = { (x, y) -> Offset(x, y) } // restaurar valores
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyFotoTheme {
                MiFoto()
            }
        }
    }
}

@Preview()
@Composable
fun MiFoto() {

    //Declaramos una variable observable para almacenar el color de fondo
    //val colorFondo = remember { mutableStateOf(Color.White) }
    //Otra forma para que en el background no usar el .value
    // var colorFondoActual by remember { mutableStateOf(Color.White) }
    var colorFondoInt by rememberSaveable { mutableStateOf(Color.White.toArgb()) }
    var colorFondo = Color(colorFondoInt)
    var posicionTexto by rememberSaveable(stateSaver = OffsetSaver) {
        mutableStateOf(
            Offset(
                0f,
                0f
            )
        )
    }

    //Calculamos alto y ancho de pantalla
    var anchoPantalla by rememberSaveable { mutableFloatStateOf(0f) }
    var altoPantalla by rememberSaveable { mutableFloatStateOf(0f) }

    //Calculamos alto y ancho de pantalla
    var altoTexto by rememberSaveable { mutableFloatStateOf(0f) }
    var anchoTexto by rememberSaveable { mutableFloatStateOf(0f) }
    var textoCentrado by rememberSaveable { mutableStateOf(false) }




    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
            .background(colorFondo)
            .onGloballyPositioned { coordinates ->

                altoPantalla = coordinates.size.height.toFloat()
                anchoPantalla = coordinates.size.width.toFloat()

            },//sin by remenber seria colorFondo.value

    ) {

        MyImage()

        Text(
            text = "Kotlin",
            fontSize = 50.sp,
            fontFamily = FontFamily.Cursive,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = Color(0xFFE10000),
            modifier =
                Modifier
                    .onGloballyPositioned { coordinates ->
                        altoTexto = coordinates.size.height.toFloat()
                        anchoTexto = coordinates.size.width.toFloat()
                        if (posicionTexto == Offset(0f,0f)) {
                            posicionTexto =
                                Offset(
                                    (anchoPantalla - anchoTexto) / 2,
                                    (altoPantalla - altoTexto) / 2
                                )

                        }
                    }
                    .offset {
                        IntOffset(
                            posicionTexto.x.toInt(),
                            posicionTexto.y.toInt()
                        )
                    }
                    .pointerInput(Unit) {
                        detectDragGestures { change, dragAmount ->
                            change.consume() //--> esto evita que se activen otros eventos al pasar por otros objetos
                            posicionTexto = Offset(
                                posicionTexto.x + dragAmount.x,
                                posicionTexto.y + dragAmount.y
                            )
                        }
                    },
            //.align(Alignment.Center),

        )
        Button(
            onClick = {
                colorFondoInt = randomColor().toArgb()
            },
            Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 40.dp)
                .size(width = 200.dp, height = 75.dp),
            colors = ButtonDefaults.buttonColors(containerColor = colorFondo)

        ) {
            Text(
                text = "Cambiar fondo"
            )
        }
    }
}


fun randomColor(): Color {
    val rojo = Random.nextInt(0, 256)
    val verde = Random.nextInt(0, 256)
    val azul = Random.nextInt(0, 256)
    return Color(rojo, verde, azul)
}


@Composable
fun MyImage() {
    //Almacenar y observar la escala de la imagen
    var escala by rememberSaveable { mutableStateOf(1f) }
    //Posicion de la imagen
    var posicion by rememberSaveable(stateSaver = OffsetSaver) { mutableStateOf(Offset.Zero) }
    var angulo by rememberSaveable { mutableStateOf(0.5f) }
    val escalaAnimada by animateFloatAsState(targetValue = escala)
    val posicionAnimado by animateOffsetAsState(targetValue = posicion)
    val anguloAnimado by animateFloatAsState(targetValue = angulo)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTransformGestures { _, desplazamiento, zoom, rotate ->
                    //Aplicamos el desplazamiento a la posicion
                    posicion += desplazamiento
                    //aplicamos el zoom a la escala
                    escala *= zoom
                    angulo += rotate
                }
            }
            .pointerInput(Unit) {
                detectTapGestures(onDoubleTap = null) {
                    escala = 1f
                    posicion = Offset.Zero
                    angulo = 0f
                }
            },
        contentAlignment = Alignment.Center

    ) {
        Image(
            painter = painterResource(R.drawable.ic_kotlin_dark),
            contentDescription = "Kotling logo",
            modifier = Modifier
                //--> Permite aplicar una escala y una posicion a la imagen
                .graphicsLayer(
                    //Desplazamiento
                    translationX = posicionAnimado.x,
                    translationY = posicionAnimado.y,
                    scaleX = escalaAnimada.coerceIn(1f, 3f),
                    scaleY = escalaAnimada.coerceIn(1f, 3f),
                    rotationZ = anguloAnimado

                )
        )
    }


}