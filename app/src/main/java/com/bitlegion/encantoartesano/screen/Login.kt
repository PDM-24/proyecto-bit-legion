package com.bitlegion.encantoartesano.screen

import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import com.bitlegion.encantoartesano.Api.ApiClient
import com.bitlegion.encantoartesano.Api.LoginRequest
import com.bitlegion.encantoartesano.Api.LoginResponse
import com.bitlegion.encantoartesano.Api.TokenManager
import com.bitlegion.encantoartesano.MainViewModel
import com.bitlegion.encantoartesano.R
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(navController: NavHostController, context: Context, viewModel: MainViewModel) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    var isUsernameValid by remember { mutableStateOf(false) }
    var isUsernameChecked by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    val checkUsername: suspend () -> Unit = {
        val response = ApiClient.apiService.checkUsername(username)
        if (response.isSuccessful) {
            val usernameCheckResponse = response.body()
            isUsernameValid = usernameCheckResponse?.exists == true
        } else {
            isUsernameValid = false
        }
        isUsernameChecked = true
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
            onValueChange = { username = it; isUsernameChecked = false },
            label = { Text("Usuario") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                backgroundColor = Color(0xFFE8DED1),
                focusedBorderColor = Color.Gray,
                unfocusedBorderColor = Color.Gray
            ),
            trailingIcon = {
                if (isUsernameChecked) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_check_24),
                        contentDescription = null,
                        tint = if (isUsernameValid) Color.Green else Color.Red
                    )
                } else {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_check_24),
                        contentDescription = null,
                        tint = Color.Red
                    )
                }
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
            singleLine = true,
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
                scope.launch {
                    val sharedPreferences = context.getSharedPreferences("encanto_artesano_prefs", Context.MODE_PRIVATE)

                    // Eliminar las variables guardadas anteriormente
                    with(sharedPreferences.edit()) {
                        remove("user_id")
                        remove("user_rol")
                        apply()
                    }

                    // Realizar la llamada a la API para el inicio de sesión
                    val response = ApiClient.apiService.loginUser(LoginRequest(username, password))
                    if (response.isSuccessful) {
                        val loginResponse = response.body()

                        // Guardar el token globalmente
                        loginResponse?.token?.let { TokenManager.saveToken(it) }

                        // Guardar las nuevas variables en SharedPreferences
                        with(sharedPreferences.edit()) {
                            putString("user_id", loginResponse?.userId)
                            putString("user_rol", loginResponse?.userRol)
                            apply()
                        }
                        viewModel.updateUserRole(loginResponse?.userRol)

                        // Navegar según el rol del usuario
                        if (loginResponse?.userRol.equals("admin")) {
                            navController.navigate("adminHome")
                        } else {
                            navController.navigate("home")
                        }
                    } else {
                        errorMessage = "Usuario o contraseña incorrectos"
                    }
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
