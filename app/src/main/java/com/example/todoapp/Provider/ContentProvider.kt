package com.example.todoapp.Provider
import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.example.todoapp.Database.NoteItem
import com.example.todoapp.Database.ToDoDatabase
import com.example.todoapp.Database.ToDoItem
import com.example.todoapp.Database.ToDoList

class ContentProvider : ContentProvider(){
    private val ITEMS = 1
    private val ITEMS_ID = 2
    private val NOTES = 3
    private val NOTES_ID = 4
    private val LISTS = 5
    private val LISTS_ID = 6

    private var db: ToDoDatabase? = null;

    private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)

    companion object {
        val AUTHORITY = "com.example.todoapp.Provider.ContentProvider"
        private val ITEM_TABLE = "toDoItem"
        private val NOTE_TABLE = "noteItem"
        private val LIST_TABLE = "toDoList"
        val CONTENT_URI_ITEM : Uri = Uri.parse("content://" + AUTHORITY + "/" +
                ITEM_TABLE)
        val CONTENT_URI_NOTE : Uri = Uri.parse("content://" + AUTHORITY + "/" +
                NOTE_TABLE)
        val CONTENT_URI_LIST : Uri = Uri.parse("content://" + AUTHORITY + "/" +
                LIST_TABLE)

    }

    init {
        uriMatcher.addURI(AUTHORITY, ITEM_TABLE, ITEMS)
        uriMatcher.addURI(AUTHORITY, ITEM_TABLE + "/#", ITEMS_ID)
        uriMatcher.addURI(AUTHORITY, NOTE_TABLE, NOTES)
        uriMatcher.addURI(AUTHORITY, NOTE_TABLE + "/#", NOTES_ID)
        uriMatcher.addURI(AUTHORITY, LIST_TABLE, LISTS)
        uriMatcher.addURI(AUTHORITY, LIST_TABLE + "/#", LISTS_ID)
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        var type = uriMatcher.match(uri)
        if (type == ITEMS){
            var itemToInsert = ToDoItem(0,  values!!.getAsBoolean("done"), values.getAsString("description"), values.getAsInteger("list"))
            var row = db!!.toDoItemDao().insert(itemToInsert)
            return ContentUris.withAppendedId(uri, row);
        } else if (type == ITEMS_ID){
            var itemToInsert = ToDoItem(0,  values!!.getAsBoolean("done"), values.getAsString("description"), values.getAsInteger("list"))
            var row = db!!.toDoItemDao().insert(itemToInsert)
            return ContentUris.withAppendedId(uri, row);
        } else if (type == NOTES){
            var noteToInsert = NoteItem(0,  values!!.getAsString("description"),values.getAsString("deadline"),values.getAsInteger("list"))
            var row = db!!.noteItemDao().insert(noteToInsert)
            return ContentUris.withAppendedId(uri, row);
        } else if (type == NOTES_ID){
            var noteToInsert = NoteItem(0,  values!!.getAsString("description"),values.getAsString("deadline"),values.getAsInteger("list"))
            var row = db!!.noteItemDao().insert(noteToInsert)
            return ContentUris.withAppendedId(uri, row);
        } else if (type == LISTS){
            var listToInsert = ToDoList(0,  values!!.getAsString("title"), values.getAsString("type"),values.getAsString("priority"))
            var row = db!!.toDoListDao().insert(listToInsert)
            return ContentUris.withAppendedId(uri, row);
        } else if (type == LISTS_ID){
            var listToInsert = ToDoList(0,  values!!.getAsString("title"), values.getAsString("type"),values.getAsString("priority"))
            var row = db!!.toDoListDao().insert(listToInsert)
            return ContentUris.withAppendedId(uri, row);
        }
        return uri;
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        var type = uriMatcher.match(uri)
        if (type == ITEMS){

        } else if (type == ITEMS_ID){

        } else if (type == NOTES){

        } else if (type == NOTES_ID){

        } else if (type == LISTS){

        } else if (type == LISTS_ID){

        }
    }

    override fun onCreate(): Boolean {
        db = ToDoDatabase.getAppDatabase(this!!.context!!)
        return false;
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        TODO("Not yet implemented")
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        TODO("Not yet implemented")
    }

    override fun getType(uri: Uri): String? {
        TODO("Not yet implemented")
    }

}