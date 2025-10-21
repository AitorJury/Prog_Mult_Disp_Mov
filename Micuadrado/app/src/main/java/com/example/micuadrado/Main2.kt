package com.example.micuadrado

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import model.Cuadrado

class Main2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.nuevo_relative)

        val cuadradoView: View = findViewById(R.id.cuadrado)
        val cuadrado: Cuadrado = Cuadrado (ContextCompat.getColor(this,R.color.))

        val buttonArriba: Button = findViewById(R.id.buttonArriba)
        val buttonAbajo: Button = findViewById(R.id.buttonAbajo)
        val buttonIzquierda: Button = findViewById(R.id.buttonIzquierda)
        val buttonDerecha: Button = findViewById(R.id.buttonDerecha)
        val buttonTamaño: Button = findViewById(R.id.buttonCambiarTamanio)
        val buttonColor: Button = findViewById(R.id.buttonCambiarColor)
        val buttonResetear: Button = findViewById(R.id.buttonResetear)


    }
}