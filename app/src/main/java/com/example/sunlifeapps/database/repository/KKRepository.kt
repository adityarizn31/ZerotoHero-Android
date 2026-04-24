package com.example.sunlifeapps.database.repository

import com.example.sunlifeapps.database.dao.KK
import com.example.sunlifeapps.database.entity.KKDao

class KKRepository (private val kkDao: KKDao) {

    fun getAll() = kkDao.getAllKK()

    suspend fun insert(kk: KK) {
        kkDao.insert(kk)
    }

    suspend fun delete(kk: KK) {
        kkDao.delete(kk)
    }

    suspend fun update(kk: KK) {
        kkDao.update(kk)
    }

}