package com.example.segundamanoproject.ui.pantallas

import android.widget.Button
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.segundamanoproject.data.RepositorioObjetos

@Composable
fun PantallaDetalleObj(navController: NavController, id: Int){
    val objeto = RepositorioObjetos.getObjetoById(id)?: return
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Button(onClick = {navController.popBackStack()},
                modifier = Modifier.align(Alignment.Start)
        ) {
            Text(text="Volver")
        }
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            objeto.nombre,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(12.dp))
        Box(modifier = Modifier.fillMaxWidth().height(300.dp), contentAlignment = Alignment.Center){
            Image(painter = painterResource(objeto.imagen), contentDescription = objeto.nombre)
        }
        Spacer(modifier = Modifier.height(24.dp))
    }
}