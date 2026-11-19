package com.example.studentmanagement.ui

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.util.Patterns

import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels

import com.example.studentmanagement.databinding.DialogThemSinhVienBinding
import com.example.studentmanagement.model.SinhVien
import com.example.studentmanagement.viewmodel.SinhVienViewModel

class ThemSinhVienDialogFragment : DialogFragment() {

    private val quanLySinhVien: SinhVienViewModel by activityViewModels()

    override fun onCreateDialog(
        savedInstanceState: Bundle?
    ): Dialog {

        val binding = DialogThemSinhVienBinding.inflate(
            LayoutInflater.from(requireContext())
        )

        val hopThoai = AlertDialog.Builder(requireContext())
            .setTitle("THÊM SINH VIÊN")
            .setView(binding.root)
            .setPositiveButton("Lưu vào Room", null)
            .setNegativeButton("Hủy", null)
            .create()

        hopThoai.setOnShowListener {

            hopThoai.getButton(
                AlertDialog.BUTTON_POSITIVE
            ).setOnClickListener {

                val hoTen =
                    binding.edtHoTen.text.toString().trim()

                val maSinhVien =
                    binding.edtMaSinhVien.text.toString().trim()

                val email =
                    binding.edtEmail.text.toString().trim()

                val diemGPA =
                    binding.edtGpa.text.toString()
                        .trim()
                        .replace(',', '.')
                        .toDoubleOrNull()

                val chuyenNganh =
                    binding.edtChuyenNganh.text.toString().trim()

                when {

                    hoTen.length < 3 -> {

                        binding.edtHoTen.error =
                            "Họ tên phải có ít nhất 3 ký tự"
                    }

                    maSinhVien.isEmpty() -> {

                        binding.edtMaSinhVien.error =
                            "Mã sinh viên không được để trống"
                    }

                    email.isNotEmpty() &&
                            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {

                        binding.edtEmail.error =
                            "Email không hợp lệ"
                    }

                    diemGPA == null ||
                            !diemGPA.isFinite() ||
                            diemGPA !in 0.0..4.0 -> {

                        binding.edtGpa.error =
                            "GPA phải từ 0.0 đến 4.0"
                    }

                    else -> {

                        val sinhVien = SinhVien(
                            hoTen = hoTen,
                            maSinhVien = maSinhVien,
                            email = if (email.isEmpty()) {
                                "$maSinhVien@ute.udn.vn"
                            } else {
                                email
                            },
                            diemGPA = diemGPA,
                            chuyenNganh = if (chuyenNganh.isEmpty()) {
                                "Công Nghệ Thông Tin"
                            } else {
                                chuyenNganh
                            }
                        )

                        quanLySinhVien.themSinhVien(sinhVien)

                        hopThoai.dismiss()
                    }
                }
            }
        }

        return hopThoai
    }
}