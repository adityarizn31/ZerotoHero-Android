package com.example.myapplication

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User (
    val name : String,
    val email : String,
) : Parcelable