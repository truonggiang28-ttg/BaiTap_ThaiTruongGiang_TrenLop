package com.example.studentmanagement.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope

import com.example.studentmanagement.model.SinhVien
import com.example.studentmanagement.repository.SinhVienRepository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SinhVienViewModel(
    private val khoDuLieu: SinhVienRepository
) : ViewModel() {

    val danhSachSinhVien: LiveData<List<SinhVien>> =
        khoDuLieu.danhSachSinhVien.asLiveData(
            context = Dispatchers.IO
        )

    private val _thongBao = MutableLiveData<String>()

    val thongBao: LiveData<String>
        get() = _thongBao

    fun themSinhVien(sinhVien: SinhVien) {

        viewModelScope.launch(Dispatchers.IO) {

            val ketQua = khoDuLieu.themSinhVien(sinhVien)

            if (ketQua.isSuccess) {

                _thongBao.postValue(
                    "Đã thêm sinh viên: ${sinhVien.hoTen}"
                )

            } else {

                _thongBao.postValue(
                    ketQua.exceptionOrNull()?.message
                        ?: "Không thể thêm sinh viên!"
                )
            }
        }
    }

    fun suaSinhVien(sinhVien: SinhVien) {

        viewModelScope.launch(Dispatchers.IO) {

            val ketQua = khoDuLieu.suaSinhVien(sinhVien)

            _thongBao.postValue(
                if (ketQua.isSuccess) {
                    "Cập nhật sinh viên thành công!"
                } else {
                    "Không thể cập nhật sinh viên!"
                }
            )
        }
    }

    fun xoaSinhVien(sinhVien: SinhVien) {

        viewModelScope.launch(Dispatchers.IO) {

            try {

                khoDuLieu.xoaSinhVien(sinhVien)

                _thongBao.postValue(
                    "Đã xóa sinh viên: ${sinhVien.hoTen}"
                )

            } catch (loi: Exception) {

                _thongBao.postValue(
                    "Xóa sinh viên thất bại!"
                )
            }
        }
    }

    fun dongBoCloud() {

        viewModelScope.launch(Dispatchers.IO) {

            val ketQua = khoDuLieu.dongBoCloud()

            _thongBao.postValue(
                if (ketQua.isSuccess) {

                    "Đã đồng bộ ${ketQua.getOrNull()} sinh viên!"

                } else {

                    "Không kết nối được Cloud. Dữ liệu cục bộ vẫn được giữ."
                }
            )
        }
    }
}