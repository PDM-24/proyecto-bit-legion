package com.bitlegion.encantoartesano.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.bitlegion.encantoartesano.Api.ApiClient
import com.bitlegion.encantoartesano.Api.User
import com.bitlegion.encantoartesano.MainViewModel
import com.bitlegion.encantoartesano.R
import kotlinx.coroutines.launch

@Composable
fun EditProfileScreen(navController: NavController, viewModel: MainViewModel, userId: String) {
    var name by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()

    var isNameValid by remember { mutableStateOf(false) }
    var isPasswordValid by remember { mutableStateOf(false) }
    var isAgeValid by remember { mutableStateOf(false) }

    fun validateName() {
        isNameValid = name.isNotBlank()
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

    LaunchedEffect(name) {
        validateName()
    }

    LaunchedEffect(password) {
        checkPassword()
    }

    LaunchedEffect(age) {
        checkAge()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF15746E))
            .padding(16.dp)
    ) {
        IconButton(onClick = { navController.popBackStack() }) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_chevron_left_24),
                contentDescription = null,
                tint = Color.White
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Editar Perfil",
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Box(
            modifier = Modifier
                .size(100.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            Image(
                painter = painterResource(id = R.drawable.logoapp),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        AccountEditable(label = "Nombre", value = name, onValueChange = { name = it })
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "El nombre de usuario es el nombre único con el que te identificarás en la plataforma.",
            style = MaterialTheme.typography.body2.copy(color = Color.White),
            modifier = Modifier.padding(horizontal = 32.dp)
        )
        PasswordEditable(label = "Contraseña", value = password, onValueChange = { password = it })
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Asegúrate de que tu contraseña tenga al menos 8 caracteres, una mayúscula, una minúscula y un número.",
            style = MaterialTheme.typography.body2.copy(color = Color.White),
            modifier = Modifier.padding(horizontal = 32.dp)
        )
        AgeEdit(label = "Edad", value = age, onValueChange = { age = it })
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = "Debes ser mayor de 18 años para registrarte en nuestra plataforma.",
            style = MaterialTheme.typography.body2.copy(color = Color.White),
            modifier = Modifier.padding(horizontal = 32.dp)
        )
        Button(
            onClick = {
                coroutineScope.launch {
                    if (isNameValid && isPasswordValid && isAgeValid) {
                        val updatedUser = User(name, password, age.toInt())
                        val response = ApiClient.apiService.updateUser(userId, updatedUser)
                        if (response.isSuccessful) {
                            navController.popBackStack()
                        } else {
                            errorMessage = "Error al actualizar el perfil"
                        }
                    } else {
                        errorMessage = "Por favor, completa todos los campos correctamente."
                    }
                }
            },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFE19390)),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.align(Alignment.CenterHorizontally),
            enabled = isNameValid && isPasswordValid && isAgeValid
        ) {
            Text(
                text = "Guardar",
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )
        }
        if (errorMessage.isNotEmpty()) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = errorMessage, color = Color.Red)
        }
    }
}

@Composable
fun AccountEditable(label: String, value: String, onValueChange: (String) -> Unit) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFD0CFBC), RoundedCornerShape(8.dp)),
            singleLine = true,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = Color.Black,
                focusedBorderColor = Color.Black,
                unfocusedBorderColor = Color.Black,
                backgroundColor = Color(0xFFD0CFBC)
            )
        )
    }
}

@Composable
fun PasswordEditable(label: String, value: String, onValueChange: (String) -> Unit) {
    var isPasswordValid by remember { mutableStateOf(false) }

    fun checkPassword() {
        isPasswordValid = value.length >= 8 &&
                value.any { it.isUpperCase() } &&
                value.any { it.isLowerCase() } &&
                value.any { it.isDigit() }
    }

    LaunchedEffect(value) {
        checkPassword()
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = value,
            onValueChange = { onValueChange(it); checkPassword() },
            visualTransformation = PasswordVisualTransformation(),
            singleLine = true,
            placeholder = { Text("Introduce tu contraseña") },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFD0CFBC), RoundedCornerShape(8.dp)),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = Color.Black,
                focusedBorderColor = Color.Black,
                unfocusedBorderColor = Color.Black,
                backgroundColor = Color(0xFFD0CFBC)
            ),
            trailingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_check_24),
                    contentDescription = null,
                    tint = if (isPasswordValid) Color.Green else Color.Red
                )
            }
        )
    }
}

@Composable
fun AgeEdit(label: String, value: String, onValueChange: (String) -> Unit) {
    var isAgeValid by remember { mutableStateOf(false) }

    fun checkAge() {
        isAgeValid = value.toIntOrNull()?.let { it >= 18 } ?: false
    }

    LaunchedEffect(value) {
        checkAge()
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = value,
            onValueChange = { onValueChange(it); checkAge() },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFD0CFBC), RoundedCornerShape(8.dp)),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = Color.Black,
                focusedBorderColor = Color.Black,
                unfocusedBorderColor = Color.Black,
                backgroundColor = Color(0xFFD0CFBC)
            ),
            trailingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_check_24),
                    contentDescription = null,
                    tint = if (isAgeValid) Color.Green else Color.Red
                )
            }
        )
    }
}
