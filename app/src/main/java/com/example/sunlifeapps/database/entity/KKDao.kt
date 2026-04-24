package com.example.sunlifeapps.database.entity

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.sunlifeapps.database.dao.KK

@Dao
interface KKDao {
    @Insert(onConflict = OnConflictStrategy.Companion.IGNORE)
    fun insert(kk: KK)

    @Update
    fun update(kk: KK)

    @Delete
    fun delete(kk: KK)

    @Query("SELECT * FROM kk ORDER BY id ASC")
    fun getAllKK() : LiveData<List<KK>>
}