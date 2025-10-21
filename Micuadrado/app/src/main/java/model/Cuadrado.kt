package model;

import android.graphics.Color;

public class Cuadrado(var color:Int, var ancho:Int, var alto:Int) {
    var x: Int=0
    var y: Int=0

    fun moverArriba() {
        y-=10
    }

    fun moverAbajo() {
        y+=10
    }

    fun moverIzquierda() {
        x-=10
    }

    fun moverDerecha() {
        x+=10
    }

    fun cambiarTamaño(nAncho:Int, nAlto:Int) {
        ancho=nAncho
        alto=nAlto
    }


}
