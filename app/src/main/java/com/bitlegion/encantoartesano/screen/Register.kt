package com.bitlegion.encantoartesano.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bitlegion.encantoartesano.R

@Composable
fun RegisterScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF005B4F)), // Color de fondo
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        IconButton(
            onClick = { /* Acción para volver */ },
            modifier = Modifier
                .align(Alignment.Start)
                .padding(16.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_chevron_left_24), // Reemplaza con tu recurso de ícono de volver
                contentDescription = null,
                tint = Color.White
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Registro",
            style = MaterialTheme.typography.h5.copy(color = Color.White),
            fontSize = 24.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Crea una cuenta para poder iniciar sesión.",
            style = MaterialTheme.typography.body2.copy(color = Color.White)
        )

        Spacer(modifier = Modifier.height(16.dp))

        var username by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var age by remember { mutableStateOf("") }

        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Nombre de Usuario") },
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

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = age,
            onValueChange = { age = it },
            label = { Text("Edad") },
            placeholder = { Text("Introduce tu edad") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                backgroundColor = Color(0xFFD1C8B8), // Color de fondo del TextField
                focusedBorderColor = Color.Gray,
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

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { /* Acción de creación de cuenta */ },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFD26059)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
        ) {
            Text(text = "Crear Cuenta", color = Color.White)
        }

        Spacer(modifier = Modifier.height(8.dp))

        TextButton(onClick = { /* Acción para iniciar sesión */ }) {
            Text(text = "¿Ya tienes cuenta? Iniciar Sesión", color = Color.White)
        }
    }
}
