package com.bitlegion.encantoartesano.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
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
import com.bitlegion.encantoartesano.R
import com.bitlegion.encantoartesano.component.Header
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.material3.DrawerState
import androidx.navigation.NavController
import com.bitlegion.encantoartesano.MainViewModel

@Composable
fun ShoppingCartScreen(navController: NavController, viewModel: MainViewModel) {
    val scope = rememberCoroutineScope()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFD3C4B8))
    ) {
        // Call HomeHeader
        Header(viewModel)

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

            val items = listOf("Nombre Producto", "Nombre Producto", "Nombre Producto")
            val prices = listOf(25, 25, 25)

            items.forEachIndexed { index, item ->
                CartItem(
                    itemName = item,
                    itemPrice = prices[index],
                    itemDescription = "Descripción del Producto",
                    itemImage = painterResource(id = R.drawable.jarron)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Subtotal, Shipping, Total
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Subtotal")
                Text(text = "$75")
            }
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Envío")
                Text(text = "$5")
            }
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Total", fontWeight = FontWeight.Bold)
                Text(text = "$80", fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Pay Button
            Button(
                onClick = { navController.navigate("pay") },  // Navegar a la pantalla de pago
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFE07A5F))
            ) {
                Text(text = "Pagar", color = Color.White, fontSize = 18.sp)
            }

        }
    }
}

@Composable
fun CartItem(
    itemName: String,
    itemPrice: Int,
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
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { /*TODO: Implement remove item*/ }) {
                Icon(Icons.Filled.KeyboardArrowLeft, contentDescription = "Remove")
            }
            Text(text = "01")
            IconButton(onClick = { /*TODO: Implement add item*/ }) {
                Icon(Icons.Filled.KeyboardArrowRight, contentDescription = "Add")
            }
        }
    }
}

