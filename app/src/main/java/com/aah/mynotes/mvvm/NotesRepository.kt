package com.aah.mynotes.mvvm

import android.provider.ContactsContract.CommonDataKinds.Note
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

    suspend fun deleteAllNotes() {
        dao.deleteAllNotes()
    }

    suspend fun deleteNote(note: Notes) {
        dao.deleteNote(note)
    }

}