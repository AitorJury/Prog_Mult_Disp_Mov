package model

import android.graphics.Color

class CuadradoBorde(
    color: Int,
    alto: Int,
    ancho: Int,
    var colorBorde: Int = Color.BLACK) :
    Cuadrado(color, alto, ancho) {

    //Metodo para cambiar el color del borde aleatorio
    fun cambiarColorBorde(nuevoColorBorde: Int) {
        colorBorde = nuevoColorBorde
    }

    // Clase nested
    class ManejoColor {
        companion object{
            val rojo = Color.RED
            val azul = Color.BLUE
            val amarillo = Color.YELLOW
            val verde = Color.GREEN
            fun ColorBordeNested(): Int {
                val listaColores = listOf(
                    rojo,
                    azul,
                    amarillo,
                    verde
                )
                return listaColores.random()
            }
        }
    }
}