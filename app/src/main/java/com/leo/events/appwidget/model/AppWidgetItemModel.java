package com.leo.events.appwidget.model;

/**
 * Created by spf on 2018/10/29.
 */
public class AppWidgetItemModel {
    public int color;
    public String title;
    public String subtitle;
    public String uri;

    public AppWidgetItemModel(int color, String title, String subtitle, String uri) {
        this.color = color;
        this.title = title;
        this.subtitle = subtitle;
        this.uri = uri;
    }
}
