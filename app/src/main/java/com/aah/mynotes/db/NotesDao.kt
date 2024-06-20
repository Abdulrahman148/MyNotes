package com.aah.mynotes.db

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface NotesDao {
    @Insert
    suspend fun insertNote(notes: Notes)

    @Query("SELECT * FROM NOTES")
    fun getAllNotes() : LiveData<List<Notes>>

    @Query("DELETE FROM NOTES")
    suspend fun deleteAllNotes()

    @Delete
    suspend fun deleteNote(notes: Notes)

}