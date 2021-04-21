package com.example.todoapp.Database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*


    @Entity(tableName = "toDoList")
    data class ToDoList(
            @PrimaryKey(autoGenerate = true)
            val id: Int = 0,
            var title: String,
            val type: String,
            val priority: String
    )
