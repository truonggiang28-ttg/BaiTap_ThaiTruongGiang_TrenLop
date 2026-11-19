package com.example.studentmanagement.network

import retrofit2.http.GET

interface SinhVienApiService {

    @GET("users")
    suspend fun laySinhVienTuCloud(): List<SinhVienDto>
}