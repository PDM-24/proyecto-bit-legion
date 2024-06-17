package com.bitlegion.encantoartesano.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.bitlegion.encantoartesano.R

@Composable
fun ProductRegistration(navController: NavController) {
    var productName by remember { mutableStateOf("") }
    var productPrice by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }

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
        ProductDetails(label = "Nombre del producto", value = productName)
        Spacer(modifier = Modifier.height(16.dp))
        ProductDetails(label = "Precio", value = productPrice)
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
            border = BorderStroke(1.dp, Color.Black),
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
        ) {}
        Spacer(modifier = Modifier.height(16.dp))
        ProductDetails(label = "Ubicación", value = location)

        Spacer(modifier = Modifier.height(40.dp))
        Button(
            onClick = { /*TODO*/ },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFE19390)),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        ) {
            Text(
                text = "Publicar",
                color = Color.Black,
                fontWeight = FontWeight.Bold)
        }
    }
}


@Composable
fun ProductDetails(label: String, value: String) {
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
            onValueChange = {},
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
/*
@Preview
@Composable
fun ProductRegistrationPreview() {
    ProductRegistration()
}*/