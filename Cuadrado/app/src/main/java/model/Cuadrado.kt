package model

open class Cuadrado(var color: Int, var ancho: Int, var alto: Int) {
    //Cordenadas iniciales
    var x = 0
    var y = 0

    //metodos para mover-cambiar el cuadrado
    fun moverArriba() {
        y -= 50
    }

    fun moverAbajo() {
        y += 50
    }

    fun moverDrch() {
        x += 50
    }

    fun moverIzq() {
        x -= 50
    }

    fun aumentarTamanio() {
        ancho += 50
        alto += 50
    }

    fun disminuirTamanio() {
        ancho -= 50
        alto -= 50
    }
}