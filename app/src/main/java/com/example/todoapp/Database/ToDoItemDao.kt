package com.example.todoapp.Database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ToDoItemDao {
    @get:Query("SELECT * FROM toDoItem")
    val toDoItem: ToDoItem?

    @Update
    fun update(toDoItem: ToDoItem?): Int

    @Insert
    fun insert(toDoItem: ToDoItem?)

    @Query("SELECT COUNT(*) from toDoItem")
    fun countToDoItem(): Int
}