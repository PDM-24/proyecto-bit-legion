package com.bitlegion.encantoartesano.screen


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
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
import com.bitlegion.encantoartesano.R
import com.bitlegion.encantoartesano.ui.theme.Aqua

@Composable
fun LoginScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Aqua),
            //.background(Color(0xFF005B4F)), // Color de fondo
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo_cuadrado), // Reemplaza con tu recurso de imagen
            contentDescription = null,
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth(),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Inicio de Sesión",
            style = MaterialTheme.typography.h5.copy(color = Color.White),
            fontSize = 24.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Inicio de Sesión con tu cuenta de EncantoArtesano.",
            style = MaterialTheme.typography.body2.copy(color = Color.White)
        )

        Spacer(modifier = Modifier.height(16.dp))

        var username by remember { mutableStateOf("EncantoArtesano") }
        var password by remember { mutableStateOf("") }

        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Usuario") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                backgroundColor = Color(0xFFD1C8B8), // Color de fondo del TextField
                focusedBorderColor = Color.Red,
                unfocusedBorderColor = Color.Gray
            ),
            trailingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_check_24), // Ícono de verificación
                    contentDescription = null,
                    tint = Color.Red
                )
            }
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
                backgroundColor = Color(0xFFD1C8B8), // Color de fondo del TextField
                focusedBorderColor = Color.Gray,
                unfocusedBorderColor = Color.Gray
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { /* Acción de inicio de sesión */ },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFD26059)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
        ) {
            Text(text = "Iniciar Sesión", color = Color.White)
        }

        Spacer(modifier = Modifier.height(8.dp))

        TextButton(onClick = { /* Acción de registro */ }) {
            Text(text = "¿No tienes cuenta? Regístrate", color = Color.White)
        }
    }
}
