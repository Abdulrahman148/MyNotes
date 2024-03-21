package com.aah.mynotes.mvvm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class NotesFactoryViewModel constructor(private val repository: NotesRepository): ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(NotesViewModel::class.java)) {
            NotesViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}