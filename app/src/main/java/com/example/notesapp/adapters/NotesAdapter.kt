package com.example.notesapp.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ContentInfoCompat.Flags
import androidx.navigation.NavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.database.Notes
import com.example.notesapp.databinding.NotesRvDesignBinding
import com.example.notesapp.ui.ModalBottomSheetDialog
import com.example.notesapp.ui.NotesFragmentDirections
import com.example.notesapp.ui.ReadAndUpdateFragment
import com.example.notesapp.viewmodels.NotesViewModel

class NotesAdapter(private val context: Context, private val navController: NavController,private val viewModel: NotesViewModel) : ListAdapter<Notes, NotesAdapter.ViewHolder>(diffCallback) {

    inner class ViewHolder(private val binding: NotesRvDesignBinding) : RecyclerView.ViewHolder(binding.root) {
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

        holder.itemView.setOnLongClickListener {
            // Create an instance of ModalBottomSheetDialog
            val modalBottomSheet = ModalBottomSheetDialog().apply {
                // Optionally pass data to the bottom sheet
                setNote(currentItem)
                onDeleteClick = { note ->
                    // Perform the delete operation
                    viewModel.delete(note){
                        Toast.makeText(context, "Note deleted", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            // Show the bottom sheet dialog
            modalBottomSheet.show((context as AppCompatActivity).supportFragmentManager, ModalBottomSheetDialog.TAG)

            true // return true to indicate that the long click event is handled
        }

    }
}
