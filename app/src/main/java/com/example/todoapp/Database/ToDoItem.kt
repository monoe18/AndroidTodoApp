package com.example.todoapp.Database

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey


@Entity(tableName = "toDoItem",
    foreignKeys = arrayOf(
        ForeignKey(
            entity = ToDoList::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("list"),
            onDelete = ForeignKey.CASCADE
        )
    )
)
data class ToDoItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val done: Boolean,
    val description: String,
    val list: Int

)
