package com.aah.mynotes.ui

import android.os.Bundle
import android.os.SystemClock
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.aah.mynotes.R
import com.aah.mynotes.databinding.FragmentAddNoteBinding
import com.aah.mynotes.db.Notes
import com.aah.mynotes.db.NotesDatabase
import com.aah.mynotes.mvvm.NotesFactoryViewModel
import com.aah.mynotes.mvvm.NotesRepository
import com.aah.mynotes.mvvm.NotesViewModel


class AddNoteFragment : Fragment() {
    private lateinit var binding: FragmentAddNoteBinding
    private lateinit var notesViewModel: NotesViewModel
    private lateinit var mNavController: NavController
    private var mLastClickTime: Long = 0



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddNoteBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()
        mNavController = findNavController()

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
                    NotesDatabase.getDatabase(requireContext()).notesDao()
                )
            )
        )[NotesViewModel::class.java]
    }

    private fun insertNote() {
        val title = binding.addTitle.text.toString()
        val subTitle = binding.addSubTitle.text.toString()
        val note = Notes(title, subTitle)

        if (title.isBlank() || subTitle.isBlank()) {
            Toast.makeText(requireContext(), "Please enter all the fields!", Toast.LENGTH_SHORT).show()
        } else {
            notesViewModel.insertNote(note)
            returnToMainActivity()
            Toast.makeText(requireContext(), "Note Saved Successfully!", Toast.LENGTH_SHORT).show()
        }


    }

    private fun returnToMainActivity() {
        mNavController.navigate(R.id.action_addNoteFragment_to_showAllNotesFragment)
    }
}