package com.example.todoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.Database.ToDoDatabase
import com.example.todoapp.Database.ToDoItem
import com.example.todoapp.Database.ToDoList

class ListEditor : AppCompatActivity() {

    protected lateinit var db: ToDoDatabase


    protected lateinit var title: EditText
    protected lateinit var description: EditText
    protected lateinit var buttonAdd: Button
    protected lateinit var showItems:RecyclerView
    lateinit var currentList: ToDoList
    var newID: Long = 0




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
        db = ToDoDatabase.getAppDatabase(this)!!

        currentList = ToDoList(0, "kongen", "List", "hej")

        newID =  db.toDoListDao().insert(currentList)


        Log.i(null, "ID" + newID)
    }

    fun insertItem(view: View) {
        var editTextField = findViewById<EditText>(R.id.textToAdd)
        val newItem = ItemTodo(editTextField.text.toString(), false)
        var editTitleField = findViewById<EditText>(R.id.titleToAdd)
        todoList.add(newItem)

        db.toDoItemDao().insert(ToDoItem(0, editTitleField.text.toString(), false , editTextField.text.toString(), newID.toInt() ))

        editTextField.text.clear();

        adapter.notifyDataSetChanged()
    }
}