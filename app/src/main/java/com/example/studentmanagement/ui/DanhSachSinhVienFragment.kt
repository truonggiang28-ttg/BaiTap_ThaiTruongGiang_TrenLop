package com.example.studentmanagement.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import androidx.appcompat.app.AlertDialog
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.studentmanagement.R
import com.example.studentmanagement.databinding.FragmentDanhSachSinhVienBinding
import com.example.studentmanagement.model.SinhVien
import com.example.studentmanagement.ui.adapter.SinhVienAdapter
import com.example.studentmanagement.viewmodel.SinhVienViewModel
import com.example.studentmanagement.MainActivity
import com.example.studentmanagement.data.AppDatabase
import com.example.studentmanagement.repository.SinhVienRepository
import com.example.studentmanagement.viewmodel.SinhVienViewModelFactory

class DanhSachSinhVienFragment : Fragment() {

    // VIEW BINDING
    private var _binding: FragmentDanhSachSinhVienBinding? = null

    private val binding
        get() = _binding!!

    // VIEWMODEL
    private val quanLySinhVien: SinhVienViewModel by activityViewModels {

        // LẤY DATABASE
        val coSoDuLieu = AppDatabase.layDatabase(
            requireContext().applicationContext
        )

        // KHỞI TẠO REPOSITORY
        val khoDuLieu = SinhVienRepository(
            coSoDuLieu
        )

        // CUNG CẤP FACTORY CHO VIEWMODEL
        SinhVienViewModelFactory(khoDuLieu)
    }

    // ADAPTER
    private lateinit var adapterSinhVien: SinhVienAdapter

    // DANH SÁCH SINH VIÊN
    private var danhSachGoc = listOf<SinhVien>()

    // TỪ KHÓA TÌM KIẾM
    private var tuKhoa = ""

    // 1. KHỞI TẠO GIAO DIỆN
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentDanhSachSinhVienBinding.inflate(
            inflater,
            container,
            false
        )

        return binding.root
    }

    // 2. KHỞI TẠO CÁC CHỨC NĂNG
    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        khoiTaoRecyclerView()

        quanSatDuLieu()

        xuLySuKien()
    }

    // 3. KHỞI TẠO RECYCLERVIEW
    private fun khoiTaoRecyclerView() {

        adapterSinhVien = SinhVienAdapter(

            khiChon = { sinhVien ->
                hienThiChiTiet(sinhVien)
            },

            khiXoa = { sinhVien ->
                xacNhanXoa(sinhVien)
            }
        )

        binding.rvSinhVien.apply {

            layoutManager = LinearLayoutManager(
                requireContext()
            )

            adapter = adapterSinhVien

            setHasFixedSize(false)
        }

        adapterSinhVien.stateRestorationPolicy =
            RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
    }

    // 4. QUAN SÁT DỮ LIỆU
    private fun quanSatDuLieu() {

        // QUAN SÁT DANH SÁCH SINH VIÊN
        quanLySinhVien.danhSachSinhVien.observe(
            viewLifecycleOwner
        ) { danhSach ->

            danhSachGoc = danhSach

            binding.tvSoLuong.text =
                "${danhSach.size} sinh viên"

            capNhatDanhSach()
        }

        // QUAN SÁT THÔNG BÁO
        quanLySinhVien.thongBao.observe(
            viewLifecycleOwner
        ) { noiDung ->

            if (!noiDung.isNullOrBlank()) {

                Toast.makeText(
                    requireContext(),
                    noiDung,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    // 5. CẬP NHẬT DANH SÁCH
    private fun capNhatDanhSach() {

        val ketQua = danhSachGoc.filter { sinhVien ->

            sinhVien.hoTen.contains(
                tuKhoa,
                ignoreCase = true
            ) ||
                    sinhVien.maSinhVien.contains(
                        tuKhoa,
                        ignoreCase = true
                    )
        }

        adapterSinhVien.submitList(ketQua)

        binding.tvDanhSachRong.visibility =
            if (ketQua.isEmpty()) {
                View.VISIBLE
            } else {
                View.GONE
            }
    }

    // 6. XỬ LÝ SỰ KIỆN
    private fun xuLySuKien() {

        // TÌM KIẾM SINH VIÊN
        binding.edtTimKiem.doAfterTextChanged { noiDung ->

            tuKhoa = noiDung.toString()

            capNhatDanhSach()
        }

        // THÊM SINH VIÊN
        binding.fabThem.setOnClickListener {

            ThemSinhVienDialogFragment().show(
                childFragmentManager,
                "THEM_SINH_VIEN"
            )
        }

        // ĐỒNG BỘ CLOUD
        binding.toolbar.setOnMenuItemClickListener { menu ->

            when (menu.itemId) {

                R.id.action_sync -> {

                    quanLySinhVien.dongBoCloud()

                    true
                }

                else -> false
            }
        }
    }

    // 7. HIỂN THỊ CHI TIẾT SINH VIÊN
    private fun hienThiChiTiet(
        sinhVien: SinhVien
    ) {

        AlertDialog.Builder(requireContext())
            .setTitle("THÔNG TIN SINH VIÊN")
            .setMessage(
                """
                ID: ${sinhVien.id}

                Họ tên: ${sinhVien.hoTen}

                MSSV: ${sinhVien.maSinhVien}

                Email: ${sinhVien.email}

                GPA: ${"%.2f".format(sinhVien.diemGPA)}

                Chuyên ngành: ${sinhVien.chuyenNganh}
                """.trimIndent()
            )
            .setPositiveButton("Đóng", null)
            .show()
    }

    // 8. XÁC NHẬN XÓA SINH VIÊN
    private fun xacNhanXoa(
        sinhVien: SinhVien
    ) {

        AlertDialog.Builder(requireContext())
            .setTitle("XÁC NHẬN XÓA")
            .setMessage(
                "Bạn có chắc muốn xóa sinh viên ${sinhVien.hoTen}?"
            )
            .setPositiveButton("Xóa") { _, _ ->

                quanLySinhVien.xoaSinhVien(sinhVien)
            }
            .setNegativeButton("Hủy", null)
            .show()
    }

    // 9. GIẢI PHÓNG VIEW BINDING
    override fun onDestroyView() {

        binding.rvSinhVien.adapter = null

        _binding = null

        super.onDestroyView()
    }
}