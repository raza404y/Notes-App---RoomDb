package com.example.notesapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Notes::class], version = 1, exportSchema = false)
abstract class NotesDatabase: RoomDatabase(){

    abstract fun getNotesDao(): NotesDao


    companion object{
        private var INSTANCE: NotesDatabase? = null
        fun createDatabase(context: Context):NotesDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NotesDatabase::class.java,
                    "notes_database"
                )
                    .allowMainThreadQueries()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

}