package com.bitlegion.encantoartesano.component

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bitlegion.encantoartesano.MainViewModel
import com.bitlegion.encantoartesano.R
import com.bitlegion.encantoartesano.ui.theme.MainRed
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.log


@Composable
fun Header(viewModel: MainViewModel){
    val localScope = rememberCoroutineScope()
    Row (
        modifier = Modifier.padding(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        IconButton(onClick = {
            viewModel.coroutineScope.launch {
                withContext(localScope.coroutineContext) {
                    viewModel.drawerState.open()
                }
            }

        }) {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "Menu",
                tint = MainRed

            )
        }
        Spacer(modifier = Modifier.width(10.dp))
        Box(

            contentAlignment = Alignment.Center
        ){
            Image(painter = painterResource(id = R.drawable.logo), contentDescription =  "Background image",
                Modifier
                    .width(width = 280.dp)
                    .height(height = 60.dp))

            Text(
                text = "Encanto Artesano",
                color = MainRed,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .background(
                        color = Color.White.copy(alpha = 0.6f),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .width(265.dp)

            )
        }
        Spacer(modifier = Modifier.width(12.dp))
        IconButton(onClick = {

        }) {
            Icon(
                painter = painterResource(R.drawable.baseline_grid_view_24),
                contentDescription = "Menu",
                tint = MainRed

            )
        }

    }
         SearchBar()
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar() {
    var textState = remember { mutableStateOf(TextFieldValue("")) }

    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp),

        value = textState.value,
        onValueChange = { textState.value = it },
        placeholder = { Text("Buscar") },
        leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "Buscar") },
        trailingIcon = {
            if (textState.value.text.isNotEmpty()) {
                IconButton(onClick = { textState.value = TextFieldValue("") }) {
                    Icon(Icons.Filled.Close, contentDescription = "Limpiar")
                }
            }
        },
        singleLine = true,
        shape = CircleShape,
        textStyle = LocalTextStyle.current.copy(color = Color.Gray),
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color.White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            errorIndicatorColor = Color.Transparent
        ),
    )
}