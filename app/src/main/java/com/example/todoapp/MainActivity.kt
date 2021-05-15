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


class MainActivity : AppCompatActivity(), OnItemClickListener {
    protected lateinit var db: ToDoDatabase
    protected lateinit var adapter : ListCollectionAdapter
    protected var final_lists: ArrayList<ListTodo> = arrayListOf();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        db = ToDoDatabase.getAppDatabase(this)!!



        adapter = ListCollectionAdapter(final_lists, this, this)
        var recyclerView = findViewById<RecyclerView>(R.id.recyclerList)
        recyclerView.setHasFixedSize(true)
        var layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter

        var getterThread : GetterThread = GetterThread()
        getterThread.start()
    }

    inner class GetterThread : Thread() {
        override fun run() {
            Log.i(null, "starting db calls");

            var todo_lists = db.toDoListDao().getListInfo();

            for (list_tuple in todo_lists) {
                if(list_tuple.type == "List") {
                    var list_items = db.toDoItemDao().getItemsFromList(list_tuple.id);
                    var todo_items: ArrayList<ItemTodo> = arrayListOf();
                    for(list_item in list_items){
                        todo_items.add(ItemTodo(list_item.id, list_item.description, list_item.done))
                    }
                    final_lists.add(ListTodo(list_tuple.id, list_tuple.title, list_tuple.type, todo_items))
                    runOnUiThread(Runnable { adapter.notifyDataSetChanged() })
                    Thread.sleep(200)
                } else if (list_tuple.type == "Note") {
                    var note_item = db.noteItemDao().getNoteFromList(list_tuple.id);
                    //FIND BETTER WAY TO DISTINQUISH NOTES AND LISTS
                    var note_array : ArrayList<ItemTodo> = arrayListOf(ItemTodo(note_item?.id, note_item?.description, false ))
                    final_lists.add(ListTodo(list_tuple.id, list_tuple.title, list_tuple.type, note_array))
                    runOnUiThread(Runnable { adapter.notifyDataSetChanged() })
                }
            }

            Log.i(null, "ending db calls");
        }
    }
    
    override fun onResume() {
        adapter.notifyDataSetChanged();
        super.onResume()
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

    override fun onItemClick(listPressedID: Int?, listPressedType: String?) {
        Log.i(null, listPressedID.toString());
        if (listPressedType != null) {
            Log.i(null, listPressedType)
        }
        if(listPressedType == "List"){
            val intent = Intent(this, ListEditor::class.java).apply {}
            intent.putExtra("id", listPressedID)
            startActivity(intent)
        } else if(listPressedType == "Note"){
            val intent = Intent(this, NoteEditor::class.java).apply {}
            intent.putExtra("id", listPressedID)
            startActivity(intent)
        }
    }
}