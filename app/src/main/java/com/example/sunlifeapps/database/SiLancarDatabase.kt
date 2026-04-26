package com.example.sunlifeapps.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
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

    companion object {
        @Volatile
        private var INSTANCE : SiLancarDatabase ?= null

        fun getDatabase(context : Context) : SiLancarDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(context.applicationContext, SiLancarDatabase::class.java, "silancar_db").build()
                INSTANCE = instance
                instance
            }
        }
    }
}