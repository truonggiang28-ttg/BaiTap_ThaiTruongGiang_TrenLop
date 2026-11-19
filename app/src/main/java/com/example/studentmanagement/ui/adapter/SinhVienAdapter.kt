package com.example.studentmanagement.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.studentmanagement.databinding.ItemSinhVienBinding
import com.example.studentmanagement.model.SinhVien

class SinhVienAdapter(
    private val khiChon: (SinhVien) -> Unit,
    private val khiXoa: (SinhVien) -> Unit
) : ListAdapter<SinhVien, SinhVienAdapter.SinhVienViewHolder>(
    SoSanhSinhVien()
) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SinhVienViewHolder {

        val binding = ItemSinhVienBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return SinhVienViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: SinhVienViewHolder,
        position: Int
    ) {
        holder.hienThi(getItem(position))
    }

    inner class SinhVienViewHolder(
        private val binding: ItemSinhVienBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun hienThi(sinhVien: SinhVien) {

            binding.tvHoTen.text = sinhVien.hoTen

            binding.tvMaSinhVien.text =
                "MSSV: ${sinhVien.maSinhVien}"

            binding.tvEmail.text = sinhVien.email

            binding.tvGpa.text =
                "GPA: %.2f".format(sinhVien.diemGPA)

            binding.tvChuyenNganh.text =
                sinhVien.chuyenNganh

            binding.tvAvatar.text =
                sinhVien.hoTen.trim()
                    .take(1)
                    .uppercase()

            binding.root.setOnClickListener {
                khiChon(sinhVien)
            }

            binding.btnXoa.setOnClickListener {
                khiXoa(sinhVien)
            }
        }
    }

    class SoSanhSinhVien : DiffUtil.ItemCallback<SinhVien>() {

        override fun areItemsTheSame(
            sinhVienCu: SinhVien,
            sinhVienMoi: SinhVien
        ): Boolean {

            return sinhVienCu.id == sinhVienMoi.id
        }

        override fun areContentsTheSame(
            sinhVienCu: SinhVien,
            sinhVienMoi: SinhVien
        ): Boolean {

            return sinhVienCu == sinhVienMoi
        }
    }
}