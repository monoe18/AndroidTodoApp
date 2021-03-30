package com.example.todoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.TextView

class AddNewToDoNote : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addnew)

        val Title: EditText = findViewById<EditText>(R.id.editText)
        val Description: EditText = findViewById<EditText>(R.id.editText3)
        val Priority: RadioButton = findViewById(R.id.radioButton)
        val addNoteBtn: Button = findViewById<Button>(R.id.button)

        addNoteBtn.setOnClickListener{
            if(Title != null && Description != null){

            }
        }
    }



}