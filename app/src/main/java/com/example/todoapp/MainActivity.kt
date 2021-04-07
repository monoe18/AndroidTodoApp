package com.example.todoapp

import android.content.Intent
import android.os.Build.ID
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.todoapp.Database.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity() {
    protected lateinit var db: ToDoDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        db = ToDoDatabase.getAppDatabase(this)!!



        var lists = arrayListOf<ListTodo>(
            ListTodo(1, "titel 1"),
            ListTodo(2, "titel 2"),
            ListTodo(3, "titel 3"),
            ListTodo(4, "titel 4")
        )

        val adapter = ListCollectionAdapter(lists, this)
        var recyclerView = findViewById<RecyclerView>(R.id.recyclerList)
        recyclerView.setHasFixedSize(true)
        var layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
    }

    fun newTodoNote(view: View) {
        val intent = Intent(this, NewToDoNote::class.java).apply { }
        startActivity(intent)
    }
}