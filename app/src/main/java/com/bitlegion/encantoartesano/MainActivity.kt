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
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.bitlegion.encantoartesano.screen.FavProduct
import com.bitlegion.encantoartesano.screen.FavProductCard
import com.bitlegion.encantoartesano.screen.LoginScreen
import com.bitlegion.encantoartesano.screen.PaymentScreen
import com.bitlegion.encantoartesano.screen.ProductDetailScreenPreview
import com.bitlegion.encantoartesano.screen.RegisterScreen
import com.bitlegion.encantoartesano.screen.TiendaUI
import com.bitlegion.encantoartesano.ui.theme.EncantoArtesanoTheme
import com.bitlegion.encantoartesano.ui.theme.LightPink
import kotlinx.coroutines.launch


data class NavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val badgeCount: Int? = null
)


class MainActivity : ComponentActivity() {

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EncantoArtesanoTheme {
                val items = listOf(
                    NavigationItem(
                        title = "Principal",
                        selectedIcon = Icons.Filled.Home,
                        unselectedIcon = Icons.Outlined.Home,
                    ),
                    NavigationItem(
                        title = "Perfíl",
                        selectedIcon = Icons.Filled.AccountCircle,
                        unselectedIcon = Icons.Outlined.AccountCircle,
                        // badgeCount = 45
                    ),
                    NavigationItem(
                        title = "Carrito de compra",
                        selectedIcon = Icons.Filled.ShoppingCart,
                        unselectedIcon = Icons.Outlined.ShoppingCart,
                    ),
                    NavigationItem(
                        title = "Favoritos",
                        selectedIcon = Icons.Filled.Favorite,
                        unselectedIcon = Icons.Outlined.Favorite,
                    ),
                    NavigationItem(
                        title = "Registro de compra",
                        selectedIcon = ImageVector.vectorResource(R.drawable.baseline_list_alt_24),
                        unselectedIcon = ImageVector.vectorResource(R.drawable.baseline_list_alt_24),
                        // badgeCount = 45
                    ),
                    NavigationItem(
                        title = "Vender Producto",
                        selectedIcon = ImageVector.vectorResource(R.drawable.baseline_sell_24),
                        unselectedIcon = ImageVector.vectorResource(R.drawable.outline_sell_24),
                    )
                )

                Surface(

                    color = MaterialTheme.colorScheme.background
                ) {
                    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
                    val scope = rememberCoroutineScope()
                    var selectedItemIndex by rememberSaveable {
                        mutableStateOf(0)
                    }
                    ModalNavigationDrawer(

                        drawerContent = {
                            ModalDrawerSheet (
                                drawerContainerColor = LightPink,

                                ){

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

                                        label = {
                                            Text(text = item.title)
                                        },
                                        selected = index == selectedItemIndex,
                                        onClick = {
//                                            navController.navigate(item.route)
                                            selectedItemIndex = index
                                            scope.launch {
                                                drawerState.close()
                                            }
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
                                                Text(text = item.badgeCount.toString())
                                            }
                                        },

                                        )
                                }
                            }
                        },
                        drawerState = drawerState
                    ) {
                        Scaffold {

                            /*LoginScreen()*/
                            /*RegisterScreen()*/
                            TiendaUI(scope, drawerState)
                            /*ProductDetailScreenPreview()*/

                            /*PaymentScreen()*/
                        }
                    }
                }
            }
        }
    }
}