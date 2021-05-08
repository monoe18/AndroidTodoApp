package com.example.todoapp.Database

import android.database.Cursor
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
    fun insert(noteItem: NoteItem?) : Long

    @Query("SELECT COUNT(*) from noteItem")
    fun countNoteItem(): Int

    @Query("SELECT * FROM noteItem WHERE :list_id = list")
    fun getNoteFromList(list_id: Int?) : NoteItem?;


    @Query("SELECT * FROM noteItem WHERE :list_id = list")
    fun getNotesFromListCursor(list_id: Int?) : Cursor;
}