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
    private lateinit var title: EditText
    private lateinit var description: EditText
    private lateinit var radioGroup: RadioGroup
    private lateinit var priorityButton: RadioButton
    private lateinit var addNoteButton: Button
    private lateinit var cancelButton: Button

    private var deadline: TextView? = null
    private var deadlineDateListener: OnDateSetListener? = null
    private lateinit var mainhandler: Handler
    var newID: Long = 0
    var newList: ToDoList? = ToDoList(0, "", "", "")
    var newNote: NoteItem? = NoteItem(0, "", "", 0)

    @Volatile
    var running = true

    private class MyHandler(activity: NoteEditor?) : Handler() {
        private val mActivity: WeakReference<NoteEditor>
        override fun handleMessage(msg: Message) {
            val activity: NoteEditor? = mActivity.get()
            if (activity != null) {
                println("The background thread is complete.")
            }
        }

        init {
            mActivity = WeakReference<NoteEditor>(activity)
        }
    }

    private val mHandler = MyHandler(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_editor)

        db = ToDoDatabase.getAppDatabase(this)!!
        title = findViewById<EditText>(R.id.editText)
        description = findViewById<EditText>(R.id.editText3)
        deadline = findViewById<TextView>(R.id.editText2)
        radioGroup = findViewById(R.id.radioGroup)
        addNoteButton = findViewById<Button>(R.id.button)
        cancelButton = findViewById<Button>(R.id.button3)


        deadline!!.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar[Calendar.YEAR]
            val month = calendar[Calendar.MONTH]
            val day = calendar[Calendar.DAY_OF_MONTH]
            val datePickerDialog = DatePickerDialog(
                this@NoteEditor,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                deadlineDateListener,
                year,
                month,
                day
            )
            datePickerDialog.window!!.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
            datePickerDialog.show()
        }



        var bundle: Bundle? = intent.extras
        if (bundle != null) {
            Log.i(null, "found Bundle")
            if (bundle.getInt("id") != null) {
                var getNoteThread :GetNoteThread = GetNoteThread(bundle)
                getNoteThread.start()
            }

        }

        deadlineDateListener = OnDateSetListener { datePicker, year, month, day ->
            var the_month = month
            the_month = +1
            Log.i("NoteEditor", "onDateSet: mm/dd/yyy: $month/$day/$year")
            val theDate = "$month/$day/$year"
            deadline!!.text = theDate
        }

        cancelButton.setOnClickListener() {
            val i = Intent(applicationContext, MainActivity::class.java)
            startActivity(i)
        }


    }

    fun deleteNoteFunction(view: View){
        var bundle: Bundle? = intent.extras
        if (bundle != null) {
            if (bundle.getInt("id") != null) {
                var deleteNoteThread : DeleteNoteThread = DeleteNoteThread(bundle)
                deleteNoteThread.start() }
        }
    }

    inner class DeleteNoteThread(bundleinfo: Bundle?) : Thread(){
        var bundle: Bundle? = bundleinfo
        override fun run() {
            newList = db.toDoListDao().listFromID(bundle?.getInt("id"))
            newID = newList!!.id.toLong()
            if(newID != null && newList != null) {
                db.toDoListDao().delete(newID.toInt())
                db.noteItemDao().delete(newID.toInt())
            }
            val i = Intent(applicationContext, MainActivity::class.java)
            startActivity(i)
        }
    }

    inner class GetNoteThread(bundlearg: Bundle) : Thread() {
        var bundle: Bundle = bundlearg
        override fun run() {
            newList = db.toDoListDao().listFromID(bundle.getInt("id"))
            if (newList != null) {
                title.setText(newList!!.title);
                newID = newList!!.id.toLong()
                newNote = db.noteItemDao().getNoteFromList(newID.toInt())
                if (newNote != null) {
                    description.setText(newNote!!.description)
                    deadline!!.text = newNote!!.deadline
                    if(newList!!.priority == "High"){
                        radioGroup!!.check(R.id.radioButton)
                    }else if(newList!!.priority == "Medium"){
                        radioGroup!!.check(R.id.radioButton3)
                    }else if(newList!!.priority == "Low") {
                        radioGroup!!.check(R.id.radioButton2)
                    }
                }
            }

        }
    }

    fun createNote(view: View) {
        if (running) {
            val runnableobject = Runnable {
                for (i in 0..2) {
                    Log.d("NoteEditor", "activateThread: $i")
                    if (i == 0) {
                        addNoteButton.text = "Processing..."
                    }
                    if (i == 2) {
                        addNoteButton.text = "Add New Note"
                        addNote()
                    }
                    try {
                        Thread.sleep(1000)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                mHandler.sendEmptyMessage(0)
            }
            val threadobject = Thread(runnableobject)
            threadobject.start()
        } else if (!running) {
            threadStops()
        }
    }

    fun addNote() {

        //addNoteBtn.setOnClickListener {
        val intSelectionBtn: Int = radioGroup!!.checkedRadioButtonId
        priorityButton = findViewById(intSelectionBtn)

        var titleInput: String = title.text.toString()
        var descriptionInput: String = description.text.toString()
        var radioBtnInput: String = priorityButton.text.toString()
        var deadlineInput: String = deadline!!.text.toString()

        if (!titleInput.isEmpty() && !descriptionInput.isEmpty() && priorityButton != null) {
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
            } else {
                val doToList =
                    ToDoList(
                        title = titleInput,
                        type = "Note",
                        priority = radioBtnInput
                    )
                newID = db.toDoListDao().insert(doToList)
                val noteItem = NoteItem(
                    description = descriptionInput,
                    deadline = deadlineInput,
                    list = newID.toInt()
                )
                db.noteItemDao().insert(noteItem)
            }

            println(db.toDoListDao().toDoList)
            println(db.noteItemDao().noteItem)

        }
        //val i = Intent(applicationContext, MainActivity::class.java)
        //startActivity(i)
        finish();


    }

    fun threadStops() {
        running = false
        println("The Thread has stopped!!!")
    }
}
