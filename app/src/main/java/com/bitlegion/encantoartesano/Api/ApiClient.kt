package com.bitlegion.encantoartesano.Api



import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
  //  private const val BASE_URL = "http://10.0.2.2:3000/api/"
    private const val BASE_URL = "http://192.168.0.9:3000/api/"


    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}

