package com.example.notesapp.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.room.Delete
import com.example.notesapp.database.Notes
import com.example.notesapp.database.NotesDatabase
import com.example.notesapp.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NotesViewModel(application: Application) : AndroidViewModel(application) {
    private lateinit var repository: Repository
    lateinit var notesList: LiveData<List<Notes>>

    init {
        val dao = NotesDatabase.createDatabase(application).getNotesDao()
        repository = Repository(dao)
        viewModelScope.launch(Dispatchers.IO) {
            notesList = repository.getAllNotes()
        }
    }

    fun insert(notes: Notes, inserted: () -> Unit) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(notes)
        withContext(Dispatchers.Main) {
            inserted()
        }
    }

    fun delete(notes: Notes, onDeleted: () -> Unit) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(notes)
        withContext(Dispatchers.Main) {
            Delete()
        }
    }

    fun update(notes: Notes, Updated: () -> Unit) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(notes)
        withContext(Dispatchers.Main) {
            Updated()
        }
    }

    fun searchNote(query: String): LiveData<List<Notes>> {
        return if (query.isEmpty()) {
            repository.getAllNotes() // return the full list if query is empty
        } else {
            repository.searchNote(query)
        }
    }


}