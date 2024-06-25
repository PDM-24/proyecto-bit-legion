package com.bitlegion.encantoartesano.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.bitlegion.encantoartesano.MainViewModel
import com.bitlegion.encantoartesano.R
import com.bitlegion.encantoartesano.component.Header

@Composable
fun BoughtItems(navController: NavController, viewModel: MainViewModel) {

    LaunchedEffect(Unit) {
        viewModel.loadShoppedProducts()
    }

    val shoppedProducts = viewModel.shoppedProducts

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
                text = "Registro de Compra",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn {
                items(shoppedProducts) { product ->
                    RegisterItem(
                        itemName = product.nombre,
                        itemPrice = product.precio.toInt(),
                        itemDescription = product.descripcion,
                        itemImage = rememberAsyncImagePainter(product.imagenes.firstOrNull())
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun RegisterItem(
    itemName: String,
    itemPrice: Int,
    itemDescription: String,
    itemImage: Painter
) {
    Row(
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
            Text(text = "Comprado")
        }
    }
}
/*
@Preview(showBackground = true)
@Composable
fun ItemsPreview() {
    val viewModel: MainViewModel = viewModel()
    val navController = rememberNavController()
    BoughtItems(navController = rememberNavController(), viewModel = MainViewModel())
}*/
