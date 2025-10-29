package model;

import android.graphics.Color

public class CuadradoBordes(color: Int, ancho: Int, alto: Int, var bordeColor: Int = Color.BLACK): Cuadrado(color,ancho,alto) {
    //metodo nuevo cambiarColorBorde
    fun cambiarColorBorde(nuevoColorBorde: Int) {
        bordeColor = nuevoColorBorde
    }
}
