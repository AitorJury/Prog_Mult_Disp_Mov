package com.example.micuadrado

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
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
                Toast.makeText(this, "No se puede mover más hacia arriba", Toast.LENGTH_SHORT).show()
            } else {
                square.y = 0f
            }
        }

        downButton.setOnClickListener {
            val step = 20
            val oldY = square.y
            val maxY = (parent.height - square.height).toFloat()

            if (oldY + step <= maxY) {
                square.y = oldY + step
            } else if (oldY.toFloat() == maxY) {
                Toast.makeText(this, "No se puede mover más hacia abajo", Toast.LENGTH_SHORT).show()
            } else {
                square.y = maxY
            }
        }

        leftButton.setOnClickListener {
            val step = 20
            val oldX = square.x

            if (oldX - step >= 0) {
                square.x = oldX - step
            } else if (oldX.toInt() == 0) {
                Toast.makeText(this, "No se puede mover más hacia la izquierda", Toast.LENGTH_SHORT).show()
            } else {
                square.x = 0f
            }
        }

        rightButton.setOnClickListener {
            val step = 20
            val oldX = square.x
            val maxX = (parent.width - square.width).toFloat()

            if (oldX + step <= maxX) {
                square.x = oldX + step
            } else if (oldX.toFloat() == maxX) {
                Toast.makeText(this, "No se puede mover más hacia la derecha", Toast.LENGTH_SHORT).show()
            } else {
                square.x = maxX
            }
        }

        enlargeButton.setOnClickListener {
            val step = 20
            val newWidth = square.width + step
            val newHeight = square.height + step
            val maxWidth = parent.width
            val maxHeight = parent.height

            if (newWidth <= maxWidth && newHeight <= maxHeight) {
                square.layoutParams.width = newWidth
                square.layoutParams.height = newHeight
                square.requestLayout()
            } else {
                Toast.makeText(this, "El cuadrado no puede ser más grande", Toast.LENGTH_SHORT).show()
            }
        }

        decreaseButton.setOnClickListener {
            val step = 20
            val newWidth = square.width - step
            val newHeight = square.height - step

            if (newWidth >= 50 && newHeight >= 50) {
                square.layoutParams.width = newWidth
                square.layoutParams.height = newHeight
                square.requestLayout()
            } else {
                Toast.makeText(this, "El cuadrado no puede ser más pequeño", Toast.LENGTH_SHORT).show()
            }
        }

        colorButton.setOnClickListener {
            val colors = listOf(
                "#900F0F", // rojo oscuro
                "#0F7B90", // azul
                "#0F900F", // verde
                "#FFD700", // dorado
                "#FF8C00", // naranja
                "#800080"  // morado
            )

            val currentColor = (square.background as? android.graphics.drawable.ColorDrawable)?.color
            val currentIndex = colors.indexOfFirst {
                android.graphics.Color.parseColor(it) == currentColor
            }

            val nextIndex = if (currentIndex == -1 || currentIndex == colors.size - 1) 0 else currentIndex + 1
            square.setBackgroundColor(android.graphics.Color.parseColor(colors[nextIndex]))
        }

        resetButton.setOnClickListener {
            square.layoutParams.width = 150
            square.layoutParams.height = 150
            square.requestLayout()
            square.setBackgroundColor(android.graphics.Color.parseColor("#900F0F"))

            square.post {
                val parentWidth = parent.width
                val parentHeight = parent.height
                val squareWidth = square.width
                val squareHeight = square.height

                square.x = ((parentWidth - squareWidth) / 2).toFloat()
                square.y = ((parentHeight - squareHeight) / 2).toFloat()
            }
        }
    }
}