package com.example.todoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import com.example.todoapp.Database.NoteItem
import com.example.todoapp.Database.ToDoDatabase
import com.example.todoapp.Database.ToDoItem
import com.example.todoapp.Database.ToDoList

class NoteEditor : AppCompatActivity() {
    protected lateinit var db: ToDoDatabase
    //protected final val uuid: UUID = UUID.randomUUID()

    protected lateinit var Title: EditText
    protected lateinit var Description: EditText
    protected lateinit var RadioGroup: RadioGroup
    protected lateinit var Priority_RadioBtn: RadioButton
    protected lateinit var result: EditText
    protected lateinit var addNoteBtn: Button

    //protected lateinit var textString: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_editor)
        db = ToDoDatabase.getAppDatabase(this)!!

        Title = findViewById<EditText>(R.id.editText)
        Description = findViewById<EditText>(R.id.editText3)
        RadioGroup = findViewById(R.id.radioGroup)
        addNoteBtn = findViewById<Button>(R.id.button)

        //result = findViewById<EditText>(R.id.test)

        addNoteBtn.setOnClickListener{
            val intSelectionBtn : Int = RadioGroup!!.checkedRadioButtonId
            Priority_RadioBtn = findViewById(intSelectionBtn)

            var titleInput: String = Title.text.toString()
            var descriptionInput: String = Description.text.toString()
            var radioBtnInput: String = Priority_RadioBtn.text.toString()

            if(titleInput != null && descriptionInput != null && Priority_RadioBtn != null){
                val doToList = ToDoList(id = 69, title = titleInput, type = "Note", priority = radioBtnInput)
                val noteItem = NoteItem(id = 420, description = descriptionInput, deadline = "000000",list = 69)
                db.toDoListDao().insert(doToList)
                db.noteItemDao().insert(noteItem)
                //Log.i(db.toDoListDao().toDoList)
            }
            //result.setText(db.toDoListDao().toString() + " + " + db.toDoItemDao().toString())

        }
    }
}