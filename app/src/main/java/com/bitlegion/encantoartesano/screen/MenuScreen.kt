package com.bitlegion.encantoartesano.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrawerScreen() {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Ejemplo Drawer") },
                navigationIcon = {
                    IconButton(onClick = {
                        scope.launch {
                            if (drawerState.isClosed) {
                                drawerState.open()
                            } else {
                                drawerState.close()
                            }
                        }
                    }) {
                        Icon(Icons.Filled.Menu, contentDescription = "Menú Hamburguesa")
                    }
                }
            )
        }
    ) {

        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                DrawerContent(navController, drawerState, scope)
            },
            content = {
                NavHost(navController = navController, startDestination = "main") {
                    //composable("main") { TiendaUI(scope, drawerState, navController) }
                    //composable("perfil") { ScreenWithDrawer("Perfil", drawerState, scope) }
                    //composable("carrito") { ShoppingCartScreen(drawerState) }
                    //composable("favorito") { Screen("Favorito") }
                    //composable("registro") { Screen("Registro de Compra") }
                    //composable("vender") { Screen("Vender Producto") }
                }
            }
        )
    }
}

@Composable
fun DrawerContent(navController: NavHostController, drawerState: DrawerState, scope: CoroutineScope) {
    Column(
        modifier = Modifier
            .background(Color(0xFFFBE9E7))
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(48.dp))
        DrawerButton(Icons.Filled.AccountCircle, "Perfil", "perfil", navController, drawerState, scope)
        DrawerButton(Icons.Filled.ShoppingCart, "Carrito de Compra", "carrito", navController, drawerState, scope)
        DrawerButton(Icons.Filled.Favorite, "Favorito", "favorito", navController, drawerState, scope)
        DrawerButton(Icons.Filled.List, "Registro de Compra", "registro", navController, drawerState, scope)
        DrawerButton(Icons.Filled.AccountBox, "Vender Producto", "vender", navController, drawerState, scope)

    }
}

@Composable
fun DrawerButton(
    icon: ImageVector,
    label: String,
    route: String,
    navController: NavHostController,
    drawerState: DrawerState,
    scope: CoroutineScope
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(12.dp))
            .padding(horizontal = 16.dp, vertical = 12.dp)
            .clickable {
                scope.launch {
                    drawerState.close()
                    navController.navigate(route)
                }
            }
    ) {
        Icon(imageVector = icon, contentDescription = label)
        Spacer(modifier = Modifier.width(24.dp))
        Text(text = label)
    }
    Spacer(modifier = Modifier.height(8.dp))
}
