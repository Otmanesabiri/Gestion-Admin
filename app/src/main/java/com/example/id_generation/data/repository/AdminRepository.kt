package com.example.id_generation.data.repository

import com.example.id_generation.data.dao.AdminDao
import com.example.id_generation.data.entity.Admin
import kotlinx.coroutines.flow.Flow

class AdminRepository(private val adminDao: AdminDao) {

    val allAdmins: Flow<List<Admin>> = adminDao.getAllAdmins()

    suspend fun insert(admin: Admin): Long {
        return adminDao.insert(admin)
    }

    suspend fun update(admin: Admin) {
        adminDao.update(admin)
    }

    suspend fun delete(admin: Admin) {
        adminDao.delete(admin)
    }

    suspend fun getAdminById(id: Int): Admin? {
        return adminDao.getAdminById(id)
    }
}
