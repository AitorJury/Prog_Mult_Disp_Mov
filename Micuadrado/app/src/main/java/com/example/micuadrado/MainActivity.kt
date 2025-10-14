package com.example.micuadrado

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Declaración de cuadrado y de las dimensiones de los límites
        val square: View = findViewById(R.id.square)
        val parent = square.parent as View
        val parentWidth = parent.width
        val parentHeight = parent.height
        val squareWidth = square.width
        val squareHeight = square.height

        // Declaración de botones
        val upButton: Button = findViewById(R.id.upButton)
        val downButton: Button = findViewById(R.id.downButton)
        val leftButton: Button = findViewById(R.id.leftButton)
        val rightButton: Button = findViewById(R.id.rightButton)
        val enlargeButton: Button = findViewById(R.id.enlargeButton)
        val decreaseButton: Button = findViewById(R.id.decreaseButton)
        val colorButton: Button = findViewById(R.id.colorButton)
        val resetButton: Button = findViewById(R.id.resetButton)

        upButton.setOnClickListener {
            val step = 20
            val oldY = square.y

            if (oldY - step >= 0) {
                square.y = oldY - step
            } else if (oldY.toInt() == 0) {

            }
        }

        downButton.setOnClickListener {

        }

        leftButton.setOnClickListener {

        }

        rightButton.setOnClickListener {

        }

        enlargeButton.setOnClickListener {

        }

        decreaseButton.setOnClickListener {

        }

        colorButton.setOnClickListener {

        }

        resetButton.setOnClickListener {

        }
    }
}