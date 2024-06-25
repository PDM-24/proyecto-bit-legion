package com.bitlegion.encantoartesano.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.bitlegion.encantoartesano.R

@Composable
fun AccountDeletion(navController: NavController){
    var name by remember { mutableStateOf("Jose Roberto") }
    var password by remember { mutableStateOf("************") }
    var age by remember { mutableStateOf("35") }

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
            text = "Eliminar cuenta",
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
            Image(painter = painterResource(id = R.drawable.logoapp),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Icon(
                painter = painterResource(id = R.drawable.baseline_camera_enhance_24),
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier
                    .size(24.dp)
                    .align(Alignment.BottomEnd)
                    .offset(x = (-4).dp, y = (-4).dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        AccountDeletionReadOnly(label = "Nombre", value = name)
        Spacer(modifier = Modifier.height(16.dp))
        AccountDeletionReadOnly(label = "Contraseña", value = password, isPassword = true)
        Spacer(modifier = Modifier.height(16.dp))
        AccountDeletionReadOnly(label = "Edad", value = age)
        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Productos en venta",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(8.dp))

        Card(
            shape = RoundedCornerShape(8.dp),
            backgroundColor = Color(0XFFD0CFCB),
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
        ) {
            Row(modifier = Modifier.padding(8.dp)) {
                Image(
                    painter = painterResource(id = R.drawable.jarron),
                    contentDescription = null,
                    modifier = Modifier
                        .size(100.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(
                        text = "Nombre Producto",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Text(
                        text = "Descripción del Producto",
                        fontSize = 14.sp,
                        color = Color.Black
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = { /*TODO*/ },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFE19390)),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        ) {
            Text(
                text = "Borrar cuenta",
                color = Color.Black,
                fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun AccountDeletionReadOnly(label: String, value: String, isPassword: Boolean = false){
    Column(modifier = Modifier.fillMaxWidth()){
        Text(
            text = label,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = value,
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFD0CFBC), RoundedCornerShape(8.dp)),
            readOnly = true,
            singleLine = true,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = Color.Black,
                focusedBorderColor = Color.Black,
                unfocusedBorderColor = Color.Black,
                disabledBorderColor = Color.Gray,
                backgroundColor = Color(0xFFD0CFBC)
            )
        )
    }
}

//Preview de prueba
//@Preview
//@Composable
//fun PreviewAccountDeletion(){
//    AccountDeletion()
//}