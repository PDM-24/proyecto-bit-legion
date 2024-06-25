package com.bitlegion.encantoartesano.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun Header(viewModel: MainViewModel, onSearch: (String) -> Unit) {
    val localScope = rememberCoroutineScope()
    Row(
        modifier = Modifier.padding(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
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
        ) {
            Image(painter = painterResource(id = R.drawable.logo), contentDescription = "Background image",
                Modifier
                    .width(width = 280.dp)
                    .height(height = 60.dp)
            )

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
        IconButton(onClick = {}) {
            Icon(
                painter = painterResource(R.drawable.baseline_grid_view_24),
                contentDescription = "Menu",
                tint = MainRed
            )
        }
    }
    SearchBar(onSearch = onSearch)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(onSearch: (String) -> Unit) {
    var textState by remember { mutableStateOf(TextFieldValue("")) }

    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp),
        value = textState,
        onValueChange = {
            textState = it
            onSearch(it.text)
        },
        placeholder = { Text("Buscar") },
        leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "Buscar") },
        trailingIcon = {
            if (textState.text.isNotEmpty()) {
                IconButton(onClick = { textState = TextFieldValue("") }) {
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
