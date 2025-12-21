package com.example.myapplication

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User (
    val username : String,
    val email : String,
    val address : String,
    val jobs : String,
) : Parcelable