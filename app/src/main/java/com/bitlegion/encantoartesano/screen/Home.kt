package com.bitlegion.encantoartesano.screen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.bitlegion.encantoartesano.Api.ApiClient
import com.bitlegion.encantoartesano.Api.Product
import com.bitlegion.encantoartesano.MainViewModel
import com.bitlegion.encantoartesano.R
import com.bitlegion.encantoartesano.component.Header
import kotlinx.coroutines.launch

@Composable
fun TiendaUI(viewModel: MainViewModel, navController: NavHostController) {
    var productos by remember { mutableStateOf(emptyList<Product>()) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            val response = ApiClient.apiService.getAllActiveProducts()
            if (response.isSuccessful) {
                productos = response.body() ?: emptyList()
            } else {
                // Manejar error
            }
        }
    }

    Column(
        modifier = Modifier
            .background(color = Color(0xFFD0CFBC))
            .fillMaxSize()
    ) {
        // Barra de búsqueda
        Header(viewModel)

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Productos Destacados",
            style = MaterialTheme.typography.bodySmall.copy(Color.Black),
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp,
            modifier = Modifier.padding(8.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .background(color = Color(0xFFD0CFBC))
                .fillMaxSize(),
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(productos) { producto ->
                ProductoCard(producto, navController, viewModel)
            }
        }
    }
}

@Composable
fun ProductoCard(producto: Product, navController: NavHostController, viewModel: MainViewModel) {
    val isFavorite = remember { derivedStateOf { viewModel.isProductFavorite(producto) } }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { navController.navigate("detail/${producto._id}") },
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
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
                painter = rememberAsyncImagePainter(producto.imagenes.firstOrNull()),
                contentDescription = null,
                modifier = Modifier
                    .width(110.dp)
                    .height(110.dp),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .padding(start = 2.dp)
                    .align(Alignment.Start),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = producto.nombre,
                    style = MaterialTheme.typography.titleMedium.copy(Color.Black),
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = producto.descripcion,
                    style = MaterialTheme.typography.bodySmall.copy(Color.Black)
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${producto.precio} USD",
                        style = MaterialTheme.typography.titleMedium.copy(Color.Black)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    IconButton(
                        onClick = {
                            viewModel.toggleProductFavorite(producto)
                        },
                        modifier = Modifier.size(30.dp),
                        colors = IconButtonDefaults.iconButtonColors(containerColor = Color(0xFF2B7A78), contentColor = Color.White)
                    ) {
                        Icon(imageVector = Icons.Default.Favorite, contentDescription = null, tint = if (isFavorite.value) Color.Red else Color.White)
                    }
                }
            }
        }
    }
}
