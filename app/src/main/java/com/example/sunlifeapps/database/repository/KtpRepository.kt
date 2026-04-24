package com.example.sunlifeapps.database.repository

import com.example.sunlifeapps.database.dao.Ktp
import com.example.sunlifeapps.database.entity.KtpDao

class KtpRepository (private val ktpDao: KtpDao) {

    fun getAll() = ktpDao.getAllKtp()

    suspend fun insert(ktp: Ktp) {
        ktpDao.insert(ktp)
    }

    suspend fun delete(ktp: Ktp) {
        ktpDao.delete(ktp)
    }

    suspend fun update(ktp: Ktp) {
        ktpDao.update(ktp)
    }
}