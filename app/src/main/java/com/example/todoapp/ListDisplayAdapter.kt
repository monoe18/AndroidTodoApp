package com.example.todoapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ListDisplayAdapter(private val dataSet: ArrayList<ItemTodo>) : RecyclerView.Adapter<ListDisplayAdapter.ViewHolder>() {

    inner class ViewHolder(item : View): RecyclerView.ViewHolder(item){
        val textView: TextView = item.findViewById<TextView>(R.id.listTitle)
        val checkBox : CheckBox = item.findViewById<CheckBox>(R.id.checkBox)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListDisplayAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.todo_list_viewholder, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = dataSet.size

    override fun onBindViewHolder(holder: ListDisplayAdapter.ViewHolder, position: Int) {
        holder.textView.text = dataSet[position].title;
        holder.checkBox.isChecked = dataSet[position].done
    }
}