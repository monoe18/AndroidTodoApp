package com.example.todoapp

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.todoapp.Database.NoteItem
import com.example.todoapp.Database.ToDoDatabase
import com.example.todoapp.Database.ToDoList
import java.util.*


class NoteEditor : AppCompatActivity() {

    protected lateinit var db: ToDoDatabase
    protected lateinit var Title: EditText
    protected lateinit var Description: EditText
    protected lateinit var RadioGroup: RadioGroup
    protected lateinit var Priority_RadioBtn: RadioButton
    protected lateinit var addNoteBtn: Button
    protected lateinit var cancelBtn: Button
    protected var Deadline: TextView ?= null
    protected var DeadlineDateListener: OnDateSetListener ?= null
    //protected var uniqueID: String = UUID.randomUUID().toString()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_editor)

        db = ToDoDatabase.getAppDatabase(this)!!
        Title = findViewById<EditText>(R.id.editText)
        Description = findViewById<EditText>(R.id.editText3)
        Deadline = findViewById<TextView>(R.id.editText2)
        RadioGroup = findViewById(R.id.radioGroup)
        addNoteBtn = findViewById<Button>(R.id.button)
        cancelBtn = findViewById<Button>(R.id.button3)
        var newID: Long = 0
        var newList: ToDoList? = ToDoList(0, "", "","")
        var newNote: NoteItem? = NoteItem(0, "", "",0)

        var bundle: Bundle? = intent.extras
        if (bundle != null) {
            if (bundle.getInt("id") != null) {
                 newList = db.toDoListDao().listFromID(bundle.getInt("id"))
                if (newList != null) {
                    Title.setText(newList.title);
                    newID = newList.id.toLong()
                    newNote = db.noteItemDao().getNoteFromList(newID.toInt())
                    if(newNote!= null){
                        Description.setText(newNote.description)
                        Deadline!!.text = newNote.deadline
                    }
                }
            }
        }

        Deadline!!.setOnClickListener{
            val calendar = Calendar.getInstance()
            val year = calendar[Calendar.YEAR]
            val month = calendar[Calendar.MONTH]
            val day = calendar[Calendar.DAY_OF_MONTH]
            val datePickerDialog = DatePickerDialog (this@NoteEditor, android.R.style.Theme_Holo_Light_Dialog_MinWidth, DeadlineDateListener, year, month, day)
            datePickerDialog.window!!.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
            datePickerDialog.show()
        }

        DeadlineDateListener = OnDateSetListener { datePicker, year, month, day ->
            var the_month = month
            the_month =+ 1
            Log.i("NoteEditor", "onDateSet: mm/dd/yyy: $month/$day/$year")
            val theDate = "$month/$day/$year"
            Deadline!!.text = theDate
        }

        addNoteBtn.setOnClickListener{
            val intSelectionBtn : Int = RadioGroup!!.checkedRadioButtonId
            Priority_RadioBtn = findViewById(intSelectionBtn)

            var titleInput: String = Title.text.toString()
            var descriptionInput: String = Description.text.toString()
            var radioBtnInput: String = Priority_RadioBtn.text.toString()
            var deadlineInput: String = Deadline!!.text.toString()

            if(!titleInput.isEmpty() && !descriptionInput.isEmpty() && Priority_RadioBtn != null){
                if(newID != 0.toLong()){
                    if(newList != null){
                        newList.title = titleInput
                        newList.priority = radioBtnInput
                        if(newNote!= null) {
                            newNote.deadline = deadlineInput
                            newNote.description = descriptionInput
                            db.toDoListDao().update(newList)
                            db.noteItemDao().update(newNote)
                        }
                    }
                } else {
                    val doToList = ToDoList(title = titleInput, type = "Note", priority = radioBtnInput)
                    newID = db.toDoListDao().insert(doToList)
                    val noteItem = NoteItem(description = descriptionInput, deadline = deadlineInput, list = newID.toInt())
                    db.noteItemDao().insert(noteItem)
                }

                //Log.i(db.toDoListDao().toDoList)

                println(db.toDoListDao().toDoList)
                println(db.noteItemDao().noteItem)

            }
            //result.setText(db.toDoListDao().toString() + " + " + db.toDoItemDao().toString())
            val i = Intent(applicationContext, MainActivity::class.java)
            startActivity(i)
        }

        cancelBtn.setOnClickListener(){
            val i = Intent(applicationContext, MainActivity::class.java)
            startActivity(i)
        }
    }
}