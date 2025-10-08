package com.example.mayoredad

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

        var num : EditText = findViewById<EditText>(R.id.editText)
        val verify : Button = findViewById<Button>(R.id.buttonVal)
        val ans : TextView = findViewById<TextView>(R.id.answer)
        val men : Button = findViewById<Button>(R.id.buttonMen)
        val may : Button = findViewById<Button>(R.id.buttonMas)

        verify.setOnClickListener {
            var number = num.text.toString().toIntOrNull()
            if (number == null || number < 0) {
                ans.text = "Introduzca un valor numérico entero positivo por favor..."
            } else {
                if (number < 18) {
                    ans.text = "Si usted tiene $number años, es menor de edad"
                } else if (number == 18) {
                    ans.text = "Si usted tiene $number años, tiene justo la mayoría de edad"
                } else {
                    ans.text = "Si usted tiene $number años, lleva siendo ${number - 18} años mayor de edad"
                }
            }
        }

        men.setOnClickListener {
            var number = num.text.toString().toIntOrNull()
            if (number == null) {
                ans.text = "Introduzca un valor númerico..."
            } else {
                number--
                num.setText(number.toString())
                actMessage(num.text.toString().toInt(), ans)
            }
        }

        may.setOnClickListener {
            var number = num.text.toString().toIntOrNull()
            if (number == null) {
                ans.text = "Introduzca un valor númerico..."
            } else {
                number++
                num.setText(number.toString())
                actMessage(num.text.toString().toInt(), ans)
            }
        }
    }

    private fun actMessage(age : Int, num: TextView) {
        val message = when {
            age == null -> "Introduzca un valor numérico"
            age < 18 -> "Menor de edad"
            age > 18 -> "Mayor de edad"
            else -> "Justo 18 años"
        }
        num.text = message
    }
}