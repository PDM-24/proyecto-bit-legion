package com.bitlegion.encantoartesano.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bitlegion.encantoartesano.MainViewModel
import com.bitlegion.encantoartesano.R
import com.bitlegion.encantoartesano.component.Header
import com.bitlegion.encantoartesano.ui.theme.Aqua
import com.bitlegion.encantoartesano.ui.theme.grayWhite
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.CoroutineScope

@OptIn(ExperimentalPagerApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(navController: NavHostController, productName: String, viewModel: MainViewModel) {
    val scope = rememberCoroutineScope()
    var isFavorite by remember { mutableStateOf(false) }
    val images = listOf(R.drawable.jarron, R.drawable.jarron, R.drawable.jarron) // Lista de imágenes
    val pagerState = rememberPagerState()

    Column(
        modifier = Modifier
            .background(color = grayWhite)
            .fillMaxSize()
    ) {
        Header(viewModel)

        Spacer(modifier = Modifier.height(16.dp))
        Box(
            contentAlignment = Alignment.TopEnd,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) {
            HorizontalPager(
                count = images.size,
                state = pagerState,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(8.dp))
            ) { page ->
                Image(
                    painter = painterResource(id = images[page]),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize()
                )
            }
            IconButton(
                onClick = { isFavorite = !isFavorite },
                modifier = Modifier
                    .size(48.dp)
                    .padding(8.dp)
                    .offset(x = (-16).dp)
                    .background(Aqua, shape = CircleShape)
                    .clip(CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Favorite",
                    tint = if (isFavorite) Color.Red else Color.White
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .offset(x = (-16).dp)
        ) {
            images.forEachIndexed { index, _ ->
                val color = if (pagerState.currentPage == index) Color.Red else Color.Gray
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .background(color, CircleShape)
                        .padding(4.dp)
                )
                if (index < images.size - 1) {
                    Spacer(modifier = Modifier.width(8.dp))
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color(0xFF008080), shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                .padding(16.dp)
        ) {
            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = productName,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = "★★★★☆",
                        fontSize = 32.sp,
                        color = Color.Black,
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Descripción del Producto",
                    fontSize = 16.sp,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(8.dp))

                Spacer(modifier = Modifier.height(200.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "$25",
                        fontSize = 50.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Column(
                        horizontalAlignment = Alignment.End
                    ) {
                        Text(
                            text = "Nombre del vendedor",
                            fontSize = 18.sp,
                            color = Color.White,
                            modifier = Modifier.clickable {
                                navController.navigate("seller_profile")
                            }
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(
                            onClick = { /* TODO: Handle add to cart */ },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0XFFE19390)),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier
                                .height(70.dp)
                                .width(250.dp)
                                .clip(RoundedCornerShape(8.dp))
                        ) {
                            Text(
                                text = "Agregar al Carrito",
                                fontSize = 20.sp,
                                color = Color.Black
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProductDetailScreenPreview() {
    val viewModel: MainViewModel = viewModel()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val navController = rememberNavController()
    ProductDetailScreen(navController,"Nombre Producto", viewModel)
}


