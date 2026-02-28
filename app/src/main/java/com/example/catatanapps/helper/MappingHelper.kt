package com.example.catatanapps.helper

import android.database.Cursor
import com.example.catatanapps.Notes
import com.example.catatanapps.db.DatabaseContract

object MappingHelper {

    fun mapCursorToArrayList(notesCursor: Cursor?) : ArrayList<Notes> {

        val notesList = ArrayList<Notes>()

        notesCursor?.apply {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(DatabaseContract.NoteColums._ID))
                val title = getString(getColumnIndexOrThrow(DatabaseContract.NoteColums.TITLE))
                val description = getString(getColumnIndexOrThrow(DatabaseContract.NoteColums.DESCRIPTION))
                val date = getString(getColumnIndexOrThrow(DatabaseContract.NoteColums.DATE))
                notesList.add(Notes(id, title, description, date))
            }
        }
        return notesList
    }
}