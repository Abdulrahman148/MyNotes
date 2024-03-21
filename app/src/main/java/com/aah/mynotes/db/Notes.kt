package com.aah.mynotes.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "NOTES")
data class Notes (
    val noteTitle: String,
    val noteSubTitle: String,
    @PrimaryKey(autoGenerate = true)
    var noteId: Int = 0)