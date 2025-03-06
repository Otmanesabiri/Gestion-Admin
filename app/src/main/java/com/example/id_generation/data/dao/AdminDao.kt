package com.example.id_generation.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.id_generation.data.entity.Admin
import kotlinx.coroutines.flow.Flow

@Dao
interface AdminDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(admin: Admin): Long

    @Update
    suspend fun update(admin: Admin)

    @Delete
    suspend fun delete(admin: Admin)

    @Query("SELECT * FROM Admin WHERE id = :id")
    suspend fun getAdminById(id: Int): Admin?

    @Query("SELECT * FROM Admin")
    fun getAllAdmins(): Flow<List<Admin>>
}
