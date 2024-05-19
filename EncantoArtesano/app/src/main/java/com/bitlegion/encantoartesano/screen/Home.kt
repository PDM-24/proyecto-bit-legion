package com.bitlegion.encantoartesano.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.bitlegion.encantoartesano.R
import com.bitlegion.encantoartesano.component.Header
import com.bitlegion.encantoartesano.ui.theme.Aqua
import com.bitlegion.encantoartesano.ui.theme.LightGreen
import com.bitlegion.encantoartesano.ui.theme.LightPink
import com.bitlegion.encantoartesano.ui.theme.MainRed
import com.bitlegion.encantoartesano.ui.theme.grayWhite
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


data class Producto(
    val nombre: String,
    val descripcion: String,
    val precio: String
)

@OptIn(ExperimentalLayoutApi::class)

@Composable
fun TiendaUI(scope: CoroutineScope, drawerState: DrawerState) {
    val productos = listOf(
        Producto("Nombre Producto", "Descripción del producto", "$25"),
        Producto("Nombre Producto", "Descripción del producto", "$25"),
        Producto("Nombre Producto", "Descripción del producto", "$25"),
        Producto("Nombre Producto", "Descripción del producto", "$25")
    )

    Column(modifier = Modifier
        .background(color = grayWhite)
        .fillMaxSize()
        ) {
        // Barra de búsqueda

            Header(scope = scope, drawerState = drawerState )

            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text="Productos Destacados",
                style = MaterialTheme.typography.bodySmall.copy(Color.Black),
                fontWeight = FontWeight.Bold ,
                fontSize = 15.sp ,
                modifier = Modifier.padding(8.dp))
            Spacer(modifier = Modifier.height(8.dp))


            FlowRow(
            ) {

                    // Lista de productos
                    productos.forEach { producto ->
                        ProductoCard(producto)
                        Spacer(modifier = Modifier.height(8.dp))


            }

        }

    }
}

@Composable
fun ProductoCard(producto: Producto) {
    Card(
        modifier = Modifier
            .fillMaxWidth(0.5f)
            .fillMaxHeight(0.5f)
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Image(painter = painterResource(id = R.drawable.jarron), contentDescription =  "Background image",
                Modifier
                    .width(width = 110.dp)
                    .height(height = 110.dp))
            Column(
                modifier = Modifier
                    .padding(start = 2.dp)
                    .align(Alignment.Start),
                verticalArrangement = Arrangement.spacedBy(7.dp)
            ) {
                Text(producto.nombre, style = MaterialTheme.typography.titleMedium.copy(Color.Black), fontWeight = FontWeight.Bold)
                Text(producto.descripcion, style = MaterialTheme.typography.bodySmall.copy(Color.Black))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Text(producto.precio, style = MaterialTheme.typography.titleMedium.copy(Color.Black))
                    Spacer(modifier = Modifier.width(105.dp))
                    IconButton(
                        onClick = { /*TODO*/ },
                        modifier = Modifier.size(30.dp),
                        colors = IconButtonDefaults.iconButtonColors(containerColor = Aqua, contentColor = Color.White)

                    ){
                        Icon(imageVector = Icons.Filled.Favorite, contentDescription = "Settings", modifier = Modifier.size(16.dp))
                    }

                }

            }
        }
    }
}
