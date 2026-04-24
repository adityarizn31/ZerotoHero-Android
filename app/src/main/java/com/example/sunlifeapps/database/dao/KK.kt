package com.example.sunlifeapps.database.dao

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "kk")
@Parcelize
data class KK (
    @PrimaryKey(autoGenerate = true) val id : Int = 0,
    val nama : String,
    val nik : String,
    val tempatLahir : String,
    val tanggalLahir : String,
    val alama : String,
    val foto : String,
    val rt : String,
    val rw : String,
    val desa : String,
    val kecamatan : String,
    val kabupaten : String,
) : Parcelable