package com.example.todoapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.Database.ToDoDatabase


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
        dialogSetup()
    }

    fun dialogSetup(){
        AlertDialog.Builder(this)
            .setTitle("Todo Type")
            .setMessage("Would you like to make a note or a list?")
            // Specifying a listener allows you to take an action before dismissing the dialog.
            // The dialog is automatically dismissed when a dialog button is clicked.
            .setPositiveButton("List") { dialog, which ->
                // Continue with delete operation
                val intent = Intent(this, ListEditor::class.java).apply {}
                startActivity(intent)
            } // A null listener allows the button to dismiss the dialog and take no further action.
            .setNegativeButton("Note"){ dialog, which ->
                // Continue with delete operation
                val intent = Intent(this, NoteEditor::class.java).apply {}
                startActivity(intent)
            }
            .show()
    }
}