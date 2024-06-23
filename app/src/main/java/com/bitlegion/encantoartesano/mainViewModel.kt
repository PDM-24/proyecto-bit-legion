package com.bitlegion.encantoartesano

import android.util.Log
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bitlegion.encantoartesano.Api.ApiClient
import com.bitlegion.encantoartesano.Api.Product
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch


class MainViewModel : ViewModel() {

    var favProducts = mutableStateListOf<Product>()
        private set

    init {
        loadLikedProducts()
    }

    fun isProductFavorite(product: Product): Boolean {
        return product in favProducts
    }

    fun toggleProductFavorite(product: Product) {
        viewModelScope.launch {
            if (isProductFavorite(product)) {
                removeProductFromFavorites(product)
            } else {
                addProductToFavorites(product)
            }
        }
    }

    private fun addProductToFavorites(product: Product) {
        favProducts.add(product)
        // Llama a la API para agregar a favoritos
        updateFavoriteStatus(product._id.toString(), true)
    }

    private fun removeProductFromFavorites(product: Product) {
        favProducts.remove(product)
        // Llama a la API para quitar de favoritos
        updateFavoriteStatus(product._id.toString(), false)
    }

    private fun updateFavoriteStatus(productId: String, isFavorite: Boolean) {
        // Aquí haces la llamada a la API para actualizar el estado de favoritos
        // Ejemplo:
        viewModelScope.launch {
            val response = if (isFavorite) {
                ApiClient.apiService.likeProduct(productId)
            } else {
                ApiClient.apiService.unlikeProduct(productId)
            }
            // Manejar la respuesta si es necesario
        }
    }

    private fun loadLikedProducts() {
        viewModelScope.launch {
            // Llama a la API para obtener los productos que le gustan al usuario
            val response = ApiClient.apiService.getLikedProducts()
            if (response.isSuccessful) {
                favProducts.addAll(response.body() ?: emptyList())
            }
        }
    }

    private val _userRole = mutableStateOf<String?>(null)
    val userRole: State<String?> get() = _userRole

    private val _productId = mutableStateOf<String?>(null)
    val productId: State<String?> get() = _productId

    fun updateUserRole(newRole: String?) {
        _userRole.value = newRole
    }

    fun updateProductId(newRole: String?) {
        _userRole.value = newRole
    }
    // Utiliza el viewModelScope para corutinas
    val coroutineScope: CoroutineScope = viewModelScope


    // Define el DrawerState en el ViewModel
    val drawerState = DrawerState(DrawerValue.Closed)

    fun openDrawer() {
        coroutineScope.launch {
            drawerState.open()
        }
    }

    fun closeDrawer() {
        coroutineScope.launch {
            drawerState.close()
        }
    }


}