package com.aah.mynotes.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.aah.mynotes.databinding.ActivityAddNotesBinding
import com.aah.mynotes.db.Notes
import com.aah.mynotes.db.NotesDatabase
import com.aah.mynotes.mvvm.NotesFactoryViewModel
import com.aah.mynotes.mvvm.NotesRepository
import com.aah.mynotes.mvvm.NotesViewModel

class AddNotesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddNotesBinding
    private lateinit var notesViewModel: NotesViewModel
    private var mLastClickTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNotesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViewModel()

        binding.addNotesSaveBtn.setOnClickListener {
            if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                return@setOnClickListener
            }
            mLastClickTime = SystemClock.elapsedRealtime()
            insertNote()
        }

        binding.addNotesCancelBtn.setOnClickListener {
            returnToMainActivity()
        }


    }

    private fun initViewModel() {
        notesViewModel = ViewModelProvider(
            this,
            NotesFactoryViewModel(
                NotesRepository(
                    NotesDatabase.getDatabase(applicationContext).notesDao()
                )
            )
        )[NotesViewModel::class.java]
    }

    private fun insertNote() {
        val title = binding.addTitle.text.toString()
        val subTitle = binding.addSubTitle.text.toString()
        val note = Notes(title, subTitle)

        if (title.isBlank() || subTitle.isBlank()) {
            Toast.makeText(this, "Please enter all the fields!", Toast.LENGTH_SHORT).show()
        } else {
            notesViewModel.insertNote(note)
            returnToMainActivity()
            Toast.makeText(this, "Note Saved Successfully!", Toast.LENGTH_SHORT).show()
        }


    }

    private fun returnToMainActivity() {
        intent = Intent(this@AddNotesActivity, MainActivity::class.java)
        startActivity(intent)
    }

}