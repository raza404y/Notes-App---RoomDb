package com.example.notesapp.ui

import android.os.Bundle
import android.os.Message
import android.provider.ContactsContract.CommonDataKinds.Note
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.notesapp.R
import com.example.notesapp.database.Notes
import com.example.notesapp.database.NotesDao
import com.example.notesapp.database.NotesDatabase
import com.example.notesapp.databinding.FragmentAddNotesBinding
import com.example.notesapp.viewmodels.NotesViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AddNotesFragment : Fragment() {
    private lateinit var binding: FragmentAddNotesBinding
    private lateinit var viewModel: NotesViewModel
    private lateinit var navController: NavController
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentAddNotesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[NotesViewModel::class.java]
        navController = findNavController()

        binding.saveBtn.setOnClickListener {
            saveNote()
        }

    }

    private fun saveNote() {
        val title = binding.noteTitleET.text.toString().trim()
        val noteText = binding.noteET.text.toString().trim()

       if (title.isEmpty()) {
           showToast("Title is missing")
       }else if (noteText.isEmpty()){
            showToast("Note is missing")
        }else {
           val simpleDateFormat = SimpleDateFormat("E, dd MMM yyyy HH:mm:ss a", Locale.getDefault())
           val currentDateTime = simpleDateFormat.format(Date())
           val notes = Notes(
               noteTitle = title,
               note = noteText,
               dateTime = currentDateTime
           )
           viewModel.insert(notes) {
               showToast("Note Inserted")
               navController.navigateUp()
               binding.noteTitleET.setText("")
               binding.noteET.setText("")
           }
       }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}