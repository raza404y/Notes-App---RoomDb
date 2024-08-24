package com.example.notesapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notesapp.R
import com.example.notesapp.adapters.NotesAdapter
import com.example.notesapp.databinding.FragmentNotesBinding
import com.example.notesapp.viewmodels.NotesViewModel

class NotesFragment : Fragment() {
    private lateinit var binding: FragmentNotesBinding
    private lateinit var viewModel: NotesViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNotesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[NotesViewModel::class.java]
        val navController = findNavController()

        binding.floatingActionButton.setOnClickListener {
            navController.navigate(R.id.action_notesFragment_to_addNotesFragment)
        }

        val adapter = NotesAdapter(requireContext(), navController, viewModel)
        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 2, LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.adapter = adapter

        viewModel.notesList.observe(viewLifecycleOwner, Observer { notes ->
            adapter.submitList(notes)
            binding.recyclerView.post {
                binding.recyclerView.smoothScrollToPosition(0)
            }
        })

        binding.idSV.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val searchQuery = newText ?: ""
                viewModel.searchNote(searchQuery).observe(viewLifecycleOwner, Observer { notes ->
                    adapter.submitList(notes)
                })
                return true
            }
        })

    }

}