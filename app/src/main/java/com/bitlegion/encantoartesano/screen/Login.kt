package com.bitlegion.encantoartesano.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.bitlegion.encantoartesano.R
import com.bitlegion.encantoartesano.ui.theme.Aqua

@Composable
fun LoginScreen(navController: NavHostController) {
    val adminUser = "admin"
    val adminPass = "admin123"
    val normalUser = "user"
    val normalPass = "user123"

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Aqua),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo_cuadrado),
            contentDescription = null,
            modifier = Modifier
                .height(300.dp)
                .fillMaxWidth(),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Inicio de Sesión",
            style = MaterialTheme.typography.h5.copy(color = Color.White),
            fontSize = 24.sp,
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Inicio de Sesión con tu cuenta de EncantoArtesano.",
            style = MaterialTheme.typography.body2.copy(color = Color.White)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Usuario",
            style = MaterialTheme.typography.body1.copy(color = Color.White),
            modifier = Modifier.padding(horizontal = 32.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Usuario") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                backgroundColor = Color(0xFFE8DED1),
                focusedBorderColor = Color.Red,
                unfocusedBorderColor = Color.Gray
            ),
            trailingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_check_24),
                    contentDescription = null,
                    tint = Color.Red
                )
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Contraseña",
            style = MaterialTheme.typography.body1.copy(color = Color.White),
            modifier = Modifier.padding(horizontal = 32.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            placeholder = { Text("Introduce tu contraseña") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                backgroundColor = Color(0xFFE8DED1),
                focusedBorderColor = Color.Gray,
                unfocusedBorderColor = Color.Gray
            )
        )

        Spacer(modifier = Modifier.height(50.dp))

        Button(
            onClick = {
                when {
                    username == adminUser && password == adminPass -> navController.navigate("home")
                    username == normalUser && password == normalPass -> navController.navigate("home")
                    else -> errorMessage = "Usuario o contraseña incorrectos"
                }
            },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0XFFE19390)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
        ) {
            Text(text = "Iniciar Sesión", color = Color.White)
        }

        if (errorMessage.isNotEmpty()) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = errorMessage, color = Color.Red)
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(onClick = { navController.navigate("register") }) {
            Text(text = "¿No tienes cuenta? Regístrate", color = Color.White)
        }
    }
}

