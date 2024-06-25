package com.bitlegion.encantoartesano.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.bitlegion.encantoartesano.MainViewModel
import com.bitlegion.encantoartesano.R
import com.bitlegion.encantoartesano.component.Header

@Composable
fun ShoppingCartScreen(navController: NavController, viewModel: MainViewModel) {
    val scope = rememberCoroutineScope()
    val cartProducts by remember { mutableStateOf(viewModel.cartProducts) }

    LaunchedEffect(Unit) {
        viewModel.loadCartProducts()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFD3C4B8))
    ) {
        // Call HomeHeader
        Header(viewModel, onSearch = {})

        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Cart items
            Text(
                text = "Carrito de Compra",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn {
                items(cartProducts) { product ->
                    CartItem(
                        itemName = product.nombre,
                        itemPrice = product.precio,
                        itemDescription = product.descripcion,
                        itemImage = rememberAsyncImagePainter(product.imagenes.firstOrNull())
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Subtotal, Shipping, Total
            val subtotal = cartProducts.sumOf { it.precio }
            val shipping = 5
            val total = subtotal + shipping

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Subtotal")
                Text(text = "$$subtotal")
            }
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Envío")
                Text(text = "$$shipping")
            }
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Total", fontWeight = FontWeight.Bold)
                Text(text = "$$total", fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Pay Button
            Button(
                onClick = { navController.navigate("pay") },  // Navegar a la pantalla de pago
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE07A5F))
            ) {
                Text(text = "Pagar", color = Color.White, fontSize = 18.sp)
            }
        }
    }
}

@Composable
fun CartItem(
    itemName: String,
    itemPrice: Double,
    itemDescription: String,
    itemImage: Painter
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Image(
            painter = itemImage,
            contentDescription = "Product Image",
            modifier = Modifier
                .size(60.dp)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = itemName, fontWeight = FontWeight.Bold)
            Text(text = itemDescription, fontSize = 12.sp, color = Color.Gray)
            Text(text = "$$itemPrice", fontWeight = FontWeight.Bold)
        }
    }
}
