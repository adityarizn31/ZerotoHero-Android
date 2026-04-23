package com.example.sunlifeapps.database

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "ktp")
@Parcelize
data class Ktp (
    @PrimaryKey(autoGenerate = true) val id : Int = 0,
    val nama : String,
    val nik : String,
    val tempatLahir : String,
    val tanggalLahir : String,
    val alamat : String,
    val foto : String,
) : Parcelable