package com.bitlegion.encantoartesano

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
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
import com.bitlegion.encantoartesano.Api.UploadResponse
import com.bitlegion.encantoartesano.Api.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.util.Date


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

    fun uploadImage(
        context: Context,
        imageUri: Uri,
        name: String,
        description: String,
        price: Double,
        location: String,
        userId: String,
        callback: (Boolean, String?) -> Unit
    ) {
        viewModelScope.launch {
            val bitmap = withContext(Dispatchers.IO) {
                MediaStore.Images.Media.getBitmap(context.contentResolver, imageUri)
            }
            val byteArrayOutputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
            val imageBytes = byteArrayOutputStream.toByteArray()

            val requestFile = RequestBody.create("image/jpeg".toMediaTypeOrNull(), imageBytes)
            val body = MultipartBody.Part.createFormData("image", "image.jpg", requestFile)

            val nameRequest = RequestBody.create("text/plain".toMediaTypeOrNull(), name)
            val descriptionRequest = RequestBody.create("text/plain".toMediaTypeOrNull(), description)
            val priceRequest = RequestBody.create("text/plain".toMediaTypeOrNull(), price.toString())

            val response = withContext(Dispatchers.IO) {
                ApiClient.apiService.uploadImage(body, nameRequest, descriptionRequest, priceRequest)
            }

            if (response.isSuccessful) {
                val imageUrl = response.body()?.imageUrl ?: ""
                val product = Product(
                    _id = null,
                    nombre = name,
                    descripcion = description,
                    precio = price,
                    ubicacion = location,
                    imagenes = listOf(imageUrl),
                    calificacion = 0,
                    fecha = Date(),
                    user = userId
                )
                val productResponse = withContext(Dispatchers.IO) {
                    ApiClient.apiService.postProduct(product)
                }
                if (productResponse.isSuccessful) {
                    callback(true, null)
                } else {
                    callback(false, productResponse.errorBody()?.string())
                }
            } else {
                // Agregar más detalles en los logs para depuración
                callback(false, "Error uploading image: ${response.errorBody()?.string()}")
            }
        }
    }

}