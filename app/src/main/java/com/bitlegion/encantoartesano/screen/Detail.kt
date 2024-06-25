package com.bitlegion.encantoartesano.screen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.bitlegion.encantoartesano.Api.ApiClient
import com.bitlegion.encantoartesano.Api.Product
import com.bitlegion.encantoartesano.Api.User
import com.bitlegion.encantoartesano.MainViewModel
import com.bitlegion.encantoartesano.R
import com.bitlegion.encantoartesano.component.Header
import com.bitlegion.encantoartesano.ui.theme.grayWhite
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(navController: NavHostController, productId: String, viewModel: MainViewModel) {
    val scope = rememberCoroutineScope()
    var product by remember { mutableStateOf<Product?>(null) }
    var seller by remember { mutableStateOf<User?>(null) }
    val pagerState = rememberPagerState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        scope.launch {
            val response = ApiClient.apiService.getProductById(productId)
            if (response.isSuccessful) {
                product = response.body()
                product?.user?.let { userId ->
                    val userResponse = ApiClient.apiService.getUserById(userId)
                    if (userResponse.isSuccessful) {
                        seller = userResponse.body()
                    }
                }
            } else {
                // Manejar error
            }
        }
    }

    Column(
        modifier = Modifier
            .background(color = grayWhite)
            .fillMaxSize()
    ) {
        Header(viewModel)

        product?.let { prod ->
            Spacer(modifier = Modifier.height(16.dp))
            Box(
                contentAlignment = Alignment.TopEnd,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                HorizontalPager(
                    count = prod.imagenes.size,
                    state = pagerState,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(8.dp))
                ) { page ->
                    Image(
                        painter = rememberAsyncImagePainter(prod.imagenes[page]),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize()
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
                prod.imagenes.forEachIndexed { index, _ ->
                    val color = if (pagerState.currentPage == index) Color.Red else Color.Gray
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .background(color, CircleShape)
                            .padding(4.dp)
                    )
                    if (index < prod.imagenes.size - 1) {
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
                    .padding(horizontal = 16.dp, vertical = 50.dp) // Ajustar el padding vertical
            ) {
                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = prod.nombre,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            modifier = Modifier.weight(1f)
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = prod.descripcion,
                        fontSize = 16.sp,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Spacer(modifier = Modifier.height(175.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "${prod.precio} USD",
                            fontSize = 35.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Column(
                            horizontalAlignment = Alignment.End
                        ) {
                            seller?.let { user ->
                                Text(
                                    text = "Vendedor: ${user.username}",
                                    fontSize = 18.sp,
                                    color = Color.White,
                                    modifier = Modifier.clickable {
                                        navController.navigate("seller_profile/${user._id}")
                                    }
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Button(
                                onClick = {
                                    viewModel.addProductToCart(productId)
                                    Toast.makeText(context, "Producto agregado al carrito", Toast.LENGTH_SHORT).show()
                                },
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
}
