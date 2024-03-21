package com.aah.mynotes.ui

import android.app.Application
import com.aah.mynotes.db.NotesDatabase
import com.aah.mynotes.db.NotesDatabase.Companion.getDatabase
import com.aah.mynotes.mvvm.NotesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class NoteApplication : Application() {
    // No need to cancel this scope as it'll be torn down with the process
   /* private val applicationScope = CoroutineScope(SupervisorJob())

    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    private val database by lazy { getDatabase(this, applicationScope) }
    val repository by lazy { NotesRepository(database.notesDao()) }*/
}