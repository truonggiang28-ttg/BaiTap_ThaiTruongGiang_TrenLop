package com.example.studentmanagement.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "students_table",
    indices = [
        Index(
            value = ["student_code"],
            unique = true
        )
    ]
)
data class SinhVien(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "full_name")
    val hoTen: String,

    @ColumnInfo(name = "student_code")
    val maSinhVien: String,

    @ColumnInfo(name = "email")
    val email: String,

    @ColumnInfo(name = "gpa")
    val diemGPA: Double,

    @ColumnInfo(name = "major")
    val chuyenNganh: String = "Công Nghệ Thông Tin"
)