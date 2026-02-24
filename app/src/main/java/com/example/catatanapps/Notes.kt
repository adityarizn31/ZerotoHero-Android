package com.example.catatanapps

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Notes(
    val id : Int = 0,
    val title : String ?= null,
    val description : String ?= null,
    val date : String ?= null,
) : Parcelable
