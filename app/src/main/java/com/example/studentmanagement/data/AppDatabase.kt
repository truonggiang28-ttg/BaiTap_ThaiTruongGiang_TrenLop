package com.example.studentmanagement.data

import android.content.Context

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

import com.example.studentmanagement.model.SinhVien

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [SinhVien::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun sinhVienDao(): SinhVienDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun layDatabase(context: Context): AppDatabase {

            return INSTANCE ?: synchronized(this) {

                val database = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "ute_student_room.db"
                )
                    .addCallback(object : Callback() {

                        override fun onCreate(
                            db: SupportSQLiteDatabase
                        ) {
                            super.onCreate(db)

                            // Chèn mẫu trực tiếp trong callback SQLite.
                            // Không phụ thuộc INSTANCE đã khởi tạo hay chưa.
                            val duLieuMau = listOf(
                                SinhVien(
                                    hoTen = "Nguyễn Văn An",
                                    maSinhVien = "20110001",
                                    email = "an.nv@ute.udn.vn",
                                    diemGPA = 3.85,
                                    chuyenNganh = "Công Nghệ Thông Tin"
                                ),
                                SinhVien(
                                    hoTen = "Trần Thị Mai",
                                    maSinhVien = "20110002",
                                    email = "mai.tt@ute.udn.vn",
                                    diemGPA = 3.92,
                                    chuyenNganh = "Kỹ Thuật Phần Mềm"
                                ),
                                SinhVien(
                                    hoTen = "Lê Hoàng Phúc",
                                    maSinhVien = "20110003",
                                    email = "phuc.lh@ute.udn.vn",
                                    diemGPA = 3.45,
                                    chuyenNganh = "An Toàn Thông Tin"
                                ),
                                SinhVien(
                                    hoTen = "Phạm Quốc Dũng",
                                    maSinhVien = "20110004",
                                    email = "dung.pq@ute.udn.vn",
                                    diemGPA = 3.60,
                                    chuyenNganh = "Mạng Máy Tính"
                                )
                            )

                            duLieuMau.forEach { sinhVien ->

                                db.execSQL(
                                    """
                                    INSERT INTO students_table
                                    (full_name, student_code, email, gpa, major)
                                    VALUES (?, ?, ?, ?, ?)
                                    """.trimIndent(),
                                    arrayOf(
                                        sinhVien.hoTen,
                                        sinhVien.maSinhVien,
                                        sinhVien.email,
                                        sinhVien.diemGPA,
                                        sinhVien.chuyenNganh
                                    )
                                )
                            }
                        }
                    })
                    .build()

                INSTANCE = database

                database
            }
        }
    }
}