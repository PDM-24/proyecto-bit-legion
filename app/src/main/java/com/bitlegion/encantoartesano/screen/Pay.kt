package com.bitlegion.encantoartesano.screen

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
// Pay.kt
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.bitlegion.encantoartesano.Api.ApiClient
import com.bitlegion.encantoartesano.Api.PayData
import com.bitlegion.encantoartesano.MainViewModel
import kotlinx.coroutines.launch

@Composable
fun PaymentScreen(navController: NavController, viewModel: MainViewModel) {
    var showDialog by remember { mutableStateOf(false) }
    var selectedPaymentMethod by remember { mutableStateOf<PayData?>(null) }
    val cartProducts = viewModel.cartProducts
    val subtotal = cartProducts.sumOf { it.precio }
    val shipping = 5
    val total = subtotal + shipping
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

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
                PaymentMethodsSection(onPaymentMethodSelected = {
                    selectedPaymentMethod = it
                })
                PaymentDetailsSection(
                    selectedPaymentMethod = selectedPaymentMethod,
                    subtotal = subtotal,
                    shipping = shipping,
                    total = total,
                    onPayNowClick = {
                        coroutineScope.launch {
                            viewModel.checkout()
                            showDialog = true
                        }
                    }
                )
            }

            if (showDialog) {
                PaymentSuccessDialog(onDismiss = {
                    showDialog = false
                    navController.navigate("home")
                })
            }
        }
    )
}

@Composable
fun PaymentDetailsSection(
    selectedPaymentMethod: PayData?,
    subtotal: Double,
    shipping: Int,
    total: Double,
    onPayNowClick: () -> Unit
) {
    var cardHolder by remember { mutableStateOf("") }
    var cardNumber by remember { mutableStateOf("") }
    var expiryDate by remember { mutableStateOf("") }
    var cvv by remember { mutableStateOf("") }
    var saveCardDetails by remember { mutableStateOf(true) }
    val sharedPreferences = LocalContext.current.getSharedPreferences("encanto_artesano_prefs", Context.MODE_PRIVATE)
    val userId = sharedPreferences.getString("user_id", null)
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    // Update fields with selected payment method
    LaunchedEffect(selectedPaymentMethod) {
        selectedPaymentMethod?.let {
            cardHolder = it.titular ?: ""
            cardNumber = it.number ?: ""
            expiryDate = it.fechaVencimiento ?: ""
            cvv = it.cvv ?: ""
        }
    }

    // Validation functions
    fun isCardHolderValid(cardHolder: String): Boolean {
        return cardHolder.isNotEmpty() && cardHolder.matches(Regex("^[a-zA-Z\\s]+$"))
    }

    fun isCardNumberValid(cardNumber: String): Boolean {
        return cardNumber.length == 16 && cardNumber.all { it.isDigit() }
    }

    fun isExpiryDateValid(expiryDate: String): Boolean {
        return expiryDate.isNotEmpty() && expiryDate.matches(Regex("(0[1-9]|1[0-2])/\\d{4}"))
    }

    fun isCvvValid(cvv: String): Boolean {
        return cvv.isNotEmpty() && cvv.length in 3..4 && cvv.all { it.isDigit() }
    }

    fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

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
            modifier = Modifier.fillMaxWidth(),
            isError = !isCardHolderValid(cardHolder)
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = cardNumber,
            onValueChange = { cardNumber = it },
            label = { Text("Número de la tarjeta") },
            placeholder = { Text("XXXX XXXX XXXX XXXX") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            visualTransformation = VisualTransformation.None,
            isError = !isCardNumberValid(cardNumber)
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
                modifier = Modifier.weight(1f),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Ascii),
                visualTransformation = VisualTransformation.None,
                isError = !isExpiryDateValid(expiryDate)
            )

            Spacer(modifier = Modifier.width(8.dp))

            OutlinedTextField(
                value = cvv,
                onValueChange = { cvv = it },
                label = { Text("CVV") },
                placeholder = { Text("Ej. 123") },
                modifier = Modifier.weight(1f),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                visualTransformation = VisualTransformation.None,
                isError = !isCvvValid(cvv)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Mostrar subtotal, envío y total
        Text("Monto total", style = MaterialTheme.typography.subtitle1, color = Color.Black)
        Spacer(modifier = Modifier.height(4.dp))
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

        PaymentButton(onClick = {
            // Validation checks
            when {
                !isCardHolderValid(cardHolder) -> showToast("El titular de la tarjeta no es válido")
                !isCardNumberValid(cardNumber) -> showToast("El número de la tarjeta no es válido")
                !isExpiryDateValid(expiryDate) -> showToast("La fecha de vencimiento no es válida")
                !isCvvValid(cvv) -> showToast("El CVV no es válido")
                else -> {
                    if (saveCardDetails) {
                        coroutineScope.launch {
                            val existingPaymentMethods = ApiClient.apiService.getPaymentMethods(userId!!)
                            if (existingPaymentMethods.isSuccessful) {
                                val existingCards = existingPaymentMethods.body()
                                val isDuplicate = existingCards?.any { it.number == cardNumber } == true

                                if (!isDuplicate) {
                                    val pay = PayData(
                                        _id = null,
                                        titular = cardHolder,
                                        number = cardNumber,
                                        fechaVencimiento = expiryDate,
                                        cvv = cvv,
                                        user = userId
                                    )
                                    val response = ApiClient.apiService.savePayment(pay)
                                    if (response.isSuccessful) {
                                        onPayNowClick.invoke()
                                    } else {
                                        showToast("Error al guardar metodo de pago")
                                    }
                                } else {
                                    showToast("Esta tarjeta ya está guardada")
                                }
                            }
                        }
                    } else {
                        onPayNowClick.invoke()
                    }
                }
            }
        })
    }
}


@Composable
fun PaymentMethodsSection(onPaymentMethodSelected: (PayData) -> Unit) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("encanto_artesano_prefs", Context.MODE_PRIVATE)
    val userId = sharedPreferences.getString("user_id", null)
    var paymentMethods by remember { mutableStateOf<List<PayData>>(emptyList()) }

    fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    LaunchedEffect(userId) {
        userId?.let {
            coroutineScope.launch {
                try {
                    val response = ApiClient.apiService.getPaymentMethods(it)
                    if (response.isSuccessful) {
                        paymentMethods = response.body() ?: emptyList()
                    } else {
                        showToast("Error al obtener métodos de pago")
                    }
                } catch (e: Exception) {
                    showToast("Error de red al obtener métodos de pago")
                }
            }
        }
    }

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

        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(paymentMethods) { paymentMethod ->
                Card(
                    modifier = Modifier
                        .size(100.dp)
                        .padding(4.dp)
                        .clickable { onPaymentMethodSelected(paymentMethod) },
                    shape = RoundedCornerShape(8.dp),
                    elevation = 4.dp
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Text(
                            text = "**** ${paymentMethod.number.takeLast(4)}",
                            style = MaterialTheme.typography.body1,
                            color = Color.Black
                        )
                    }
                }
            }
        }
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
                .background(Color(0xFF008080), RoundedCornerShape(8.dp))
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
