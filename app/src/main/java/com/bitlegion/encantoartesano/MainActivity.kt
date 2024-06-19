package com.bitlegion.encantoartesano

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.ExitToApp
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
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
import com.bitlegion.encantoartesano.screen.admin.TiendaUIAdmin
import com.bitlegion.encantoartesano.ui.theme.EncantoArtesanoTheme
import com.bitlegion.encantoartesano.ui.theme.LightPink
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import androidx.compose.ui.platform.LocalContext
import kotlin.math.log

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
            var currentRoute by remember { mutableStateOf<String?>(null) }
            val context = LocalContext.current
            val sharedPreferences = context.getSharedPreferences("encanto_artesano_prefs", Context.MODE_PRIVATE)
            val userRol = sharedPreferences.getString("user_rol", null)

            EncantoArtesanoTheme {
                val navController = rememberNavController()

                LaunchedEffect(navController) {
                    navController.addOnDestinationChangedListener { _, destination, _ ->
                        currentRoute = destination.route
                    }
                }

                val showDrawer = currentRoute != "login" && currentRoute != "register" && currentRoute != "perfil" && currentRoute != "edit_profile"

                Surface(color = MaterialTheme.colorScheme.background) {
                    if (showDrawer) {
                        ModalNavigationDrawer(
                            drawerState = viewModel.drawerState,
                            drawerContent = {
                                val items = setDrawerContent(rol = viewModel.userRole.value)


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
                            AppContent(navController, viewModel, context)
                        }

                    } else {
                        AppContent(navController, viewModel, context)
                    }
                }
            }
        }
    }

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @Composable
    fun AppContent(navController: NavHostController, viewModel: MainViewModel, context: Context) {
        val sharedPreferences = context.getSharedPreferences("encanto_artesano_prefs", Context.MODE_PRIVATE)
        val userId = sharedPreferences.getString("user_id", null)

        // Actualizar el estado del ViewModel con los valores actuales de SharedPreferences
        LaunchedEffect(Unit) {
            viewModel.updateUserRole(sharedPreferences.getString("user_rol", null))
        }


        Scaffold {

            NavHost(navController = navController, startDestination = "login") {
                composable("login") { LoginScreen(navController, context, viewModel) }
                composable("home") { TiendaUI(viewModel, navController) }
                composable("register") { RegisterScreen(navController) }
                composable(
                    "detail/{productName}",
                    arguments = listOf(navArgument("productName") { type = NavType.StringType })
                ) { backStackEntry ->
                    val productName = backStackEntry.arguments?.getString("productName") ?: ""
                    ProductDetailScreen(navController, productName, viewModel)
                }
                composable("perfil") { PerfilScreen(navController) }
                composable("edit_profile") { EditProfileScreen(navController) }
                composable("seller_profile") { SellerProfileScreen(navController) }
                composable("cart") { ShoppingCartScreen(navController, viewModel) }
                composable("favorites") { FavUI(viewModel) }
                composable("pay") { PaymentScreen(navController) }
                composable("vender") { ProductRegistration(navController, context, userId ?: "") }

                composable("adminHome") { TiendaUIAdmin(viewModel = viewModel, navController = navController) }
                composable("RegistroDeCompra") { BoughtItems(navController, viewModel) }
                composable("active_users") { ActiveUsers() }
                composable("account_deletion") { AccountDeletion(navController) }
            }
        }
    }
}


@Composable
fun setDrawerContent(rol: String?): List<NavigationItem> {
    return if (rol == "admin") {
        listOf(
            NavigationItem(
                title = "Principal",
                selectedIcon = Icons.Filled.Home,
                unselectedIcon = Icons.Outlined.Home,
                route = "adminHome"
            ),
            NavigationItem(
                title = "Perfíl",
                selectedIcon = Icons.Filled.AccountCircle,
                unselectedIcon = Icons.Outlined.AccountCircle,
                route = "perfil"
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
                route = "RegistroDeCompra"
            ),
            NavigationItem(
                title = "Vender Producto",
                selectedIcon = ImageVector.vectorResource(R.drawable.baseline_sell_24),
                unselectedIcon = ImageVector.vectorResource(R.drawable.outline_sell_24),
                route = "vender"
            ),
            NavigationItem(
                title = "Cuentas bloqueadas",
                selectedIcon = Icons.Filled.Person,
                unselectedIcon = Icons.Outlined.Person,
                route = "account_deletion"
            ),
            NavigationItem(
                title = "Administrar cuentas",
                selectedIcon = Icons.Filled.AccountBox,
                unselectedIcon = Icons.Outlined.AccountBox,
                route = "active_users"
            ),
            NavigationItem(
                title = "Cerrar Sesión",
                selectedIcon = Icons.Outlined.ExitToApp,
                unselectedIcon = Icons.Outlined.ExitToApp,
                route = "login"
            )
        )
    } else {
        listOf(
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
                route = "perfil"
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
                route = "RegistroDeCompra"
            ),
            NavigationItem(
                title = "Vender Producto",
                selectedIcon = ImageVector.vectorResource(R.drawable.baseline_sell_24),
                unselectedIcon = ImageVector.vectorResource(R.drawable.outline_sell_24),
                route = "vender"
            ),
            NavigationItem(
                title = "Cerrar Sesión",
                selectedIcon = Icons.Outlined.ExitToApp,
                unselectedIcon = Icons.Outlined.ExitToApp,
                route = "login"
            )
        )
    }
}
