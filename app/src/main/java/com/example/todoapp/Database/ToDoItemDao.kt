package com.example.todoapp.Database

import android.database.Cursor
import androidx.room.*

@Dao
interface ToDoItemDao {
    @get:Query("SELECT * FROM toDoItem")
    val toDoItem: ToDoItem?

    @Update
    fun update(toDoItem: ToDoItem?): Int

    @Insert
    fun insert(toDoItem: ToDoItem?) : Long

    @Query("SELECT COUNT(*) FROM toDoItem")
    fun countToDoItem(): Int

    @Query("SELECT * FROM toDoItem WHERE :list_id = list")
    fun getItemsFromList(list_id: Int?) : List<ToDoItemTuple>;

    @Query("SELECT * FROM toDoItem WHERE :list_id = list")
    fun getItemsFromListCursor(list_id: Int?) : Cursor;

    @Query("DELETE FROM toDoItem WHERE :list_id = list")
    fun delete(list_id: Int?) : Int

    //@Delete
    //fun delete(list_id: Int?)
}