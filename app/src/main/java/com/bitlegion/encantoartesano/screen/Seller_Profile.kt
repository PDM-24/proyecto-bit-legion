package com.bitlegion.encantoartesano.screen

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
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.bitlegion.encantoartesano.Api.ApiClient
import com.bitlegion.encantoartesano.Api.Product
import com.bitlegion.encantoartesano.Api.User
import com.bitlegion.encantoartesano.R
import kotlinx.coroutines.launch

@Composable
fun SellerProfileScreen(navController: NavHostController, userId: String) {
    var user by remember { mutableStateOf<User?>(null) }
    var products by remember { mutableStateOf<List<Product>>(emptyList()) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(userId) {
        scope.launch {
            val userResponse = ApiClient.apiService.getUserById(userId)
            if (userResponse.isSuccessful) {
                user = userResponse.body()
            } else {
                // Manejar error
            }

            val productsResponse = ApiClient.apiService.getUserProducts(userId)
            if (productsResponse.isSuccessful) {
                products = productsResponse.body() ?: emptyList()
            } else {
                // Manejar error
            }
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF2B7A78))
            .padding(16.dp)
    ) {
        item {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_chevron_left_24),
                    contentDescription = null,
                    tint = Color.White
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Perfil del Vendedor",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.fillMaxWidth(),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
            Spacer(modifier = Modifier.height(16.dp))
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .fillMaxWidth()
            ) {
                Image(
                    painter = rememberAsyncImagePainter(user?.profilePicture),
                    contentDescription = null,
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            user?.let {
                PerfilLabel(label = "Nombre", value = it.username)
                Spacer(modifier = Modifier.height(16.dp))
                PerfilLabel(label = "Edad", value = it.edad.toString())
            }
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = "Productos en venta",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
        item {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .height(300.dp)
                    .background(color = Color(0xFFD0CFBC)),
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(products) { producto ->
                    ProductoCarddd(producto, navController)
                }
            }
        }
    }
}

@Composable
fun PerfilLabell(label: String, value: String, isPassword: Boolean = false) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = value,
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0XFFD0CFCB), RoundedCornerShape(8.dp)),
            singleLine = true,
            readOnly = true,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = Color.Black,
                disabledTextColor = Color.Black,
                focusedBorderColor = Color.Black,
                unfocusedBorderColor = Color.Gray,
                disabledBorderColor = Color.Gray,
                backgroundColor = Color(0XFFD0CFCB)
            ),
            visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None
        )
    }
}

@Composable
fun ProductoCarddd(producto: Product, navController: NavHostController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { navController.navigate("detail/${producto._id}") },
        elevation = 8.dp,
        shape = RoundedCornerShape(8.dp)
    ) {
        Column {
            Image(
                painter = rememberAsyncImagePainter(producto.imagenes.firstOrNull()),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = producto.nombre,
                modifier = Modifier.padding(8.dp),
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Text(
                text = producto.descripcion,
                modifier = Modifier.padding(horizontal = 8.dp),
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}


@Composable
fun ReadOnlyTextFieldWithLabel(label: String, value: String, isPassword: Boolean = false) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = value,
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0XFFD0CFCB), RoundedCornerShape(8.dp)),
            singleLine = true,
            readOnly = true,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = Color.Black,
                disabledTextColor = Color.Black,
                focusedBorderColor = Color.Black,
                unfocusedBorderColor = Color.Gray,
                disabledBorderColor = Color.Gray,
                backgroundColor = Color(0XFFD0CFCB)
            ),
            visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None
        )
    }
}

