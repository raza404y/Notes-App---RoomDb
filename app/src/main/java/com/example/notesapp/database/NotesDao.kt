package com.example.notesapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface NotesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(notes: Notes)

    @Update
    suspend fun update(notes: Notes)

    @Delete
    suspend fun delete(notes: Notes)

    @Query("select * from notes_table order by id desc")
     fun getAllNotes():LiveData<List<Notes>>



}