package com.example.segundamanoproject.ui.pantallas

import android.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.segundamanoproject.data.RepositorioObjetos

@Composable
fun PantallaGale(navController: NavHostController){
    LazyColumn (modifier = Modifier.fillMaxSize().padding(10.dp)) {
        items(RepositorioObjetos.listaObjetos.size){
            index: Int-> val objeto = RepositorioObjetos.listaObjetos[index]
            Card (modifier = Modifier.padding(8.dp).fillMaxSize().clickable{
            }) {
                Row(modifier = Modifier.padding(8.dp)) {
                    Image(painter = painterResource(id = objeto.imagen),
                        objeto.nombre,
                        modifier = Modifier.size(80.dp)
                        )
                    Spacer(modifier = Modifier.width(16.dp))
                    Column(modifier = Modifier.weight(1f)){
                        Text(objeto.nombre, style = MaterialTheme.typography.titleLarge)
                        Text(objeto.descripcion, maxLines = 2)
                    }
                }
            }
        }
        item{
            Spacer(modifier = Modifier.height(32.dp))
            Button(
                onClick = {navController.popBackStack()},
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
            ) {
                Text("Volver al inicio")
            }
        }
    }
}