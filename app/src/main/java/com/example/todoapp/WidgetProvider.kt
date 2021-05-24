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
    override fun onUpdate(
        context: Context?,
        appWidgetManager: AppWidgetManager?,
        appWidgetIds: IntArray?
    ){
        super.onUpdate(context, appWidgetManager, appWidgetIds)

        var listView : RemoteViews = RemoteViews(context?.packageName, R.layout.activity_widget)
        val intent = Intent(context,WidgetListService::class.java)
        listView.setRemoteAdapter(R.id.widget_listView, intent)
        appWidgetManager?.updateAppWidget(appWidgetIds,listView)

    }

}




