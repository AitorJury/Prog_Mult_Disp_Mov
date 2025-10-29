package model

import android.graphics.Color

class CuadradoBorde(color: Int, alto: Int, ancho: Int, var colorBorde: Int = Color.BLACK) :
    Cuadrado(color, alto, ancho) {

    //Metodo para cambiar el color del borde
    fun cambiarColorBorde(nuevoColorBorde: Int) {
        colorBorde = nuevoColorBorde
    }


}

