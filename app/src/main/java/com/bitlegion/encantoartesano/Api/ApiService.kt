package com.bitlegion.encantoartesano.Api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
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
}

data class UsernameCheckResponse(val exists: Boolean)


data class Product(
    val nombre: String,
    val descripcion: String,
    val precio: Double,
    val ubicacion: String,
    val imagenes: List<String>,
    val calificacion: Int = 0,
    val fecha:Date,
    val user: String
)




