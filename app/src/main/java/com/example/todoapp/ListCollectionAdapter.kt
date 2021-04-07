package com.example.todoapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ListCollectionAdapter(private val dataSet: ArrayList<ListTodo>, private val context : AppCompatActivity) : RecyclerView.Adapter<ListCollectionAdapter.ViewHolder>() {


    inner class ViewHolder(item : View): RecyclerView.ViewHolder(item){
        val textView: TextView = item.findViewById<TextView>(R.id.TodoListTitle)
        val recyclerView: RecyclerView = item.findViewById<RecyclerView>(R.id.recyclerTodo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListCollectionAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.todo_list_display_viewholder, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = dataSet.size

    override fun onBindViewHolder(holder: ListCollectionAdapter.ViewHolder, position: Int) {
        holder.textView.text = dataSet[position].title;

        val adapter = ListDisplayAdapter(dataSet[position].todoList)
        holder.recyclerView.setHasFixedSize(true)
        var layoutManager = LinearLayoutManager(context)
        holder.recyclerView.layoutManager = layoutManager
        holder.recyclerView.adapter = adapter
    }
}