package com.example.myapplication

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User (
    val username: String,
    val email: String,
    val address: String,
    val numberwa: String,
) : Parcelable
