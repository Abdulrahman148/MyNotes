package com.aah.mynotes.db

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Entity(tableName = "NOTES")
@Parcelize
data class Notes (
    val noteTitle: String,
    val noteSubTitle: String,
    @PrimaryKey(autoGenerate = true)
    var noteId: Int = 0) : Parcelable