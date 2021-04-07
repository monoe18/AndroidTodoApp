package com.example.todoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
        val intent = Intent(this, NewListNote::class.java).apply {}
        startActivity(intent)
    }
}