package com.example.todoapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ListCollectionAdapter(private val dataSet: ArrayList<ListTodo>, private val context : AppCompatActivity) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    inner class ListViewHolder(item : View): RecyclerView.ViewHolder(item){
        val textView: TextView = item.findViewById<TextView>(R.id.TodoListTitle)
        val recyclerView: RecyclerView = item.findViewById<RecyclerView>(R.id.recyclerTodo)
    }

    inner class NoteViewHolder(item: View): RecyclerView.ViewHolder(item){
        val note_Title: TextView = item.findViewById<TextView>(R.id.noteTitle)
        val note_Description: TextView = item.findViewById<TextView>(R.id.noteDescription)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == 1){
            val view = LayoutInflater.from(parent.context).inflate(R.layout.todo_note_display_viewholder, parent, false)
            return NoteViewHolder(view);
        } else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.todo_list_display_viewholder, parent, false)
            return ListViewHolder(view);
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (dataSet[position].equals("Note")){
            return 1;
        } else {
            return 2;
        }
    }

    override fun getItemCount(): Int = dataSet.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder.itemViewType == 1){
            var holder_note = holder as NoteViewHolder
            holder_note.note_Title.text = dataSet[position].title
            holder_note.note_Description.text = dataSet[position].todoList[0].title
        } else {
            var holder_list = holder as ListViewHolder
            val adapter = ListDisplayAdapter(dataSet[position].todoList)
            var layoutManager = LinearLayoutManager(context)
            holder_list.textView.text = dataSet[position].title
            holder_list.recyclerView.setHasFixedSize(true)
            holder_list.recyclerView.layoutManager = layoutManager
            holder_list.recyclerView.adapter = adapter
        }
    }
}