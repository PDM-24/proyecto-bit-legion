package com.bitlegion.encantoartesano.screen

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.bitlegion.encantoartesano.Api.ApiClient
import com.bitlegion.encantoartesano.Api.Product
import com.bitlegion.encantoartesano.R
import kotlinx.coroutines.launch
import android.widget.Toast
import androidx.compose.ui.res.painterResource
import coil.compose.rememberAsyncImagePainter
import java.util.Date

@Composable
fun ProductRegistration(navController: NavController, context: Context, userId: String) {
    var productName by remember { mutableStateOf("") }
    var productPrice by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val coroutineScope = rememberCoroutineScope()

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        imageUri = uri
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF2B7A78))
            .padding(16.dp)
    ) {
        IconButton(onClick = { navController.navigate("home") }) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_chevron_left_24),
                contentDescription = null,
                tint = Color.White
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Publicar Producto",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(40.dp))
        ProductDetails(label = "Nombre del producto", value = productName, onValueChange = { productName = it })
        Spacer(modifier = Modifier.height(16.dp))
        ProductDetails(label = "Precio", value = productPrice, onValueChange = { productPrice = it })
        Spacer(modifier = Modifier.height(16.dp))
        ProductDetails(label = "Descripción", value = description, onValueChange = { description = it })
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Imagen del Producto",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(8.dp))
        Card(
            shape = RoundedCornerShape(8.dp),
            backgroundColor = Color(0xFFD0CFBC),
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .clickable { launcher.launch("image/*") }
        ) {
            if (imageUri != null) {
                Image(
                    painter = rememberAsyncImagePainter(imageUri),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            } else {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Seleccionar Imagen", color = Color.Black)
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        ProductDetails(label = "Ubicación", value = location, onValueChange = { location = it })
        Spacer(modifier = Modifier.height(40.dp))
        Button(
            onClick = {
                coroutineScope.launch {
                    val product = Product(
                        _id = null,
                        nombre = productName,
                        descripcion = description,
                        precio = productPrice.toDoubleOrNull() ?: 0.0,
                        ubicacion = location,
                        imagenes = listOf(imageUri.toString()),
                        fecha = Date(),
                        user = userId
                    )
                    val response = ApiClient.apiService.postProduct(product)
                    if (response.isSuccessful) {
                        navController.navigate("home")
                        Toast.makeText(context, "Producto publicado", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "Error al publicar el producto", Toast.LENGTH_SHORT).show()
                    }
                }
            },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFE19390)),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Publicar", color = Color.Black, fontWeight = FontWeight.Bold)
        }
    }
}


@Composable
fun ProductDetails(label: String, value: String, onValueChange: (String) -> Unit) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFD0CFBC), RoundedCornerShape(8.dp)),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = Color.Black,
                focusedBorderColor = Color.Black,
                unfocusedBorderColor = Color.Black,
                disabledBorderColor = Color.Gray,
                backgroundColor = Color(0xFFD0CFBC)
            )
        )
    }
}

