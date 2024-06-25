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
import com.bitlegion.encantoartesano.Api.TokenManager
import com.bitlegion.encantoartesano.Api.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import retrofit2.Response


class MainViewModel : ViewModel() {

    var shoppedProducts = mutableStateListOf<Product>()
        private set

    var favProducts = mutableStateListOf<Product>()
        private set

    init {
        loadShoppedProducts()
        loadLikedProducts()
    }

    fun loadShoppedProducts() {
        viewModelScope.launch {
            val token = TokenManager.getToken()
            if (token != null) {
                val response = ApiClient.apiService.getShoppedProducts("Bearer $token")
                if (response.isSuccessful) {
                    shoppedProducts.clear()
                    shoppedProducts.addAll(response.body() ?: emptyList())
                } else {
                    Log.e("MainViewModel", "Error loading shopped products: ${response.errorBody()?.string()}")
                }
            } else {
                Log.e("MainViewModel", "Token is null")
            }
        }
    }

    fun isProductFavorite(product: Product): Boolean {
        return favProducts.any { it._id == product._id }
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
        updateFavoriteStatus(product._id.toString(), true)
    }

    private fun removeProductFromFavorites(product: Product) {
        favProducts.removeAll { it._id == product._id }
        updateFavoriteStatus(product._id.toString(), false)
    }

    private fun updateFavoriteStatus(productId: String, isFavorite: Boolean) {
        viewModelScope.launch {
            val token = TokenManager.getToken()
            if (token != null) {
                val response: Response<User> = if (isFavorite) {
                    ApiClient.apiService.likeProduct(productId, "Bearer $token")
                } else {
                    ApiClient.apiService.likeProduct(productId, "Bearer $token")
                }
                if (!response.isSuccessful) {
                    Log.e("MainViewModel", "Error updating favorite status: ${response.errorBody()?.string()}")
                }
            } else {
                Log.e("MainViewModel", "Token is null")
            }
        }
    }

    fun loadLikedProducts() {
        viewModelScope.launch {
            val token = TokenManager.getToken()
            if (token != null) {
                val response = ApiClient.apiService.getLikedProducts("Bearer $token")
                if (response.isSuccessful) {
                    favProducts.clear()
                    favProducts.addAll(response.body() ?: emptyList())
                } else {
                    Log.e("MainViewModel", "Error loading liked products: ${response.errorBody()?.string()}")
                }
            } else {
                Log.e("MainViewModel", "Token is null")
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