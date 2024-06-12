package com.bitlegion.encantoartesano.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bitlegion.encantoartesano.R
// Pay.kt
import androidx.navigation.NavController

@Composable
fun PaymentScreen(navController: NavController) {
    var cardHolder by remember { mutableStateOf("") }
    var cardNumber by remember { mutableStateOf("") }
    var expiryDate by remember { mutableStateOf("") }
    var cvv by remember { mutableStateOf("") }
    var saveCardDetails by remember { mutableStateOf(true) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Pagar") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {  // Navegar hacia atrás
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                backgroundColor = Color(0xFF004D40),
                contentColor = Color.White,
                actions = {
                    IconButton(onClick = { /* Handle action */ }) {
                        //Icon(painter = painterResource(id = R.drawable.baseline_chevron_left_24), contentDescription = "More Actions")
                    }
                }
            )
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Métodos de pago", style = MaterialTheme.typography.h6)
                    TextButton(onClick = { /* Ver todos los métodos de pago */ }) {
                        Text("Ver todos", color = Color(0xFF004D40))
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    listOf(R.drawable.jarron, R.drawable.jarron, R.drawable.jarron, R.drawable.jarron).forEach { paymentMethod ->
                        Image(
                            painter = painterResource(id = paymentMethod),
                            contentDescription = "Payment Method",
                            modifier = Modifier.size(60.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text("Tus datos de pago", style = MaterialTheme.typography.subtitle1)

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = cardHolder,
                    onValueChange = { cardHolder = it },
                    label = { Text("Titular de la tarjeta") },
                    placeholder = { Text("Ej. Juan Roberto") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = cardNumber,
                    onValueChange = { cardNumber = it },
                    label = { Text("Número de la tarjeta") },
                    placeholder = { Text("XXXX XXXX XXXX XXXX") },
                    modifier = Modifier.fillMaxWidth(),
                    visualTransformation = VisualTransformation.None
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    OutlinedTextField(
                        value = expiryDate,
                        onValueChange = { expiryDate = it },
                        label = { Text("Fecha de vencimiento") },
                        placeholder = { Text("MM/YYYY") },
                        modifier = Modifier.weight(1f)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    OutlinedTextField(
                        value = cvv,
                        onValueChange = { cvv = it },
                        label = { Text("CVV") },
                        placeholder = { Text("Ej. 123") },
                        modifier = Modifier.weight(1f),
                        visualTransformation = VisualTransformation.None
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    "Monto total",
                    style = MaterialTheme.typography.subtitle1
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    "$80.00 USD",
                    style = MaterialTheme.typography.h5,
                    color = Color(0xFF00C853)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = saveCardDetails,
                        onCheckedChange = { saveCardDetails = it }
                    )
                    Text("Guardar datos para futuras compras")
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { /* Handle payment */ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = RoundedCornerShape(24.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFDD2C00))
                ) {
                    Icon(
                        Icons.Filled.Lock,
                        contentDescription = "Lock",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Pagar ahora", color = Color.White)
                }
            }
        }
    )
}
