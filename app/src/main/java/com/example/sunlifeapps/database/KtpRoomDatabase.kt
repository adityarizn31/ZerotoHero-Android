package com.example.sunlifeapps.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Ktp::class], version = 1)
abstract class KtpRoomDatabase : RoomDatabase() {
    abstract fun ktpDao() : KtpDao

    companion object {
        @Volatile
        private var INSTANCE : KtpRoomDatabase ?= null

        @JvmStatic
        fun getDatabase (context: Context) : KtpRoomDatabase {
            if (INSTANCE == null) {
                synchronized(KtpRoomDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext, KtpRoomDatabase::class.java, "ktp_database")
                        .build()
                }
            }
            return INSTANCE as KtpRoomDatabase
        }
    }
}