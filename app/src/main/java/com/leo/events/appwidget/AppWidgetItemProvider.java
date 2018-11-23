package com.leo.events.appwidget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;

import com.leo.events.R;

import static android.app.PendingIntent.FLAG_UPDATE_CURRENT;

/**
 * Created by spf on 2018/10/27.
 */
public class AppWidgetItemProvider extends AppWidgetProvider {
    private static final String TAG = AppWidgetItemProvider.class.getSimpleName();


    //    private Set<Integer> appWidgetIds = new TreeSet<>();
    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        Log.e(TAG, "onReceive: " + intent.getAction());

        String action = intent.getAction();
        if (action.equals("com.example.action.CLICK")) {
            Intent intent1 = new Intent(context, AppWidgetListViewService.class);
            intent1.setAction("leo.update");
            context.startService(intent1);
        } else if (action.equals(AppWidgetConfig.ACTION_APPWIDGET_LIST_CLICK)) {
            Intent start = new Intent();
            String uri = intent.getStringExtra(AppWidgetConfig.KEY_APPWIDGET_URI);
            start.setData(Uri.parse(uri));
            context.startActivity(start);
        }

    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
        Log.e(TAG, "onEnabled: ");
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        Log.e(TAG, "onUpdate: ");
//        for (int appWidgetId : appWidgetIds) {
//            this.appWidgetIds.add(appWidgetId);
//        }
        onWidgetUpdate(context, appWidgetManager, appWidgetIds);
    }

    private void onWidgetUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.app_widget_container);
        //listview
        Intent intent = new Intent(context, AppWidgetListViewService.class);
        remoteViews.setRemoteAdapter(R.id.lv_list, intent);
        //listView itemclick
        Intent clickIntent = new Intent(context, AppWidgetItemProvider.class);
        clickIntent.setAction(AppWidgetConfig.ACTION_APPWIDGET_LIST_CLICK);
        PendingIntent pendingIntentTemplate = PendingIntent.getBroadcast(
                context, 0, clickIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setPendingIntentTemplate(R.id.lv_list, pendingIntentTemplate);
        // add btn
        Intent intentAdd = new Intent(context, AppWidgetConfigActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intentAdd, FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.iv_add, pendingIntent);
        appWidgetManager.updateAppWidget(appWidgetIds, remoteViews);
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
        Log.e(TAG, "onDeleted: ");
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
        Log.e(TAG, "onDisabled: ");
    }

    @Override
    public void onRestored(Context context, int[] oldWidgetIds, int[] newWidgetIds) {
        super.onRestored(context, oldWidgetIds, newWidgetIds);
        Log.e(TAG, "onRestored: ");
    }

    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);
        Log.e(TAG, "onAppWidgetOptionsChanged: ");
    }
}
