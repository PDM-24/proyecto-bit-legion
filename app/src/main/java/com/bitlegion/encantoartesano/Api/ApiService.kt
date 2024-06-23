package com.bitlegion.encantoartesano.Api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
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

    @GET("post/getProduct/{id}")
    suspend fun getProductById(@Path("id") _id: String): Response<Product>

    @PATCH("post/like/{id}")
    suspend fun likeProduct(@Path("id") id: String): Response<User>

    @PATCH("post/unlike/{id}")
    suspend fun unlikeProduct(@Path("id") id: String): Response<User>

    @GET("post/getLikes")
    suspend fun getLikedProducts(): Response<List<Product>>
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
    val fecha:Date,
    val user: String
)

data class PayData(
    val _id: String?,
    val number: String,
    val titular: String,
    val fechaVencimiento: String,
    val cvv: String,
    val user: String?
)




