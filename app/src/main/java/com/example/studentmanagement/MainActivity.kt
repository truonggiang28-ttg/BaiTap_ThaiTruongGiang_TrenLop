package com.example.studentmanagement

import android.os.Bundle

import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity

import com.example.studentmanagement.data.AppDatabase
import com.example.studentmanagement.repository.SinhVienRepository
import com.example.studentmanagement.ui.DanhSachSinhVienFragment
import com.example.studentmanagement.viewmodel.SinhVienViewModel
import com.example.studentmanagement.viewmodel.SinhVienViewModelFactory

class MainActivity : AppCompatActivity() {

    private val database by lazy {
        AppDatabase.layDatabase(applicationContext)
    }

    private val repository by lazy {
        SinhVienRepository(database)
    }

    private val quanLySinhVien: SinhVienViewModel by viewModels {
        SinhVienViewModelFactory(repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {

            supportFragmentManager.beginTransaction()
                .replace(
                    R.id.fragment_container_view,
                    DanhSachSinhVienFragment()
                )
                .commit()
        }
    }
}