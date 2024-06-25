package com.bitlegion.encantoartesano.Api

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import java.util.Date

interface ApiService {

    @POST("auth/register")
    suspend fun registerUser(@Body user: User): Response<Void>

    @POST("auth/login")
    suspend fun loginUser(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @GET("auth/checkUsername/{username}")
    suspend fun checkUsername(@Path("username") username: String): Response<UsernameCheckResponse>

    @POST("post/postProduct")
    suspend fun postProduct(@Body product: Product): Response<Void>

    @GET("post/getAllProducts")
    suspend fun getAllProducts(): Response<List<Product>>

    @GET("post/getAllActiveProducts")
    suspend fun getAllActiveProducts(): Response<List<Product>>

    @GET("auth/getUser/{id}")
    suspend fun getUserById(@Path("id") userId: String): Response<User>

    @PATCH("auth/update/{id}")
    suspend fun updateUser(@Path("id") userId: String, @Body user: User): Response<User>

    @GET("post/getUserProducts/{userId}")
    suspend fun getUserProducts(@Path("userId") userId: String): Response<List<Product>>

    @GET("post/getPayment/{userId}")
    suspend fun getPaymentMethods(@Path("userId") userId: String): Response<List<PayData>>


    @POST("post/postPayment")
    suspend fun savePayment(@Body payData: PayData): Response<Void>

    @DELETE("post/deleteProduct/{productId}")
    suspend fun deleteProduct(@Path("productId") productId: String): Response<Void>

    @GET("post/getProduct/{id}")
    suspend fun getProductById(@Path("id") _id: String): Response<Product>

    @PATCH("post/like/{id}")
    suspend fun likeProduct(@Path("id") id: String, @Header("Authorization") token: String): Response<User>

    @GET("post/getLikes")
    suspend fun getLikedProducts(@Header("Authorization") token: String): Response<List<Product>>

    @GET("auth/getAllUsers")
    suspend fun getAllUsers(): Response<List<UserWithState>>

    @GET("auth/getActiveUsers")
    suspend fun getActiveUsers(): Response<List<UserWithState>>


    @GET("auth/getBlockedUsers")
    suspend fun getBlockedUsers(): Response<List<UserWithState>>


    @PATCH("auth/updateUserState/{userId}")
    suspend fun updateState(@Path("userId") userId: String): Response<Void>


    @GET("post/getShopped")
    suspend fun getShoppedProducts(@Header("Authorization") token: String): Response<List<Product>>


    @Multipart
    @POST("/api/upload")
    suspend fun uploadImage(
        @Part image: MultipartBody.Part,
        @Part("name") name: RequestBody,
        @Part("description") description: RequestBody,
        @Part("price") price: RequestBody
    ): Response<UploadResponse>

    @PATCH("post/onCart/{id}")
    suspend fun addProductToCart(@Path("id") productId: String,  @Header("Authorization") token: String): Response<User>

    @GET("post/getOnCart")
    suspend fun getOnCartProducts(@Header("Authorization") token: String): Response<List<Product>>

    @PATCH("post/checkout")
    suspend fun checkout(@Header("Authorization") token: String): Response<User>
}

data class UsernameCheckResponse(val exists: Boolean)

data class Product(
    val _id: String?,
    val nombre: String,
    val descripcion: String,
    val precio: Double,
    val ubicacion: String,
    val imagenes: List<String>,
    val calificacion: Int = 0,
    val fecha: Date,
    val user: String
)

data class ProductHome(
    val _id: String?,
    val nombre: String,
    val descripcion: String,
    val precio: Double,
    val ubicacion: String,
    val imagenes: List<String>,
    val calificacion: Int = 0,
    val fecha: Date,
    val user: UserProduct
)

data class UserProduct(
    val _id: String,
    val state: Boolean
)

data class PayData(
    val _id: String?,
    val number: String,
    val titular: String,
    val fechaVencimiento: String,
    val cvv: String,
    val user: String?
)

data class UploadResponse(
    val imageUrl: String
)
