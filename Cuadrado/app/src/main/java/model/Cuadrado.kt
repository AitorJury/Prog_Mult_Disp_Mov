package model

class Cuadrado (var color: Int , var ancho: Int, var alto : Int ) {
    // Coordenadas iniciales
    var x : Int = 0
    var y : Int = 0

    //métodos para mover-cambiar el cuadrado
    fun moverArriba(){
        y-= 10 //y = y - 10

    }
    fun moverAbajo(){
        y+=10; //
    }
    fun moverDerecha(){
        x+=10;
    }
    fun moverIzquierda(){
        x-=10;
    }
    fun cambiarTamanio(nuevoAncho: Int, nuevoAlto : Int){
        alto = nuevoAlto
        ancho = nuevoAncho
    }





}