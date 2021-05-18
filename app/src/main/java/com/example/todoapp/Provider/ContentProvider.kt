package com.example.todoapp.Provider
import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.example.todoapp.Database.*

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
            var cursor : Cursor? = db?.toDoItemDao()?.getItemsFromListCursor(selectionArgs?.get(0)?.toInt())
            return cursor;
        } else if (type == ITEMS_ID){
            var cursor : Cursor? = db?.toDoItemDao()?.getItemsFromListCursor(selectionArgs?.get(0)?.toInt())
            return cursor;
        } else if (type == NOTES){
            var cursor : Cursor? = db?.noteItemDao()?.getNotesFromListCursor(selectionArgs?.get(0)?.toInt())
            return cursor;
        } else if (type == NOTES_ID){
            var cursor : Cursor? = db?.noteItemDao()?.getNotesFromListCursor(selectionArgs?.get(0)?.toInt())
            return cursor;
        } else if (type == LISTS){
            var cursor : Cursor? = db?.toDoListDao()?.getListsCursor()
            return cursor;
        } else if (type == LISTS_ID){
            var cursor : Cursor? = db?.toDoListDao()?.getListsCursor()
            return cursor;
        } else {
            throw IllegalArgumentException("Unknown URI: $uri")
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
        TODO("Not Neccessary in widget use and shouldn't be allowed to be used")
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        TODO("Not Neccessary in widget use and shouldn't be allowed to be used")
        val type = uriMatcher.match(uri)

        if(type == ITEMS){
            var note : Int = db!!.toDoItemDao()!!.delete(selectionArgs?.get(0)?.toInt())
            return note
        } else if(type == ITEMS_ID){
            var note : Int = db!!.toDoItemDao()!!.delete(selectionArgs?.get(0)?.toInt())
            return note
        } else if(type == NOTES){
            var note : Int = db!!.noteItemDao()!!.delete(selectionArgs?.get(0)?.toInt())
            return note
        } else if (type == NOTES_ID){
            var note : Int = db!!.noteItemDao()!!.delete(selectionArgs?.get(0)?.toInt())
            return note
        } else if (type == LISTS){
            var note : Int = db!!.toDoListDao()!!.delete(selectionArgs?.get(0)?.toInt())
            return note
        } else if (type == LISTS_ID){
            var note : Int = db!!.toDoListDao()!!.delete(selectionArgs?.get(0)?.toInt())
            return note
        } else {
            throw IllegalArgumentException("Unknown URI: $uri")
        }
    }

    override fun getType(uri: Uri): String? {
        TODO("Not Neccessary in widget use and shouldn't be allowed to be used")
    }

}