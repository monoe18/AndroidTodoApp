package com.example.todoapp

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
interface OnItemClickListener {
    fun onItemClick(listPressedID: Int?, listPressedType: String?)
}
class ListCollectionAdapter(
    private val dataSet: ArrayList<ListTodo>,
    private val context : AppCompatActivity,
    private val listener: OnItemClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    inner class ListViewHolder(item : View): RecyclerView.ViewHolder(item), View.OnClickListener{
        val textView: TextView = item.findViewById<TextView>(R.id.TodoListTitle)
        val recyclerView: RecyclerView = item.findViewById<RecyclerView>(R.id.recyclerTodo)
        init{
            itemView.setOnClickListener(this);
        }

        override fun onClick(v: View?) {
            var listPressedID : Int? = dataSet[adapterPosition].ID
            var listPressedType : String? = dataSet[adapterPosition].type
            if(adapterPosition!= RecyclerView.NO_POSITION) {
                listener.onItemClick(listPressedID, listPressedType)
            }
        }
    }

    inner class NoteViewHolder(item: View): RecyclerView.ViewHolder(item), View.OnClickListener{
        val note_Title: TextView = item.findViewById<TextView>(R.id.noteTitle)
        val note_Description: TextView = item.findViewById<TextView>(R.id.noteDescription)
        init{
            itemView.setOnClickListener(this);
        }

        override fun onClick(v: View?) {
            var listPressedID : Int? = dataSet[adapterPosition].ID
            var listPressedType : String? = dataSet[adapterPosition].type
            if(adapterPosition!= RecyclerView.NO_POSITION) {
                listener.onItemClick(listPressedID, listPressedType)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == 1){
            val view = LayoutInflater.from(parent.context).inflate(R.layout.todo_note_display_viewholder, parent, false)
            NoteViewHolder(view);
        } else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.todo_list_display_viewholder, parent, false)
            ListViewHolder(view);
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (dataSet[position].listType.equals("Note")){
            1;
        } else {
            2;
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