package com.example.studentmanagement.repository

import androidx.room.withTransaction

import com.example.studentmanagement.data.AppDatabase
import com.example.studentmanagement.model.SinhVien
import com.example.studentmanagement.network.RetrofitClient
import com.example.studentmanagement.network.chuyenThanhSinhVien

import kotlinx.coroutines.flow.Flow

class SinhVienRepository(
    private val database: AppDatabase
) {

    private val sinhVienDao = database.sinhVienDao()

    val danhSachSinhVien: Flow<List<SinhVien>> =
        sinhVienDao.layDanhSachSinhVien()

    suspend fun themSinhVien(
        sinhVien: SinhVien
    ): Result<Long> {

        return runCatching {

            require(sinhVien.hoTen.isNotBlank())
            require(sinhVien.maSinhVien.isNotBlank())
            require(sinhVien.diemGPA in 0.0..4.0)

            val daTonTai = sinhVienDao.timTheoMa(
                sinhVien.maSinhVien
            )

            check(daTonTai == null) {
                "Mã sinh viên đã tồn tại!"
            }

            sinhVienDao.themSinhVien(sinhVien)
        }
    }

    suspend fun suaSinhVien(
        sinhVien: SinhVien
    ): Result<Unit> {

        return runCatching {

            require(sinhVien.hoTen.isNotBlank())
            require(sinhVien.diemGPA in 0.0..4.0)

            sinhVienDao.suaSinhVien(sinhVien)
        }
    }

    suspend fun xoaSinhVien(
        sinhVien: SinhVien
    ) {
        sinhVienDao.xoaSinhVien(sinhVien)
    }

    suspend fun dongBoCloud(): Result<Int> {

        return runCatching {

            val duLieuCloud =
                RetrofitClient.apiService.laySinhVienTuCloud()

            val danhSach = duLieuCloud.map {
                it.chuyenThanhSinhVien()
            }

            database.withTransaction {

                danhSach.forEach { sinhVienMoi ->

                    val sinhVienCu =
                        sinhVienDao.timTheoMa(
                            sinhVienMoi.maSinhVien
                        )

                    if (sinhVienCu == null) {

                        sinhVienDao.themSinhVien(
                            sinhVienMoi
                        )

                    } else {

                        sinhVienDao.suaSinhVien(
                            sinhVienMoi.copy(id = sinhVienCu.id)
                        )
                    }
                }
            }

            danhSach.size
        }
    }
}