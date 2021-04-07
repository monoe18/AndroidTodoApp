package com.example.todoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.renderscript.RenderScript
import android.widget.*
import com.example.todoapp.Database.ToDoDatabase
import com.example.todoapp.Database.ToDoItem
import com.example.todoapp.Database.ToDoList
import java.util.*

class AddNewToDoNote : AppCompatActivity() {

    protected lateinit var db: ToDoDatabase
    protected final val uuid: UUID = UUID.randomUUID()

    protected lateinit var Title: EditText
    protected lateinit var Description: EditText
    protected lateinit var RadioGroup: RadioGroup
    protected lateinit var Priority_RadioBtn: RadioButton
    protected lateinit var result: EditText
    protected lateinit var addNoteBtn: Button
    
    //protected lateinit var textString: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addnew)

        Title = findViewById<EditText>(R.id.editText)
        Description = findViewById<EditText>(R.id.editText3)
        RadioGroup = findViewById(R.id.radioGroup)
        addNoteBtn = findViewById<Button>(R.id.button)

        result = findViewById<EditText>(R.id.test)

        addNoteBtn.setOnClickListener{
            val intSelectionBtn : Int = RadioGroup!!.checkedRadioButtonId
            Priority_RadioBtn = findViewById(intSelectionBtn)

            var titleInput: String = Title.text.toString()
            var descriptionInput: String = Description.text.toString()
            var radioBtnInput: String = Priority_RadioBtn.text.toString()

            if(titleInput != null && descriptionInput != null && Priority_RadioBtn != null){
                val DoToList = ToDoList(id = 3, title = titleInput, type = "ToDoList", priority = radioBtnInput)
                val ToDoItem = ToDoItem(id = 3, done = false, description = descriptionInput, list = 4)
                db.toDoListDao().insert(DoToList)
            }
            result.setText(db.toDoListDao().toString())

        }
    }



}