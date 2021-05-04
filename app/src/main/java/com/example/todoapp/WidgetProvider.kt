package com.example.todoapp

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews

class WidgetProvider : AppWidgetProvider() {

    override fun onUpdate(
        context: Context?,
        appWidgetManager: AppWidgetManager?,
        appWidgetIds: IntArray?
    ) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)
        if (appWidgetIds != null) {
            for (appWidgetId in appWidgetIds) {
                val intent = Intent(context, MainActivity::class.java)
                val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)
                val views = RemoteViews(context?.getPackageName(), R.layout.activity_widget)
                views.setOnClickPendingIntent(R.id.example_widget_button, pendingIntent)
                if (appWidgetManager != null) {
                    appWidgetManager.updateAppWidget(appWidgetId, views)
                }
            }
        }


    }


}

