package com.example.catatanapps.db

import android.provider.BaseColumns

class DatabaseContract {

    internal class NoteColums : BaseColumns {

        companion object {
            const val TABLE_NAME = "note"

            const val _ID = "id"
            const val TITLE = "title"
            const val DESCRIPTION = "description"
            const val DATE = "date"
        }
    }

}