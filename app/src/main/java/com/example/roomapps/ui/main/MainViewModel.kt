package com.example.roomapps.ui.main

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.roomapps.database.Note
import com.example.roomapps.repository.NoteRepository

class MainViewModel (application: Application) : ViewModel() {

    private val mNoteRepository : NoteRepository = NoteRepository(application)

    fun getAllNotes() : LiveData<List<Note>> = mNoteRepository.getAllNotes()
}