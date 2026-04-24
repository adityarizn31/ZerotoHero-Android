package com.example.sunlifeapps.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.sunlifeapps.database.dao.KK
import com.example.sunlifeapps.database.dao.Ktp
import com.example.sunlifeapps.database.entity.KKDao
import com.example.sunlifeapps.database.entity.KtpDao

@Database(
    entities = [
        Ktp::class,
        KK::class
    ],
    version = 1
)

abstract class SiLancarDatabase : RoomDatabase() {

    abstract fun ktpDao() : KtpDao
    abstract fun kkDao() : KKDao
}