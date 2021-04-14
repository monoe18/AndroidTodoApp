package com.example.todoapp.Database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

    @Dao
    interface ToDoListDao {
        @get:Query("SELECT * FROM toDoList")
        val toDoList: ToDoList?

        @Update
        fun update(toDoList: ToDoList?): Int

        @Insert
        fun insert(toDoList: ToDoList?): Long


        @Query("SELECT COUNT(*) from toDoList")
        fun countToDoList(): Int
    }
