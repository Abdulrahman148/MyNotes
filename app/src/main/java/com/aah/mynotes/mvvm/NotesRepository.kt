package com.aah.mynotes.mvvm

import androidx.lifecycle.LiveData
import com.aah.mynotes.db.Notes
import com.aah.mynotes.db.NotesDao

class NotesRepository(private val dao: NotesDao) {

    fun allNotes() : LiveData<List<Notes>> {
        return dao.getAllNotes()
    }

    suspend fun insertNote(notes: Notes) {
        dao.insertNote(notes)
    }

    suspend fun deleteNotes() {
        dao.deleteAllNotes()
    }

}