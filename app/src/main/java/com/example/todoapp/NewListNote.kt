package com.example.todoapp

import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class NewListNote : AppCompatActivity() {

    var todoList = arrayListOf<ItemTodo>(
            ItemTodo("Clean desk", false),
            ItemTodo("Go Shopping", true),
            ItemTodo("Cook Dinner", true)


    )

    val adapter = ListDisplayAdapter(todoList)

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_new_list_note)



        //var recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        //recyclerView.setHasFixedSize(true)
        //var layoutManager = LinearLayoutManager(this)
        //recyclerView.layoutManager = layoutManager
        //recyclerView.adapter = adapter
    }



    fun insertItem(view: View) {
      //  val newItem = ItemTodo(findViewById<EditText>(R.id.ItemName).text.toString(), true)
        //todoList.add(newItem)

        //adapter.notifyDataSetChanged()



    }


    fun removeItem(view: View) {

        //remove items in db


    }
}