package com.example.todoapp.Database

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import javax.security.auth.DestroyFailedException

@Entity(tableName = "noteItem",
    foreignKeys = arrayOf(
        ForeignKey(
            entity = ToDoList::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("list"),
            onDelete = ForeignKey.CASCADE
        )
    )
)

data class NoteItem(
    @PrimaryKey (autoGenerate = true)
    val id: Int = 0,
    var description: String,
    var deadline: String,
    var list: Int
)
