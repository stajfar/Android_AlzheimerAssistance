package com.alzheimer.alzheimerpatient;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.Toast;


public class HelpMeWidget extends AppWidgetProvider {

	

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,int[] appWidgetIds) {
		// TODO Auto-generated method stub
		super.onUpdate(context, appWidgetManager, appWidgetIds);
		
		final int N = appWidgetIds.length;

        // Perform this loop procedure for each App Widget that belongs to this provider
        for (int i=0; i<N; i++) {
            int appWidgetId = appWidgetIds[i];

            // Create an Intent to launch ExampleActivity
            Intent intent = new Intent(context,  WidgetService.class);             
            PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, Intent.FLAG_ACTIVITY_NEW_TASK);
            

            // Get the layout for the App Widget and attach an on-click listener
            // to the button           
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.helpmewidget);
            views.setOnClickPendingIntent(R.id.btwidgethelp, pendingIntent);

            // Tell the AppWidgetManager to perform an update on the current app widget
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
	}

	

	@Override
	public void onDeleted(Context context, int[] appWidgetIds) {
		// TODO Auto-generated method stub
		super.onDeleted(context, appWidgetIds);
		Toast.makeText(context, "Widget Deleted", Toast.LENGTH_SHORT).show();
	}

}
