package com.example.todoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ListEditor : AppCompatActivity() {


    var todoList = arrayListOf<ItemTodo>(
        ItemTodo("Clean desk", false),
        ItemTodo("Go Shopping", true),
        ItemTodo("Cook Dinner", true)
    )

    val adapter = ListDisplayAdapter(todoList)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_editor)

        var recyclerView = findViewById<RecyclerView>(R.id.editorRecyclerView)
        recyclerView.setHasFixedSize(true)
        var layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
    }

    fun insertItem(view: View) {
        var editTextField = findViewById<EditText>(R.id.textToAdd)
        val newItem = ItemTodo(editTextField.text.toString(), false)
        todoList.add(newItem)
        editTextField.text.clear();


        adapter.notifyDataSetChanged()
    }
}