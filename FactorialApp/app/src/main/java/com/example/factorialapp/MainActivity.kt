package com.example.factorialapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

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

        val wText : EditText = findViewById<EditText>(R.id.writeText)
        val aText : TextView = findViewById<TextView>(R.id.answerText)
        val bFact : Button = findViewById<Button>(R.id.buttonFact)

        bFact.setOnClickListener {
            val number = wText.text.toString().toIntOrNull()
            factFun(number)
        }
    }

    private fun factFun(number : Int?) {
        val message = when {
            number >= 0 -> for (val cont = number, cont > 0, cont--) {

            }
            else -> "Introduzca un valor numérico positivo..."
        }

    }
}