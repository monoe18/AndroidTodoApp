package com.example.todoapp

class ListTodo (var listUID: Int, var listTitle: String){
    var ID = listUID;
    var title = listTitle;
    var todoList = arrayListOf<ItemTodo>(
        ItemTodo("Clean desk", false),
        ItemTodo("Go Shopping", true),
        ItemTodo("Cook Dinner", true)
    )
}