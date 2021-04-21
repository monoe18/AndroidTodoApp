package com.example.todoapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.todoapp.Database.ToDoDatabase
import com.example.todoapp.Database.ToDoItem
import com.example.todoapp.Database.ToDoList


class MainActivity : AppCompatActivity() {
    protected lateinit var db: ToDoDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        db = ToDoDatabase.getAppDatabase(this)!!

        Log.i(null, "starting db calls");

        var todo_lists = db.toDoListDao().getListInfo();
        var final_lists: ArrayList<ListTodo> = arrayListOf();
        for (list_tuple in todo_lists) {
            if(list_tuple.type == "List") {
                var list_items = db.toDoItemDao().getItemsFromList(list_tuple.id);
                var todo_items: ArrayList<ItemTodo> = arrayListOf();
                for(list_item in list_items){
                    todo_items.add(ItemTodo(list_item.id, list_item.description, list_item.done))
                }
                final_lists.add(ListTodo(list_tuple.id, list_tuple.title, list_tuple.type, todo_items))
            } else if (list_tuple.type == "Note") {
                var note_item = db.noteItemDao().getNoteFromList(list_tuple.id);
                //FIND BETTER WAY TO DISTINQUISH NOTES AND LISTS
                var note_array : ArrayList<ItemTodo> = arrayListOf(ItemTodo(note_item?.id, note_item?.description, false ))
                final_lists.add(ListTodo(list_tuple.id, list_tuple.title, list_tuple.type, note_array))
            }
        }

        Log.i(null, "ending db calls");

        if(final_lists.isEmpty()){
            Log.i(null,"final list empty");
        }

        for (list_to_print in  final_lists){
            Log.i(null,"list: " + list_to_print.listTitle +", "+ list_to_print.listType +", "+ list_to_print.ID);
            for(item_ in list_to_print.todoItems){
                Log.i(null,"item: " + item_.title +", "+ item_.done +", "+ item_.id);
            }
        }
        val adapter = ListCollectionAdapter(final_lists, this)
        var recyclerView = findViewById<RecyclerView>(R.id.recyclerList)
        recyclerView.setHasFixedSize(true)
        var layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
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