package com.example.catatanapps.db

import android.provider.BaseColumns

class DatabaseContract {

    internal class NoteColums : BaseColumns {

        companion object {
            const val TABLE_NAME = "note"

            const val _ID = "id"
            const val title = "title"
            const val description = "description"
            const val date = "date"
        }
    }

}