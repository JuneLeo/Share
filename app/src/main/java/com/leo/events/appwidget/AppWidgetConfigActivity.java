package com.leo.events.appwidget;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.leo.events.R;

/**
 * Created by spf on 2018/10/30.
 */
public class AppWidgetConfigActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_widget_container);
    }
}
