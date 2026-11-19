package com.example.studentmanagement.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    // ĐỊA CHỈ REST API
    private const val BASE_URL =
        "https://jsonplaceholder.typicode.com/"

    // GHI LOG HTTP
    private val ghiLog = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BASIC
    }

    // KHỞI TẠO HTTP CLIENT
    private val httpClient = OkHttpClient.Builder()
        .addInterceptor(ghiLog)
        .build()

    // KHỞI TẠO RETROFIT
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(httpClient)
        .addConverterFactory(
            GsonConverterFactory.create()
        )
        .build()

    // API SERVICE
    val apiService: SinhVienApiService =
        retrofit.create(
            SinhVienApiService::class.java
        )
}