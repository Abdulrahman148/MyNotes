package com.aah.mynotes.ui

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TimePicker
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat.startActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.aah.mynotes.R
import com.aah.mynotes.databinding.ActivityMainBinding
import com.aah.mynotes.databinding.ListItemBinding
import com.aah.mynotes.databinding.ToolbarBinding
import com.aah.mynotes.db.Notes
import com.aah.mynotes.db.NotesDao
import com.aah.mynotes.db.NotesDatabase
import com.aah.mynotes.mvvm.NotesFactoryViewModel
import com.aah.mynotes.mvvm.NotesRepository
import com.aah.mynotes.mvvm.NotesViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity(), OnItemClickListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var notesViewModel: NotesViewModel
    private lateinit var deleteIcon: ImageView
    private val noteAdapter by lazy {
        NotesAdapter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViewModel()
        initViews()
        showNotes()

        deleteIcon.setOnClickListener {
            onCreateDialog().show()
        }

        binding.fab.setOnClickListener {
            addNote()
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

    private fun initViews() {
        binding.recyclerView.adapter = noteAdapter
        binding.recyclerView.setHasFixedSize(true)
        deleteIcon = findViewById(R.id.delete_icon)
    }

    private fun showNotes() {
        noteAdapter.clear()
        notesViewModel.allNotes.observe(this@MainActivity) {
            noteAdapter.addDate(it.toMutableList())
        }
    }

    private fun onCreateDialog(): Dialog {
        return MaterialAlertDialogBuilder(this, R.style.MaterialAlertDialog_rounded)
            .setMessage("Delete all notes?")
            .setPositiveButton("OK") { _, _ ->
                notesViewModel.deleteAllNotes()
                noteAdapter.clear()
                Toast.makeText(this, "All notes deleted!", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.cancel()
            }
            .create()
    }

    private fun addNote() {
        intent = Intent(this@MainActivity, AddNotesActivity::class.java)
        startActivity(intent)
    }

    override fun onItemClick(note: Notes) {
        deleteItemDialog(note).show()
    }

    private fun deleteItemDialog(note: Notes): Dialog {
        return MaterialAlertDialogBuilder(this, R.style.MaterialAlertDialog_rounded)
            .setMessage("Are you sure to delete this notes?")
            .setPositiveButton("OK") { _, _ ->
                notesViewModel.deleteNote(note)
                noteAdapter.clear()
                Toast.makeText(this, "Note deleted successfully!", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.cancel()
            }
            .create()
    }

}