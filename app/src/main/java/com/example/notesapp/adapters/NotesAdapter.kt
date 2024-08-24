package com.example.notesapp.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.ContentInfoCompat.Flags
import androidx.navigation.NavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.database.Notes
import com.example.notesapp.databinding.NotesRvDesignBinding
import com.example.notesapp.ui.NotesFragmentDirections
import com.example.notesapp.ui.ReadAndUpdateFragment

class NotesAdapter(private val context: Context,val navController: NavController) : ListAdapter<Notes, NotesAdapter.ViewHolder>(diffCallback) {

    inner class ViewHolder(val binding: NotesRvDesignBinding) : RecyclerView.ViewHolder(binding.root) {
        fun setData(notes: Notes) {
            notes.let {
                binding.titleTv.text = it.noteTitle
                binding.dateTv.text = it.dateTime
                binding.noteTv.text = it.note
            }
        }
    }

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<Notes>() {
            override fun areItemsTheSame(oldItem: Notes, newItem: Notes): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Notes, newItem: Notes): Boolean {
                return oldItem == newItem
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(NotesRvDesignBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.setData(currentItem)

        holder.itemView.setOnClickListener {
            val action = NotesFragmentDirections.actionNotesFragmentToReadAndUpdateFragment(currentItem)
            navController.navigate(action)
        }

//        holder.itemView.setOnClickListener {
//            val intent = Intent(context, ReadAndUpdateFragment::class.java)
//            intent.putExtra("oldNote", currentItem)
//            context.startActivity(intent)
//        }

    }
}
