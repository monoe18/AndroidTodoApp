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
    var todo_items: ArrayList<ItemTodo> = arrayListOf();
    val adapter = ListDisplayAdapter(todo_items)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_editor)
        db = ToDoDatabase.getAppDatabase(this)!!
        var bundle: Bundle? = intent.extras
        if (bundle != null) {
            if (bundle.getInt("id") != null) {
                var newList: ToDoList? = db.toDoListDao().listFromID(bundle.getInt("id"))
                if (newList != null) {
                    findViewById<EditText>(R.id.titleToAdd).setText(newList.title)
                    newID = newList.id.toLong()
                    var list_items = db.toDoItemDao().getItemsFromList(newID.toInt());

                    for(list_item in list_items){
                        todo_items.add(ItemTodo(list_item.id, list_item.description, list_item.done))
                    }

                }
            }
        } else {
            currentList = ToDoList(0, "title", "List", "hej")
            newID =  db.toDoListDao().insert(currentList)
        }

        var recyclerView = findViewById<RecyclerView>(R.id.editorRecyclerView)
        recyclerView.setHasFixedSize(true)
        var layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
        adapter.notifyDataSetChanged()


        Log.i(null, "ID" + newID)
    }

    fun insertItem(view: View) {
        var editTextField = findViewById<EditText>(R.id.textToAdd)
        val newItem = ItemTodo(0,editTextField.text.toString(), false)
        var editTitleField = findViewById<EditText>(R.id.titleToAdd)
        todo_items.add(newItem)

        db.toDoItemDao().insert(ToDoItem(0, false, editTextField.text.toString(), newID.toInt() ))
        var newTitleList: ToDoList? = db.toDoListDao().listFromID(newID.toInt())
        if (newTitleList != null) {
            newTitleList.title = editTitleField.text.toString()
            db.toDoListDao().update(newTitleList)
        }
        editTextField.text.clear();

        adapter.notifyDataSetChanged()
    }
}