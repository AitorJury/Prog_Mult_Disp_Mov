package medina.proyects.cuadrado

import android.content.Context
import model.Cuadrado
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import medina.proyects.cuadrado.R
import model.CuadradoBorde
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        //Creacion de todas las variable por id's
        val btnLeft = findViewById<Button>(R.id.btnLeft)
        val btnUp = findViewById<Button>(R.id.btnUp)
        val btnRight = findViewById<Button>(R.id.btnRight)
        val btnDown = findViewById<Button>(R.id.btnDown)
        val btnSetColor = findViewById<Button>(R.id.btnSetColor)
        val btnPlusSize = findViewById<Button>(R.id.btnPlus)
        val btnMinusSize = findViewById<Button>(R.id.btnMinus)
        val btnBorderColor = findViewById<Button>(R.id.btnBorderColor)


        val vSquare = findViewById<View>(R.id.vBox)

        /*Usamos el metodo post() para que se ejecute este bloque de codigo en el hilo de la interfaz
        de usuario justo despues que se cargue la vista, se construya y se mida
        */

        vSquare.post {
            val initX = vSquare.x.toInt()
            val initY = vSquare.y.toInt()
            val alto = vSquare.height
            val ancho = vSquare.width

            /*
            val cuadrado: Cuadrado =
                Cuadrado(ContextCompat.getColor(this, R.color.red), ancho, alto)
                    .apply {
                        //Reasignamos los valores de x e y
                        //Asignamos los valores de la vista initY al objeto cuadrado y
                        x = initX
                        y = initY
                    }
            */
             //cuadradoBorde se usa para poder cambiar el color del borde usando herencia de la clase Cuadrado
            val cuadrado: CuadradoBorde =
                CuadradoBorde(
                    ContextCompat.getColor(this, R.color.red), ancho, alto,
                    ContextCompat.getColor(this, R.color.black)
                ).apply {
                    //Reasignamos los valores de x e y
                    //Asignamos los valores de la vista initY al objeto cuadrado y
                    x = initX
                    y = initY
                }

            btnBorderColor.setOnClickListener {
                cuadrado.cambiarColorBorde(randomColor())
                // cuadrado.cambiarColorBorde(nuevoColorBorde = CuadradoBorde.ManejoColor.ColorBordeNested())
                updateView(cuadrado, vSquare)
            }

            btnUp.setOnClickListener {
                resetCoordenas(cuadrado, vSquare)
                cuadrado.moverArriba()
                updateView(cuadrado, vSquare)
            }
            btnDown.setOnClickListener {
                resetCoordenas(cuadrado, vSquare)
                cuadrado.moverAbajo()
                updateView(cuadrado, vSquare)
            }
            btnRight.setOnClickListener {
                resetCoordenas(cuadrado, vSquare)
                cuadrado.moverDrch()
                updateView(cuadrado, vSquare)
            }
            btnLeft.setOnClickListener {
                resetCoordenas(cuadrado, vSquare)
                cuadrado.moverIzq()
                updateView(cuadrado, vSquare)
            }
            btnPlusSize.setOnClickListener {
                resetCoordenas(cuadrado, vSquare)
                cuadrado.aumentarTamanio()
                updateView(cuadrado, vSquare)
            }
            btnMinusSize.setOnClickListener {
                resetCoordenas(cuadrado, vSquare)
                cuadrado.disminuirTamanio()
                updateView(cuadrado, vSquare)
            }
            btnSetColor.setOnClickListener {
                cuadrado.color = randomColor()
                updateView(cuadrado, vSquare)
            }
        }
    }

    private fun resetCoordenas(cuadrado: Cuadrado, vBox: View) {
        val newX = vBox.x
        val newY = vBox.y
        cuadrado.y = newY.toInt()
        cuadrado.x = newX.toInt()
    }

    private fun updateView(cuadrado: CuadradoBorde, vSquare: View) {

        //Aqui es donde enlazamos la vista con el objeto
        val params = vSquare.layoutParams
        params.width = cuadrado.ancho
        params.height = cuadrado.alto
        vSquare.layoutParams = params

        //Cambiar el color
       // vBox.setBackgroundColor(cuadrado.color)

        //cambiamos el color del fondo y del borde usando GradientDrawable
        val drawable: GradientDrawable = GradientDrawable()
        //Asignamos color de fondo
        drawable.setColor(cuadrado.color)
        //Asignamos color y ancho del borde
        drawable.setStroke(20,cuadrado.colorBorde)
        vSquare.background = drawable


        //actualizar coordenadas
        vSquare.x = cuadrado.x.toFloat()
        vSquare.y = cuadrado.y.toFloat()

        //aplicar los cambios
        vSquare.requestLayout()

    }

    private fun randomColor(): Int {

        val randomR = Random.nextInt(256)
        val randomG = Random.nextInt(256)
        val randomB = Random.nextInt(256)
        //Construir color


        return Color.rgb(randomR, randomG, randomB)
    }

}
