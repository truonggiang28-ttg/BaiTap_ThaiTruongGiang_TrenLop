package com.example.studentmanagement.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import com.example.studentmanagement.repository.SinhVienRepository

class SinhVienViewModelFactory(
    private val khoDuLieu: SinhVienRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(
        modelClass: Class<T>
    ): T {

        if (modelClass.isAssignableFrom(
                SinhVienViewModel::class.java
            )
        ) {

            return SinhVienViewModel(khoDuLieu) as T
        }

        throw IllegalArgumentException(
            "Không tìm thấy ViewModel!"
        )
    }
}