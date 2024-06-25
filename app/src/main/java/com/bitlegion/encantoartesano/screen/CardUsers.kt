package com.bitlegion.encantoartesano.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bitlegion.encantoartesano.R
import com.bitlegion.encantoartesano.ui.theme.ColorCards


@Composable
fun MyCardUser(name: String, password: String, numProductos: Int, age: Int) {
    Card( colors= CardDefaults.cardColors(containerColor = ColorCards),
        modifier= Modifier
        .padding(7.dp)
        .size(width = 320.dp, height = 150.dp)) {
        Row(modifier=Modifier.padding(20.dp), verticalAlignment = Alignment.Top) {
            Image(painter = painterResource(id = R.drawable.profile_icon), contentDescription = "Perfil", Modifier.size(55.dp))
            Column(modifier = Modifier) {
                Text(text = "Nombre de Usuario: $name", fontSize = 16.sp)
                Text(text = "Contrasena: $password", fontSize = 16.sp)
                Text(text = "Productos en venta: $numProductos", fontSize = 16.sp)
                Text(text = "Edad: $age", fontSize = 16.sp)
            }
        }

    }
}


@Preview(showBackground = true)
@Composable
private fun MyCardUserPreview() {
    MyCardUser("Paolo Guitierrez", "Chocolate123", 13, 27)
}

