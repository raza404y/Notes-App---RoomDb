package com.example.notesapp.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "notes_table")
data class Notes(

    @PrimaryKey(autoGenerate = true)
    val id: Int?= null,

    @ColumnInfo(name = "Title")
    var noteTitle: String,

    @ColumnInfo(name = "Note")
    var note: String,

    @ColumnInfo(name = "Date_Time")
    var dateTime: String
):Serializable
