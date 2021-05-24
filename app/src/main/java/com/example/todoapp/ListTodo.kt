package com.example.todoapp

class ListTodo (var listUID: Int?, var listTitle: String?, var listType: String?, var todoItems : ArrayList<ItemTodo>){
    var ID = listUID;
    var title = listTitle;
    var type = listType;
    var todoList = todoItems;

        /*arrayListOf<ItemTodo>(
        ItemTodo("Clean desk", false),
        ItemTodo("Go Shopping", true),
        ItemTodo("Cook Dinner", true)
    )*/
}