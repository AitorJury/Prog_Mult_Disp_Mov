package com.example.segundamanoproject.data

import com.example.segundamanoproject.R

data class EjemploObjeto (val id:Int, val nombre: String, val imagen:Int, val descripcion:String)
object RepositorioObjetos{
    val listaObjetos = listOf(
        EjemploObjeto(1, "obj1", R.drawable.images, "Un mareito"),
        EjemploObjeto(2, "obj2", R.drawable.images, "Un mareito2")
    )
    // funcion que devuelva el objeto por id, si no encuentra la casa devuelva null
    /*fun getObjectById(id:Int): EjemploObjeto?{
        for (objeto in listaObjetos) {
            if(objeto.id == id){
                return objeto
            }
        }
    }*/
    fun getObjetoById(id:Int): EjemploObjeto?{
        return listaObjetos.find { it.id == id }
    }
}