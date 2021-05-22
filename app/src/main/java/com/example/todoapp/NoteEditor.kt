package com.example.todoapp

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.todoapp.Database.NoteItem
import com.example.todoapp.Database.ToDoDatabase
import com.example.todoapp.Database.ToDoList
import java.lang.ref.WeakReference
import java.util.*


class NoteEditor : AppCompatActivity() {

    private lateinit var db: ToDoDatabase
    private lateinit var title : EditText
    private lateinit var description : EditText
    private lateinit var radioGroup : RadioGroup
    private lateinit var priorityRadioBtn : RadioButton

    private lateinit var addNoteBtn : Button
    private lateinit var cancelBtn : Button
    private lateinit var saveBtn : Button
    private lateinit var deleteBtn: Button

    private var deadline: TextView? = null
    private var deadlineDateListener: OnDateSetListener? = null
    private var titleInput: String = title.text.toString()
    private var descriptionInput: String = description.text.toString()
    private var radioBtnInput: String = priorityRadioBtn.text.toString()
    private var deadlineInput: String = deadline!!.text.toString()

    private var newID : Long = 0
    private var newList: ToDoList? = ToDoList(0, "", "", "")
    private var newNote: NoteItem? = NoteItem(0, "", "", 0)

    @Volatile
    private var running = true

    /**
    private class MyHandler(activity: NoteEditor?) : Handler() {
        private val mActivity: WeakReference<NoteEditor> = WeakReference<NoteEditor>(activity)
        override fun handleMessage(msg: Message) {
            val activity: NoteEditor? = mActivity.get()
            if (activity != null) {
                println("The background thread is complete.")
            }
        }
    }
    private val mHandler = MyHandler(this)
    */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_editor)
        db = ToDoDatabase.getAppDatabase(this)!!
        title = findViewById(R.id.Title)
        description = findViewById(R.id.Description)
        radioGroup = findViewById(R.id.radioGroup)
        addNoteBtn = findViewById(R.id.addNote)
        cancelBtn = findViewById(R.id.Cancel)
        saveBtn = findViewById(R.id.Save)
        deleteBtn = findViewById(R.id.Delete)

        deadline!!.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar[Calendar.YEAR]
            val month = calendar[Calendar.MONTH]
            val day = calendar[Calendar.DAY_OF_MONTH]
            val datePickerDialog = DatePickerDialog(this@NoteEditor, android.R.style.Theme_Holo_Light_Dialog_MinWidth, deadlineDateListener, year, month, day)
            datePickerDialog.window!!.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
            datePickerDialog.show()
        }
        deadlineDateListener = OnDateSetListener { datePicker, year, month, day ->
            var theMonth = month
            theMonth = +1
            Log.i("NoteEditor", "onDateSet: mm/dd/yyy: $month/$day/$year")
            val theDate = "$month/$day/$year"
            deadline!!.text = theDate
        }


        var bundle: Bundle? = intent.extras
        if (bundle != null) {
            Log.i(null, "found Bundle")
            if (bundle.getInt("id") != null) {

                var getNoteThread :GetNoteThread = GetNoteThread(bundle)
                getNoteThread.start()
            }
        }

    }

    inner class GetNoteThread(bundlearg: Bundle) : Thread() {
        var bundle: Bundle = bundlearg
        override fun run() {
            addNoteBtn.visibility = View.INVISIBLE
            cancelBtn.visibility = View.INVISIBLE
            saveBtn.visibility = View.VISIBLE
            deleteBtn.visibility = View.VISIBLE
            newList = db.toDoListDao().listFromID(bundle.getInt("id"))
            if (newList != null) {
                title.setText(newList!!.title);
                newID = newList!!.id.toLong()
                newNote = db.noteItemDao().getNoteFromList(newID.toInt())
                if (newNote != null) {
                    description.setText(newNote!!.description)
                    deadline!!.text = newNote!!.deadline
                    if(priorityRadioBtn.isChecked) {
                        radioGroup.checkedRadioButtonId
                    }
                }
            }
        }
    }

    fun createnoteFunction(view: View) {
        if (running) {
            val runnableObject = Runnable {
                for (i in 0..2) {
                    Log.d("NoteEditor", "activateThread: $i")
                    if (i == 0) {
                        addNoteBtn.text = "Processing..."
                        insertFunction()
                    }
                    if (i == 2) {
                        addNoteBtn.text = "Add New Note"
                        insertFunction()
                    }
                    try {
                        Thread.sleep(1000)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                //mHandler.sendEmptyMessage(0)
            }
            val threadObject = Thread(runnableObject)
            threadObject.start()
        } else if (!running) {
            stopThreadFunction()
        }
    }

    private fun stopThreadFunction() {
        running = false
        println("The Thread has stopped!!!")
    }

    private fun insertFunction(){
        priorityRadioBtn = findViewById(radioGroup!!.checkedRadioButtonId)

        if(titleInput.isNotEmpty() && descriptionInput.isNotEmpty() && radioBtnInput.isNotEmpty() && deadlineInput.isNotEmpty()){

            val doToList = ToDoList(title = titleInput, type = "Note", priority = radioBtnInput)
            newID = db.toDoListDao().insert(doToList)
            val noteItem = NoteItem(description = descriptionInput, deadline = deadlineInput, list = newID.toInt())
            db.noteItemDao().insert(noteItem)
            println(doToList)
            println(noteItem)

        }else{
            println("Every fields aren't filled out. Try Again!!")
        }
    }



    fun cancelFunction(view: View){
    val i = Intent(applicationContext, MainActivity::class.java)
    startActivity(i)
    }


    fun updateFunction(view: View){
        var bundle : Bundle? = intent.extras
        newList = db.toDoListDao().listFromID(bundle!!.getInt("id"))
        newID = newList!!.id.toLong()

        if (newID != 0.toLong()) {
            if (newList != null) {
                newList!!.title = titleInput
                newList!!.priority = radioBtnInput
                if (newNote != null) {
                    newNote!!.deadline = deadlineInput
                    newNote!!.description = descriptionInput
                    db.toDoListDao().update(newList)
                    db.noteItemDao().update(newNote)
                }
            }
        }
    }

    fun deleteFunction(view: View){
        var checkDeletion : Boolean = false
        var bundle : Bundle? = intent.extras
        newList = db.toDoListDao().listFromID(bundle!!.getInt("id"))
        newID = newList!!.id.toLong()

        if(newList != null && newID != null){
            db.toDoListDao().delete(newID.toInt())
            db.noteItemDao().delete(newID.toInt())
            checkDeletion = true
            if(checkDeletion){
                println("Deletion of data has been completed.")
            }
        }
        val i = Intent(applicationContext, MainActivity::class.java)
        startActivity(i)
    }

}
