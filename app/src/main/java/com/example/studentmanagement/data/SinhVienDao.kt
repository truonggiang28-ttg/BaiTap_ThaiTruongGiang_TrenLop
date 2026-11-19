package com.example.studentmanagement.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

import com.example.studentmanagement.model.SinhVien

import kotlinx.coroutines.flow.Flow

@Dao
interface SinhVienDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun themSinhVien(
        sinhVien: SinhVien
    ): Long

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun themDanhSach(
        danhSach: List<SinhVien>
    )

    @Update
    suspend fun suaSinhVien(
        sinhVien: SinhVien
    )

    @Delete
    suspend fun xoaSinhVien(
        sinhVien: SinhVien
    )

    @Query(
        "DELETE FROM students_table WHERE id = :id"
    )
    suspend fun xoaTheoId(id: Int)

    @Query(
        "SELECT * FROM students_table ORDER BY id DESC"
    )
    fun layDanhSachSinhVien(): Flow<List<SinhVien>>

    @Query(
        """
        SELECT * FROM students_table
        WHERE student_code = :maSinhVien
        LIMIT 1
        """
    )
    suspend fun timTheoMa(
        maSinhVien: String
    ): SinhVien?

    @Query("SELECT COUNT(*) FROM students_table")
    suspend fun demSinhVien(): Int
}