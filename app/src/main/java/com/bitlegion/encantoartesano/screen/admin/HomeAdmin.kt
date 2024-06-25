package com.bitlegion.encantoartesano.screen.admin

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.bitlegion.encantoartesano.Api.ApiClient
import com.bitlegion.encantoartesano.Api.Product
import com.bitlegion.encantoartesano.MainViewModel
import com.bitlegion.encantoartesano.R
import com.bitlegion.encantoartesano.component.Header
import com.bitlegion.encantoartesano.ui.theme.Aqua
import com.bitlegion.encantoartesano.ui.theme.grayWhite
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
@Composable
fun TiendaUIAdmin(viewModel: MainViewModel, navController: NavHostController) {
    var productos by remember { mutableStateOf(emptyList<Product>()) }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            val response = ApiClient.apiService.getAllProducts()
            if (response.isSuccessful) {
                productos = response.body() ?: emptyList()
                Toast.makeText(context, "Productos cargados exitosamente", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Error al cargar productos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    Column(
        modifier = Modifier
            .background(color = Color(0xFFD0CFBC))
            .fillMaxSize()
    ) {
        // Barra de búsqueda
        Header(viewModel, onSearch = {})

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
                ProductoCard(producto, navController, viewModel, onDeleteProduct = { deletedProduct ->
                    productos = productos.filter { it._id != deletedProduct._id }
                })
            }
        }
    }
}

@Composable
fun ProductoCard(producto: Product, navController: NavHostController, viewModel: MainViewModel, onDeleteProduct: (Product) -> Unit) {
    val isFavorite = viewModel.isProductFavorite(producto)
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    var showDeleteDialog by remember { mutableStateOf(false) }

    if (showDeleteDialog) {
        onDeleteActionDialog(
            onConfirm = {
                coroutineScope.launch {
                    val response = ApiClient.apiService.deleteProduct(""+producto._id)
                    if (response.isSuccessful) {
                        onDeleteProduct(producto)
                        Toast.makeText(context, "Producto eliminado exitosamente", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "Error al eliminar el producto", Toast.LENGTH_SHORT).show()
                    }
                }
                showDeleteDialog = false
            },
            onDismiss = {
                showDeleteDialog = false
            }
        )
    }

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
            IconButton(
                onClick = { showDeleteDialog = true },
                modifier = Modifier
                    .align(Alignment.End)
                    .size(28.dp)
                    .padding(4.dp)
                    .background(Color.White, shape = CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Cerrar",
                    tint = Color.Black
                )
            }
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
                        Icon(imageVector = Icons.Default.Favorite, contentDescription = null, tint = if (isFavorite) Color.Red else Color.White)
                    }
                }
            }
        }
    }
}

@Composable
fun onDeleteActionDialog(onConfirm: () -> Unit, onDismiss: () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier
                .background(Color.White, RoundedCornerShape(8.dp))
                .padding(16.dp)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "¿Estás seguro de eliminar este producto?", color = Color.Black, fontSize = 18.sp)
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = onConfirm,
                       // colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                        shape = RoundedCornerShape(8.dp),
                       //colors =  ButtonDefaults.buttonColors(backgroundColor = Color.White)
                    ) {
                        Text(text = "Eliminar", color = Color.White, fontSize = 16.sp)
                    }
                    Button(
                        onClick = onDismiss,
                       // colors = ButtonDefaults.buttonColors(backgroundColor = Color.Gray),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(text = "Cancelar", color = Color.White, fontSize = 16.sp)
                    }
                }
            }
        }
    }
}
