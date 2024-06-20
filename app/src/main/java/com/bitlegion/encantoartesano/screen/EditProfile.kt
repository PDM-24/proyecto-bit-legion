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
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
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
    val coroutineScope = rememberCoroutineScope()

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
        AccountEditable(label = "Contraseña", value = password, onValueChange = { password = it })
        Spacer(modifier = Modifier.height(16.dp))
        AgeEdit(label = "Edad", value = age, onValueChange = { age = it })
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = {
                coroutineScope.launch {
                    val updatedUser = User(name, password, age.toInt())
                    val response = ApiClient.apiService.updateUser(userId, updatedUser)
                    if (response.isSuccessful) {
                        navController.popBackStack()
                    }
                }
            },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFE19390)),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(
                text = "Guardar",
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )
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
fun AgeEdit(label: String, value: String, onValueChange: (String) -> Unit) {
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
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
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
/*
@Preview(showBackground = true)
@Composable
fun PreviewEditProfile() {
    EditProfileScreen(navController = rememberNavController())
}*/
