package com.bitlegion.encantoartesano.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.bitlegion.encantoartesano.MainViewModel
import com.bitlegion.encantoartesano.R
import com.bitlegion.encantoartesano.component.Header
import com.bitlegion.encantoartesano.ui.theme.Aqua
import com.bitlegion.encantoartesano.ui.theme.grayWhite
import kotlinx.coroutines.CoroutineScope

data class Producto(
    val nombre: String,
    val descripcion: String,
    val precio: String
)

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TiendaUI(viewModel: MainViewModel, drawerState: DrawerState, navController: NavHostController) {


    val productos = listOf(
        Producto("Nombre Producto 1", "Descripción del producto 1", "$25"),
        Producto("Nombre Producto 2", "Descripción del producto 2", "$30"),
        Producto("Nombre Producto 3", "Descripción del producto 3", "$20"),
        Producto("Nombre Producto 4", "Descripción del producto 4", "$15")
    )

    Column(
        modifier = Modifier
            .background(color = grayWhite)
            .fillMaxSize()
    ) {
        // Barra de búsqueda
        Header(viewModel, drawerState = drawerState)

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Productos Destacados",
            style = MaterialTheme.typography.bodySmall.copy(Color.Black),
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp,
            modifier = Modifier.padding(8.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))

        FlowRow {
            // Lista de productos
            productos.forEach { producto ->
                ProductoCard(producto, navController)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun ProductoCard(producto: Producto, navController: NavHostController) {
    var isFavorite by remember { mutableStateOf(false) }


    Card(
        modifier = Modifier
            .fillMaxWidth(0.5f)
            .fillMaxHeight(0.5f)
            .padding(8.dp)
            .clickable { navController.navigate("detail/${producto.nombre}") },
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
            Image(
                painter = painterResource(id = R.drawable.jarron), contentDescription = "Background image",
                Modifier
                    .width(110.dp)
                    .height(110.dp)
            )
            Column(
                modifier = Modifier
                    .padding(start = 2.dp)
                    .align(Alignment.Start),
                verticalArrangement = Arrangement.spacedBy(7.dp)
            ) {
                Text(
                    producto.nombre, style = MaterialTheme.typography.titleMedium.copy(Color.Black), fontWeight = FontWeight.Bold
                )
                Text(
                    producto.descripcion, style = MaterialTheme.typography.bodySmall.copy(Color.Black)
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        producto.precio, style = MaterialTheme.typography.titleMedium.copy(Color.Black)
                    )
                    Spacer(modifier = Modifier.width(105.dp))
                    IconButton(
                        onClick = { isFavorite = !isFavorite  },
                        modifier = Modifier.size(30.dp),
                        colors = IconButtonDefaults.iconButtonColors(containerColor = Aqua, contentColor = Color.White)
                    ) {
                        Icon(imageVector = Icons.Filled.Favorite,
                            contentDescription = "Settings",
                            tint = if (isFavorite) Color.Red else Color.White,
                            modifier = Modifier.size(16.dp))
                    }
                }
            }
        }
    }
}

