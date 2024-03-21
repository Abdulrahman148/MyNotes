package com.aah.mynotes.ui

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.graphics.toColor
import androidx.recyclerview.widget.RecyclerView
import com.aah.mynotes.R
import com.aah.mynotes.databinding.ListItemBinding
import com.aah.mynotes.db.Notes
import kotlinx.coroutines.withContext

class NotesAdapter : RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

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
        holder.cardView.setOnLongClickListener {

            holder.cardView.setBackgroundResource(R.drawable.list_item_long_clicked)
            holder.title.setTextColor(Color.parseColor("#2C2C2C"))
            holder.subTitle.setTextColor(Color.parseColor("#2C2C2C"))
            Toast.makeText(context, "Long note clicked ", Toast.LENGTH_SHORT).show()

            return@setOnLongClickListener true
        }
    }

    override fun getItemCount() = dataSet.size
}