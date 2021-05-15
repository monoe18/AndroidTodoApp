package com.example.todoapp

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import android.widget.SimpleCursorAdapter

class WidgetListService: RemoteViewsService() {
    override fun onGetViewFactory(intent: Intent?): RemoteViewsFactory {
        return WidgetListServiceFactory(applicationContext, intent)
    }

    inner class WidgetListServiceFactory(context: Context, intent: Intent?) : RemoteViewsFactory {
        var context : Context = context;
        var cursor : Cursor? = null;
        var uri : Uri = Uri.parse("content://com.example.todoapp.Provider.ContentProvider/toDoList")
        private val mProjection: Array<String> = arrayOf(
            "id", "title", "type", "priority")

        private var selectionClause: String? = null

        private var selectionArgs = null

        val TodoListTitle = intArrayOf(R.id.textView3)
        var cursorAdapter = SimpleCursorAdapter(
            context,                                        // The application's Context object
            R.layout.activity_widget_list_holder,           // A layout in XML for one row in the ListView
            cursor,                                         // The result from the query
            mProjection,                                    // A string array of column names in the cursor
            TodoListTitle,                                  // An integer array of view IDs in the row layout
            0                                         // Flags (usually none are needed)
        )

        fun initCursor() {
            cursor = context?.contentResolver?.query(
                uri,
                mProjection,
                selectionClause,
                selectionArgs,
                null)
        }



        override fun onCreate() {
            TODO("Not yet implemented")
        }

        override fun getLoadingView(): RemoteViews? {
            return null;
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun onDataSetChanged() {
            initCursor()
        }

        override fun hasStableIds(): Boolean {
            return true
        }

        override fun getViewAt(position: Int): RemoteViews {
            var rv : RemoteViews = RemoteViews(context.packageName, R.layout.activity_widget_list_holder)
            cursor?.moveToPosition(position)
            rv.setTextViewText(R.id.textView3, cursor?.getString(1))
            return rv;
        }

        override fun getCount(): Int {
            TODO("Not yet implemented")
        }

        override fun getViewTypeCount(): Int {
            return 1;
        }

        override fun onDestroy() {
            TODO("Not yet implemented")
        }

    }

}