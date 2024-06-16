package com.bitlegion.encantoartesano.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.bitlegion.encantoartesano.R
// Pay.kt
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
@Composable
fun PaymentScreen(navController: NavController) {
    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Pagar") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                backgroundColor = Color(0xFF008080),
                contentColor = Color.White
            )
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .background(Color.White)
            ) {
                PaymentMethodsSection()
                PaymentDetailsSection(
                    onPayNowClick = { showDialog = true }
                )
            }

            if (showDialog) {
                PaymentSuccessDialog(onDismiss = { showDialog = false })
            }
        }
    )
}

@Composable
fun PaymentMethodsSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF008080))
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Métodos de pago", style = MaterialTheme.typography.h6, color = Color.White)
            TextButton(onClick = { /* Ver todos los métodos de pago */ }) {
                Text("Ver todos", color = Color.White)
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            listOf(
                R.drawable.visa,
                R.drawable.mastercard,
                R.drawable.american_express
            ).forEach { paymentMethod ->
                Card(
                    modifier = Modifier
                        .size(100.dp)
                        .padding(4.dp),
                    shape = RoundedCornerShape(8.dp),
                    elevation = 4.dp
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Image(
                            painter = painterResource(id = paymentMethod),
                            contentDescription = "Payment Method",
                            modifier = Modifier.size(90.dp)
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentDetailsSection(onPayNowClick: () -> Unit) {
    var cardHolder by remember { mutableStateOf("") }
    var cardNumber by remember { mutableStateOf("") }
    var expiryDate by remember { mutableStateOf("") }
    var cvv by remember { mutableStateOf("") }
    var saveCardDetails by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text("Tus datos de pago", style = MaterialTheme.typography.subtitle1, color = Color.Black)
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

        Text("Monto total", style = MaterialTheme.typography.subtitle1, color = Color.Black)

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            "$80.00 USD",
            style = MaterialTheme.typography.h5,
            color = Color(0xFF008080)
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

        PaymentButton(onClick = onPayNowClick)
    }
}

@Composable
fun PaymentButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        shape = RoundedCornerShape(24.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0XFFE19390))
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

@Composable
fun PaymentSuccessDialog(onDismiss: () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier
                .background(Color(0xFF00C853), RoundedCornerShape(8.dp))
                .padding(16.dp)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Pago exitoso", color = Color.White, fontSize = 18.sp)
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = onDismiss,
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0XFFE19390)),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(text = "Aceptar", color = Color.White, fontSize = 16.sp)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
        PaymentScreen(navController = rememberNavController())
}