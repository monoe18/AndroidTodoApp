package com.example.todoapp.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ToDoList::class,ToDoItem::class,NoteItem::class], version = 1, exportSchema = false)
abstract class ToDoDatabase : RoomDatabase() {
    abstract fun toDoListDao(): ToDoListDao
    abstract fun toDoItemDao(): ToDoItemDao
    abstract fun noteItemDao(): NoteItemDao

    companion object {
        // Singleton to prevent multiple instances from existing
        private var INSTANCE: ToDoDatabase? = null

        fun getAppDatabase(context: Context): ToDoDatabase? {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.applicationContext, ToDoDatabase::class.java, "ToDoDatabase")
                    // Allow queries on the main thread.
                    // Don't do this on a real app!
                    .allowMainThreadQueries()
                    .build()
            }
            return INSTANCE
        }
    }
}