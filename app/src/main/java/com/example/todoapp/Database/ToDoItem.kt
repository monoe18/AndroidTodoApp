package com.example.todoapp.Database

import android.icu.text.CaseMap
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
    val id: Int = 0,
    val title: String,
    val done: Boolean,
    val description: String,
    val list: Int

)
