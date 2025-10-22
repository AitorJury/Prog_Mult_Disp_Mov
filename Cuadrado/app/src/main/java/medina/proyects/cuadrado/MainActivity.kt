package medina.proyects.cuadrado

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

import androidx.core.view.WindowInsetsCompat
import model.Cuadrado

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        //identificacion del cuadrado vista
        val cuadradoView : View = findViewById(R.id.cuadrado)
        //asociar a la vista con el objeto cuadrado
        //ContextCompat es una clase para acceder a recursos
        val cuadrado : Cuadrado = Cuadrado(ContextCompat.getColor(this, R.color.red), 100,100)


        //identifiacacion de botones

        val botonArriba : Button = findViewById<Button>(R.id.buttonArriba)
        val botonAbajo : Button = findViewById<Button>(R.id.buttonAbajo)
        val botonIzquierda : Button = findViewById<Button>(R.id.buttonIzquierda)
        val botonDerecha : Button = findViewById<Button>(R.id.buttonDerecha)
        val botonCambiarColor : Button = findViewById<Button>(R.id.buttonCambiarColor)
        val botonCambiarTamanio : Button = findViewById<Button>(R.id.buttonCambiarTamanio)

        //ponemos botones a la escucha
        botonArriba.setOnClickListener {
            cuadrado.moverArriba()
        }
        botonAbajo.setOnClickListener {
            cuadrado.moverAbajo()
        }
        botonDerecha.setOnClickListener {
            cuadrado.moverDerecha()
        }
        botonIzquierda.setOnClickListener {
            cuadrado.moverIzquierda()
        }
        botonCambiarTamanio.setOnClickListener {
            cuadrado.cambiarTamanio(150,150)
        }
        botonCambiarColor.setOnClickListener {
            cuadrado.color = ContextCompat.getColor(this, R.color.blue)
        }
    }
    private fun actualizarVista(cuadrado : Cuadrado, cuadradoView: View){
        //aqui es donde enlazamos la vista con el objeto
        //la vista actualizará su ancho y alto con los datos del objeto
        cuadradoView.layoutParams.width = cuadrado.ancho
        cuadradoView.layoutParams.height = cuadrado.alto
        //Cambiamos color
        cuadradoView.setBackgroundColor(cuadrado.color)
        //actualizar cordenadas
        
    }
}