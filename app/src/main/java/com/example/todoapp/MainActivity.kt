package com.example.todoapp

import android.content.Intent
import android.os.Build.ID
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.todoapp.Database.*


class MainActivity : AppCompatActivity() {
    protected lateinit var db: ToDoDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        db = ToDoDatabase.getAppDatabase(this)!!


    }

    fun newTodoNote(view: View) {
        val intent = Intent(this, NoteEditor::class.java).apply { }
        startActivity(intent)
    }
}