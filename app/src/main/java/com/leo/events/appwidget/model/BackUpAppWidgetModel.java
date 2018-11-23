package com.leo.events.appwidget.model;

import android.content.Context;
import android.widget.RemoteViews;

import com.leo.events.R;

/**
 * Created by spf on 2018/10/31.
 */
public class BackUpAppWidgetModel extends AppWidgetModel {

    @Override
    public RemoteViews create(Context app) {
        RemoteViews remoteViews = new RemoteViews(app.getPackageName(),R.layout.app_widget_back_up);


        return super.create(app);
    }
}
