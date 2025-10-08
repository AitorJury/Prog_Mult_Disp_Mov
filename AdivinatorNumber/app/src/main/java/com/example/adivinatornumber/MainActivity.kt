package com.example.adivinatornumber

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val number : EditText = findViewById<EditText>(R.id.wText)
        val answer : TextView = findViewById<TextView>(R.id.rText)
        val button : Button = findViewById<Button>(R.id.button)
        val ale = Random.nextInt(0, 101)
        var cont = 0

        button.setOnClickListener {
            val num = number.text.toString().toIntOrNull()
                cont++
                if (num == null) {
                    answer.text = "Debe introducir un valor válido (número)"
                } else {
                    if (num < ale) {
                        answer.text = "Es un número mayor"
                    } else if (num > ale) {
                        answer.text = "Es un número menor"
                    } else {
                        answer.text = "¡Enhorabuena! Has acertado con el número $num. Han sido $cont intentos"
                    }
                }
        }
    }
}