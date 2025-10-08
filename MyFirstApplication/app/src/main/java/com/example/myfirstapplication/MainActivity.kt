package com.example.myfirstapplication

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
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
        val boton : Button = findViewById<Button>(R.id.Mybutton)
        boton.setOnClickListener {
            Toast.makeText(this, "No pulse aquí", Toast.LENGTH_LONG).show()
        }

        val boton2 : Button = findViewById<Button>(R.id.Mybutton2)
        boton2.setOnClickListener {
            Toast.makeText(this, "Good boy", Toast.LENGTH_LONG).show()
        }
    }
}