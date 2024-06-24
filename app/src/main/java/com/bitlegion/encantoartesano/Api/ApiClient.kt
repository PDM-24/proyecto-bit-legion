package com.bitlegion.encantoartesano.Api



import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
  private const val BASE_URL = "http://10.0.2.2:3000/api/"
  // private const val BASE_URL = "http://192.168.0.2:3000/api/"


    private val client: OkHttpClient by lazy {
        OkHttpClient.Builder().addInterceptor { chain ->
            val requestBuilder: Request.Builder = chain.request().newBuilder()
            TokenManager.getToken()?.let { token ->
                requestBuilder.addHeader("Authorization", "Bearer $token")
            }
            chain.proceed(requestBuilder.build())
        }.build()
    }

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}

