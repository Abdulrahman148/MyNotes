package com.aah.mynotes.ui

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.aah.mynotes.R
import com.aah.mynotes.databinding.ActivityMainBinding
import com.aah.mynotes.databinding.FragmentShowAllNotesBinding
import com.aah.mynotes.databinding.ToolbarBinding
import com.aah.mynotes.db.Notes
import com.aah.mynotes.db.NotesDatabase
import com.aah.mynotes.mvvm.NotesFactoryViewModel
import com.aah.mynotes.mvvm.NotesRepository
import com.aah.mynotes.mvvm.NotesViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class ShowAllNotesFragment : Fragment(), OnItemClickListener, OnCardClickListener {
    private lateinit var binding: FragmentShowAllNotesBinding
    private lateinit var notesViewModel: NotesViewModel
    private lateinit var mNavController: NavController
    private lateinit var deleteIcon: ImageView
    private val noteAdapter by lazy {
        NotesAdapter(this,this)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.toolbar, container, false)
        binding = FragmentShowAllNotesBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mNavController = findNavController()
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
                    NotesDatabase.getDatabase(requireContext()).notesDao()
                )
            )
        )[NotesViewModel::class.java]
    }

    private fun initViews() {
        binding.recyclerView.adapter = noteAdapter
        binding.recyclerView.setHasFixedSize(true)
        deleteIcon = view?.findViewById(R.id.delete_icon)!!
    }

    private fun showNotes() {
        noteAdapter.clear()
        notesViewModel.allNotes.observe(viewLifecycleOwner) {
            noteAdapter.addDate(it.toMutableList())
        }
    }

    private fun onCreateDialog(): Dialog {
        return MaterialAlertDialogBuilder(requireContext(), R.style.MaterialAlertDialog_rounded)
            .setMessage("Delete all notes?")
            .setPositiveButton("OK") { _, _ ->
                notesViewModel.deleteAllNotes()
                noteAdapter.clear()
                Toast.makeText(requireContext(), "All notes deleted!", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.cancel()
            }
            .create()
    }

    private fun addNote() {
        mNavController.navigate(R.id.action_showAllNotesFragment_to_addNoteFragment)
    }

    override fun onItemClick(note: Notes) {
        deleteItemDialog(note).show()
    }

    private fun deleteItemDialog(note: Notes): Dialog {
        return MaterialAlertDialogBuilder(requireContext(), R.style.MaterialAlertDialog_rounded)
            .setMessage("Are you sure to delete this notes?")
            .setPositiveButton("OK") { _, _ ->
                notesViewModel.deleteNote(note)
                noteAdapter.clear()
                Toast.makeText(requireContext(), "Note deleted successfully!", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.cancel()
            }
            .create()
    }

    override fun onCardClick(note: Notes) {

        val action = ShowAllNotesFragmentDirections.actionShowAllNotesFragmentToShowNoteFragment(note.noteTitle, note.noteSubTitle)
        mNavController.navigate(action)
    }

}