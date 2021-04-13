package com.example.todoapp.Database

import androidx.room.ColumnInfo
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

    data class ListInfoTuple(
        @ColumnInfo(name = "id") val id: Int?,
        @ColumnInfo(name = "type") val type: String?,
        @ColumnInfo(name = "title") val title: String?
    )
