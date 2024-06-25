package com.aah.mynotes.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import com.aah.mynotes.R
import com.aah.mynotes.databinding.FragmentShowAllNotesBinding
import com.aah.mynotes.databinding.FragmentShowNoteBinding
import com.aah.mynotes.db.Notes


class ShowNoteFragment : Fragment() {

    private lateinit var binding: FragmentShowNoteBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentShowNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args: ShowNoteFragmentArgs by navArgs()
        binding.showNoteTitleTextView.text = args.noteTitle
        binding.showNoteSubTitleTextView.text = args.noteSubTitle

    }

}