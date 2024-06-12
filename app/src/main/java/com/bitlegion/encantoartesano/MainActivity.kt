package com.bitlegion.encantoartesano
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.bitlegion.encantoartesano.screen.*
import com.bitlegion.encantoartesano.ui.theme.EncantoArtesanoTheme
import com.bitlegion.encantoartesano.ui.theme.LightPink
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

data class NavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val badgeCount: Int? = null,
    val route: String
)

class MainActivity : ComponentActivity() {

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: MainViewModel = viewModel()
            val localScope = rememberCoroutineScope()

            EncantoArtesanoTheme {
                val navController = rememberNavController()
                Surface(color = MaterialTheme.colorScheme.background) {
                    ModalNavigationDrawer(
                        drawerState = viewModel.drawerState,
                        drawerContent = {
                            val items = listOf(
                                NavigationItem(
                                    title = "Principal",
                                    selectedIcon = Icons.Filled.Home,
                                    unselectedIcon = Icons.Outlined.Home,
                                    route = "home"
                                ),
                                NavigationItem(
                                    title = "Perfíl",
                                    selectedIcon = Icons.Filled.AccountCircle,
                                    unselectedIcon = Icons.Outlined.AccountCircle,
                                    route = "home"
                                ),
                                NavigationItem(
                                    title = "Carrito de compra",
                                    selectedIcon = Icons.Filled.ShoppingCart,
                                    unselectedIcon = Icons.Outlined.ShoppingCart,
                                    route = "cart"
                                ),
                                NavigationItem(
                                    title = "Favoritos",
                                    selectedIcon = Icons.Filled.Favorite,
                                    unselectedIcon = Icons.Outlined.Favorite,
                                    route = "favorites"
                                ),
                                NavigationItem(
                                    title = "Registro de compra",
                                    selectedIcon = ImageVector.vectorResource(R.drawable.baseline_list_alt_24),
                                    unselectedIcon = ImageVector.vectorResource(R.drawable.baseline_list_alt_24),
                                    route = "home"
                                ),
                                NavigationItem(
                                    title = "Vender Producto",
                                    selectedIcon = ImageVector.vectorResource(R.drawable.baseline_sell_24),
                                    unselectedIcon = ImageVector.vectorResource(R.drawable.outline_sell_24),
                                    route = "home"
                                )
                            )
                            var selectedItemIndex by rememberSaveable { mutableStateOf(0) }
                            ModalDrawerSheet(drawerContainerColor = LightPink) {
                                items.forEachIndexed { index, item ->
                                    NavigationDrawerItem(
                                        modifier = Modifier.padding(12.dp),
                                        colors = NavigationDrawerItemDefaults.colors(
                                            unselectedContainerColor = LightPink,
                                            selectedContainerColor = Color.LightGray,
                                            selectedTextColor = Color.Black,
                                            selectedIconColor = Color.Black,
                                            unselectedTextColor = Color.White,
                                            unselectedIconColor = Color.White
                                        ),
                                        label = { Text(text = item.title) },
                                        selected = index == selectedItemIndex,
                                        onClick = {
                                            selectedItemIndex = index

                                            viewModel.coroutineScope.launch {
                                                withContext(localScope.coroutineContext) {
                                                    viewModel.drawerState.close()

                                                }
                                            }
                                            navController.navigate(item.route)
                                        },
                                        icon = {
                                            Icon(
                                                imageVector = if (index == selectedItemIndex) {
                                                    item.selectedIcon
                                                } else item.unselectedIcon,
                                                contentDescription = item.title
                                            )
                                        },
                                        badge = {
                                            item.badgeCount?.let {
                                                Text(text = it.toString())
                                            }
                                        }
                                    )
                                }
                            }
                        }
                    ) {
                        Scaffold(

                        ) {
                            var drawerState = viewModel.drawerState
                            NavHost(navController = navController, startDestination = "login") {
                                composable("login") { LoginScreen(navController) }
                                composable("home") { TiendaUI(viewModel, navController) }
                                composable("register") { RegisterScreen(navController) }
                                composable(
                                    "detail/{productName}",
                                    arguments = listOf(navArgument("productName") { type = NavType.StringType })
                                ) { backStackEntry ->
                                    val productName = backStackEntry.arguments?.getString("productName") ?: ""
                                    ProductDetailScreen(navController, productName, viewModel)
                                }
                                composable("seller_profile") { SellerProfileScreen(navController) }
                                composable("cart") { ShoppingCartScreen(navController, viewModel) }
                                composable("favorites") { FavUI(viewModel) }
                                //composable("pay") { PaymentScreen() }  // Añadir esta línea
                                composable("pay") { PaymentScreen(navController) }  // Pasar el NavController
                            }
                        }
                    }
                }
            }
        }
    }
}