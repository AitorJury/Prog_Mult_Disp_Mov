package medina.proyects.cuadrado

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import android.graphics.Color

import androidx.core.view.WindowInsetsCompat
import model.Cuadrado
import kotlin.random.Random
import kotlin.random.nextInt

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        //identificacion del cuadrado vista
        val cuadradoView : View = findViewById(R.id.cuadrado)
        cuadradoView.post {
            val inicialAncho = cuadradoView.width
            val inicialAlto = cuadradoView.height
            val inicialX = cuadradoView.x
            val inicialY = cuadradoView.y
            //asociar a la vista con el objeto cuadrado
            //ContextCompat es una clase para acceder a recursos
            val cuadrado : Cuadrado = Cuadrado(ContextCompat.getColor(this, R.color.red), inicialAncho, inicialAlto).apply {
                x = inicialX.toInt()
                y  = inicialY.toInt()

            }


            //identifiacacion de botones

            val botonArriba : Button = findViewById<Button>(R.id.buttonArriba)
            val botonAbajo : Button = findViewById<Button>(R.id.buttonAbajo)
            val botonIzquierda : Button = findViewById<Button>(R.id.buttonIzquierda)
            val botonDerecha : Button = findViewById<Button>(R.id.buttonDerecha)
            val botonCambiarColor : Button = findViewById<Button>(R.id.buttonCambiarColor)
            val botonAgrandar : Button = findViewById<Button>(R.id.buttonAgrandar)
            val botonDisminuir : Button = findViewById<Button>(R.id.buttonDisminuir)

            //ponemos botones a la escucha
            botonArriba.setOnClickListener {
                cuadrado.moverArriba()
                actualizarVista(cuadrado, cuadradoView)
            }
            botonAbajo.setOnClickListener {
                cuadrado.moverAbajo()
                actualizarVista(cuadrado, cuadradoView)
            }
            botonDerecha.setOnClickListener {
                cuadrado.moverDerecha()
                actualizarVista(cuadrado, cuadradoView)
            }
            botonIzquierda.setOnClickListener {
                cuadrado.moverIzquierda()
                actualizarVista(cuadrado, cuadradoView)
            }
            botonAgrandar.setOnClickListener {
                cuadrado.ampliar()
                actualizarVista(cuadrado, cuadradoView)
            }
            botonDisminuir.setOnClickListener {
                cuadrado.disminuir()
                actualizarVista(cuadrado, cuadradoView)
            }
            botonCambiarColor.setOnClickListener {
                cuadrado.color = generarColorAleatorio()
                actualizarVista(cuadrado, cuadradoView)
            }
        }
    }

    fun generarColorAleatorio() : Int{
        val random = Random.Default
        val rojo= random.nextInt(256)
        val verde= random.nextInt(256)
        val azul= random.nextInt(256)
        return Color.rgb(rojo,verde,azul)
    }

    private fun actualizarVista(cuadrado : Cuadrado, cuadradoView: View){
        //aqui es donde enlazamos la vista con el objeto
        //la vista actualizará su ancho y alto con los datos del objeto
        cuadradoView.layoutParams.width = cuadrado.ancho
        cuadradoView.layoutParams.height = cuadrado.alto
        //Cambiamos color
        cuadradoView.setBackgroundColor(cuadrado.color)
        //actualizar cordenadas
        cuadradoView.x = cuadrado.x.toFloat()
        cuadradoView.y = cuadrado.y.toFloat()
        //ejecutar los cambios
        cuadradoView.requestLayout()
    }
}