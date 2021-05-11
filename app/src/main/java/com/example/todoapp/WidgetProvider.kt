package com.example.todoapp

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.UserDictionary
import android.widget.ListView
import android.widget.RemoteViews
import android.widget.SimpleCursorAdapter
import androidx.recyclerview.widget.RecyclerView

class WidgetProvider : AppWidgetProvider() {

    private val mProjection: Array<String> = arrayOf(
        "id", "title", "type", "priority")

    private var selectionClause: String? = null

    private var selectionArgs = null




    override fun onUpdate(
        context: Context?,
        appWidgetManager: AppWidgetManager?,
        appWidgetIds: IntArray?
    ) {



        super.onUpdate(context, appWidgetManager, appWidgetIds)

        var uri : Uri = Uri.parse("content://com.example.todoapp.Provider.ContentProvider/toDoList")

        var cursor = context?.contentResolver?.query(
            uri,
            mProjection,
            selectionClause,
            selectionArgs,
           null)

        val TodoListTitle = intArrayOf(R.id.TodoListTitle)


        var cursorAdapter = SimpleCursorAdapter(
           context,             // The application's Context object
            R.layout.activity_widget_list_holder,           // A layout in XML for one row in the ListView
            cursor,                        // The result from the query
            mProjection,               // A string array of column names in the cursor
            TodoListTitle,                 // An integer array of view IDs in the row layout
            0                               // Flags (usually none are needed)
        )

        var listView : RemoteViews = RemoteViews(context?.packageName, R.layout.activity_widget)
        val intent = Intent(this,WidgetProvider::class.java)
        listView.setRemoteAdapter(R.id.widget_listView, cursorAdapter)



        //   if (appWidgetIds != null) {
     //       for (appWidgetId in appWidgetIds) {
     //           val intent = Intent(context, MainActivity::class.java)
     //           val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)
     //           val views = RemoteViews(context?.getPackageName(), R.layout.activity_widget)
     //           if (appWidgetManager != null) {
     //               appWidgetManager.updateAppWidget(appWidgetId, views)
                }

}




