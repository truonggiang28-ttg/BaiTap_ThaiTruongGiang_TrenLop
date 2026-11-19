package com.example.studentmanagement.network

import com.example.studentmanagement.model.SinhVien
import com.google.gson.annotations.SerializedName

data class SinhVienDto(

    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val ten: String,

    @SerializedName("email")
    val email: String
)

fun SinhVienDto.chuyenThanhSinhVien(): SinhVien {

    return SinhVien(
        hoTen = ten,
        maSinhVien = "API%05d".format(id),
        email = email,
        diemGPA = 2.5 + (id % 4) * 0.4,
        chuyenNganh = "Công Nghệ Thông Tin"
    )
}