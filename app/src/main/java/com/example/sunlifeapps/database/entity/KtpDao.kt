package com.example.sunlifeapps.database.entity

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.sunlifeapps.database.dao.Ktp

@Dao
interface KtpDao {
    @Insert(onConflict = OnConflictStrategy.Companion.IGNORE)
    fun insert (ktp: Ktp)

    @Update
    fun update(ktp: Ktp)

    @Delete
    fun delete(ktp: Ktp)

    @Query("SELECT * FROM ktp ORDER BY id ASC")
    fun getAllKtp() : LiveData<List<Ktp>>
}