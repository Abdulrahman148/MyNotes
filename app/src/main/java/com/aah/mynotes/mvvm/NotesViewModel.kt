package com.aah.mynotes.mvvm

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aah.mynotes.db.Notes
import com.aah.mynotes.ui.MainActivity
import kotlinx.coroutines.launch

class NotesViewModel(private val repository: NotesRepository) : ViewModel() {

    var allNotes: LiveData<List<Notes>> = repository.allNotes()


    fun insertNote(notes: Notes) = viewModelScope.launch {
        repository.insertNote(notes)
    }

    fun deleteAllNotes() = viewModelScope.launch {
        repository.deleteNotes()
    }

}