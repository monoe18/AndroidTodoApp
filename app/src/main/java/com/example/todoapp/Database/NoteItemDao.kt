package com.example.todoapp.Database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface NoteItemDao {
    @get:Query("SELECT * FROM noteItem")
    val noteItem: NoteItem?

    @Update
    fun update(noteItem: NoteItem?): Int

    @Insert
    fun insert(noteItem: NoteItem?)

    @Query("SELECT COUNT(*) from noteItem")
    fun countNoteItem(): Int
}