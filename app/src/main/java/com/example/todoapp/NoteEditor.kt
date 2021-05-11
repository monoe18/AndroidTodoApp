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
    private lateinit var Title: EditText
    private lateinit var Description: EditText
    private lateinit var RadioGroup: RadioGroup
    private lateinit var Priority_RadioBtn: RadioButton
    private lateinit var addNoteBtn: Button
    private lateinit var cancelBtn: Button
    private var Deadline: TextView? = null
    private var DeadlineDateListener: OnDateSetListener? = null
    private lateinit var mainhandler: Handler
    var newID: Long = 0
    var newList: ToDoList? = ToDoList(0, "", "", "")
    var newNote: NoteItem? = NoteItem(0, "", "", 0)


    @Volatile
    var running = true

    /**
    var handlerobject: Handler = object : Handler() {
    override fun handleMessage(msg: Message) {
    super.handleMessage(msg)
    println("The background thread is complete.")
    }
    }
     */
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
        Title = findViewById<EditText>(R.id.editText)
        Description = findViewById<EditText>(R.id.editText3)
        Deadline = findViewById<TextView>(R.id.editText2)
        RadioGroup = findViewById(R.id.radioGroup)
        addNoteBtn = findViewById<Button>(R.id.button)
        cancelBtn = findViewById<Button>(R.id.button3)

        Deadline!!.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar[Calendar.YEAR]
            val month = calendar[Calendar.MONTH]
            val day = calendar[Calendar.DAY_OF_MONTH]
            val datePickerDialog = DatePickerDialog(
                this@NoteEditor,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                DeadlineDateListener,
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

        DeadlineDateListener = OnDateSetListener { datePicker, year, month, day ->
            var the_month = month
            the_month = +1
            Log.i("NoteEditor", "onDateSet: mm/dd/yyy: $month/$day/$year")
            val theDate = "$month/$day/$year"
            Deadline!!.text = theDate
        }

        cancelBtn.setOnClickListener() {
            val i = Intent(applicationContext, MainActivity::class.java)
            startActivity(i)
        }
    }

    inner class GetNoteThread(bundlearg: Bundle) : Thread() {
        var bundle: Bundle = bundlearg
        override fun run() {
            newList = db.toDoListDao().listFromID(bundle.getInt("id"))
            if (newList != null) {
                Title.setText(newList!!.title);
                newID = newList!!.id.toLong()
                newNote = db.noteItemDao().getNoteFromList(newID.toInt())
                if (newNote != null) {
                    Description.setText(newNote!!.description)
                    Deadline!!.text = newNote!!.deadline
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
                        addNoteBtn.text = "Processing..."
                    }
                    if (i == 2) {
                        addNoteBtn.text = "Add New Note"
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
        val intSelectionBtn: Int = RadioGroup!!.checkedRadioButtonId
        Priority_RadioBtn = findViewById(intSelectionBtn)

        var titleInput: String = Title.text.toString()
        var descriptionInput: String = Description.text.toString()
        var radioBtnInput: String = Priority_RadioBtn.text.toString()
        var deadlineInput: String = Deadline!!.text.toString()

        if (!titleInput.isEmpty() && !descriptionInput.isEmpty() && Priority_RadioBtn != null) {
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
        val i = Intent(applicationContext, MainActivity::class.java)
        startActivity(i)
        //}


    }


    /**
    class ExampleRunnable(var seconds: Int) : Runnable {
    override fun run() {
    val threadHandler = Handler(Looper.getMainLooper())
    for (i in 0..seconds) {
    Log.d("NoteEditor", "activateThread: $i")
    if (i == 5) {

    threadHandler?.post {
    addNoteBtn.text = "50%"
    }
    }
    if (i == 10) {
    threadHandler.post{
    createNote()
    }
    }

    try {
    Thread.sleep(1000)
    } catch (e: InterruptedException) {
    e.printStackTrace()
    }
    }
    }*/

/*
    fun activateThread(view: View) {
        val runnable = ExampleRunnable(10)
        Thread(runnable).start()
    }
*/
    fun threadStops() {
        running = false
        println("The Thread has stopped!!!")
    }
}
