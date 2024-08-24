package com.example.notesapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.notesapp.database.Notes
import com.example.notesapp.databinding.FragmentReadAndUpdateBinding
import com.example.notesapp.viewmodels.NotesViewModel


class ReadAndUpdateFragment : Fragment() {

    private lateinit var binding: FragmentReadAndUpdateBinding
    private val navArgs: ReadAndUpdateFragmentArgs by navArgs()
    private lateinit var viewModel: NotesViewModel
    private lateinit var navController: NavController
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentReadAndUpdateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[NotesViewModel::class.java]
        navController = findNavController()

        val note = navArgs.MyNotes

        setAData(note)
        binding.updateNoteBtn.setOnClickListener {
            val noteTitle = binding.noteTitleET.text.toString().trim()
            val noteText = binding.noteET.text.toString().trim()
            if (noteTitle.isEmpty()) {
                showToast("Note Title is missing")
            } else if (noteText.isEmpty()) {
                showToast("Note text is missing")
            } else {
                note.noteTitle = noteTitle
                note.note = noteText
                viewModel.update(note){
                    showToast("Note Updated")
                    navController.navigateUp()
                    binding.noteTitleET.setText("")
                    binding.noteET.setText("")
                }

            }
        }
    }
    private fun setAData(note: Notes?) {
        note?.let {
            binding.noteTitleET.setText(it.noteTitle)
            binding.noteET.setText(it.note)
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}