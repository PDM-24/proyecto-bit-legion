package com.bitlegion.encantoartesano.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.bitlegion.encantoartesano.Api.ApiClient
import com.bitlegion.encantoartesano.Api.User
import com.bitlegion.encantoartesano.R
import kotlinx.coroutines.launch

@Composable
fun RegisterScreen(navController: NavHostController) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    var isUsernameValid by remember { mutableStateOf(false) }
    var isPasswordValid by remember { mutableStateOf(false) }
    var isAgeValid by remember { mutableStateOf(false) }
    var isUsernameChecked by remember { mutableStateOf(false) }

    val checkUsername: suspend () -> Unit = {
        val response = ApiClient.apiService.checkUsername(username)
        isUsernameValid = response.isSuccessful && response.body()?.exists == false
        isUsernameChecked = true
    }

    fun checkPassword() {
        isPasswordValid = password.length >= 8 &&
                password.any { it.isUpperCase() } &&
                password.any { it.isLowerCase() } &&
                password.any { it.isDigit() }
    }

    fun checkAge() {
        isAgeValid = age.toIntOrNull()?.let { it >= 18 } ?: false
    }

    LaunchedEffect(username) {
        if (username.isNotBlank()) {
            checkUsername()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF15746E)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        IconButton(
            onClick = { navController.navigate("login") },
            modifier = Modifier
                .align(Alignment.Start)
                .padding(16.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_chevron_left_24),
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

        OutlinedTextField(
            value = username,
            onValueChange = { username = it; isUsernameChecked = false },
            label = { Text("Nombre de Usuario") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                backgroundColor = Color(0xFFD1C8B8),
                focusedBorderColor = Color.Gray,
                unfocusedBorderColor = Color.Gray
            ),
            trailingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_check_24),
                    contentDescription = null,
                    tint = if (isUsernameChecked) {
                        if (isUsernameValid) Color.Green else Color.Red
                    } else {
                        Color.Red
                    }
                )
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "El nombre de usuario es el nombre único con el que te identificarás en la plataforma.",
            style = MaterialTheme.typography.body2.copy(color = Color.White),
            modifier = Modifier.padding(horizontal = 32.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it; checkPassword() },
            label = { Text("Contraseña") },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            placeholder = { Text("Introduce tu contraseña") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                backgroundColor = Color(0xFFD1C8B8),
                focusedBorderColor = Color.Gray,
                unfocusedBorderColor = Color.Gray
            ),
            trailingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_check_24),
                    contentDescription = null,
                    tint = if (isPasswordValid) Color.Green else Color.Red
                )
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Asegúrate de que tu contraseña tenga al menos 8 caracteres, una mayúscula, una minúscula y un número.",
            style = MaterialTheme.typography.body2.copy(color = Color.White),
            modifier = Modifier.padding(horizontal = 32.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = age,
            onValueChange = { age = it; checkAge() },
            label = { Text("Edad") },
            placeholder = { Text("Introduce tu edad") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                backgroundColor = Color(0xFFD1C8B8),
                focusedBorderColor = Color.Gray,
                unfocusedBorderColor = Color.Gray
            ),
            trailingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_check_24),
                    contentDescription = null,
                    tint = if (isAgeValid) Color.Green else Color.Red
                )
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Debes ser mayor de 18 años para registrarte en nuestra plataforma.",
            style = MaterialTheme.typography.body2.copy(color = Color.White),
            modifier = Modifier.padding(horizontal = 32.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                scope.launch {
                    checkUsername()
                    if (isUsernameValid && isPasswordValid && isAgeValid) {
                        val user = User(username, password, age.toInt())
                        val response = ApiClient.apiService.registerUser(user)
                        if (response.isSuccessful) {
                            navController.navigate("login")
                        } else {
                            errorMessage = "Error al crear la cuenta"
                        }
                    }
                }
            },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFD26059)),
            enabled = isUsernameChecked && isUsernameValid && isPasswordValid && isAgeValid,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
        ) {
            Text(text = "Crear Cuenta", color = Color.White)
        }

        if (errorMessage.isNotEmpty()) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = errorMessage, color = Color.Red)
        }

        Spacer(modifier = Modifier.height(8.dp))

        TextButton(onClick = { navController.navigate("login") }) {
            Text(text = "¿Ya tienes cuenta? Iniciar Sesión", color = Color.White)
        }
    }
}
