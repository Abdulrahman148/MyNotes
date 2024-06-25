package com.aah.mynotes.ui

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.aah.mynotes.R
import com.aah.mynotes.databinding.ListItemBinding
import com.aah.mynotes.db.Notes

class NotesAdapter(private val onItemClickListener: OnItemClickListener, private val onCardClickListener: OnCardClickListener) : RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    var dataSet: MutableList<Notes> = mutableListOf()

    @SuppressLint("NotifyDataSetChanged")
    fun addDate(items: MutableList<Notes>) {
        dataSet.addAll(items)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun clear() {
        dataSet.clear()
        notifyDataSetChanged()
    }

    class NoteViewHolder(binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {

        val title = binding.listItemTitle
        val subTitle = binding.listItemSubtitle
        val cardView = binding.cardView
        val deleteItem = binding.ImgDeleteItem

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {

        val notesList = dataSet[position]
        holder.title.text = notesList.noteTitle
        holder.subTitle.text = notesList.noteSubTitle


        holder.cardView.setBackgroundResource(R.drawable.list_item_style)

        val context = holder.cardView.context

        holder.deleteItem.setOnClickListener {
            onItemClickListener.onItemClick(dataSet[position])
        }

        holder.cardView.setOnClickListener {
            onCardClickListener.onCardClick(dataSet[position])
        }
    }

    override fun getItemCount() = dataSet.size
}

interface OnItemClickListener {
    fun onItemClick(note: Notes)
}

interface OnCardClickListener {
    fun onCardClick(note: Notes)
}