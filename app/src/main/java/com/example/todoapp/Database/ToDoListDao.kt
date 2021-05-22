package com.example.todoapp.Database

import android.database.Cursor
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

        @Query("SELECT * from todoList")
        fun getListInfo(): List<ListInfoTuple>;

        @Query("DELETE FROM toDoList WHERE :list_id = id")
        fun delete(list_id: Int?): Int

        @Query("SELECT * FROM toDoList WHERE :list_id = id")
        fun listFromID(list_id: Int?): ToDoList?

        @Query("SELECT * FROM todolist")
        fun getListsCursor() : Cursor;

        @Query("DELETE FROM toDoList WHERE :list_id = id")
        fun delete(list_id: Int?): Int
    }

