package com.example.todoapp

import android.content.Intent
import android.os.Build.ID
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.todoapp.Database.*
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity() {
    protected lateinit var db: ToDoDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        db = ToDoDatabase.getAppDatabase(this)!!

        var todoList = arrayListOf<ItemTodo>(
            ItemTodo("Clean desk", false),
            ItemTodo("Go Shopping", true),
            ItemTodo("Cook Dinner", true)
        )

        val adapter = ListDisplayAdapter(todoList)
        var recyclerView = findViewById<RecyclerView>(R.id.recyclerTodo)
        recyclerView.setHasFixedSize(true)
        var layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
    }

    fun newTodoNote(view: View) {
        val intent = Intent(this, ListEditor::class.java).apply {}
        startActivity(intent)
    }
}