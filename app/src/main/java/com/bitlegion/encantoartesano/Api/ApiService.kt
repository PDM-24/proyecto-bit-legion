package com.bitlegion.encantoartesano.Api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @POST("auth/register")
    suspend fun registerUser(@Body user: User): Response<Void>

    @POST("auth/login")
    suspend fun loginUser(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @GET("auth/checkUsername/{username}")
    suspend fun checkUsername(@Path("username") username: String): Response<UsernameCheckResponse>
}

data class UsernameCheckResponse(val exists: Boolean)



