package com.example.notesapp.repository

import androidx.lifecycle.LiveData
import com.example.notesapp.database.Notes
import com.example.notesapp.database.NotesDao

class Repository(private val notesDao: NotesDao) {

    suspend fun insert(notes: Notes){
        notesDao.insert(notes)
    }

    suspend fun update(notes: Notes){
        notesDao.update(notes)
    }

    suspend fun delete(notes: Notes){
        notesDao.delete(notes)
    }

     fun getAllNotes():LiveData<List<Notes>>{
        return notesDao.getAllNotes()
    }

}