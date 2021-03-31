package com.example.todoapp.Database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*


    @Entity(tableName = "toDoList")
    data class ToDoList(
            @PrimaryKey
            val id: Int,
            val title: String,
            val type: String,
            val priority: String
    )
