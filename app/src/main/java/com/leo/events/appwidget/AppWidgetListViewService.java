package com.leo.events.appwidget;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.leo.events.MainApplication;
import com.leo.events.R;
import com.leo.events.appwidget.model.AppWidgetModel;
import com.leo.events.appwidget.model.AppleAppWidgetModel;
import com.leo.events.appwidget.model.BackUpAppWidgetModel;
import com.leo.events.appwidget.model.FunctionWidgetModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by spf on 2018/10/28.
 */
public class AppWidgetListViewService extends RemoteViewsService {

    private static HashMap<String,RemoteViewsFactory> map = new HashMap<>();

    public static ListRemoteViewsFactory factory;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (factory == null) {
            factory = new ListRemoteViewsFactory(this.getApplicationContext(), intent);
        }
//        if (intent.getAction().contains("leo.update")) {
////            factory.setData(Arrays.asList("NI", "SI", "BU", "SI", "SHA","NI", "SI", "BU", "SI", "SHA"));
////            ComponentName componentName = new ComponentName(MainApplication.getApp(), AppWidgetItemProvider.class);
////            int[] appWidgetIds = AppWidgetManager.getInstance(MainApplication.getApp()).getAppWidgetIds(componentName);
////            AppWidgetManager.getInstance(MainApplication.getApp()).notifyAppWidgetViewDataChanged(appWidgetIds, R.id.lv_list);
////        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return factory = new ListRemoteViewsFactory(this.getApplicationContext(), intent);
    }

    private static class ListRemoteViewsFactory implements RemoteViewsFactory {
        private final Context mContext;
        private List<AppWidgetModel> mList;

        public void setData(List<AppWidgetModel> mList) {
            this.mList = mList;
        }

        public ListRemoteViewsFactory(Context context, Intent intent) {
            mContext = context;
            mList = new ArrayList<>();
//            mList.add(new FunctionWidgetModel().createDefault());
            mList.add(new FunctionWidgetModel().createTwo());
            mList.add(new FunctionWidgetModel().createThree());
            mList.add(new FunctionWidgetModel().createOne());
            mList.add(new FunctionWidgetModel().createFour());
            mList.add(new AppleAppWidgetModel("苹果"));
            mList.add(new AppleAppWidgetModel("苹果"));
            mList.add(new AppleAppWidgetModel("苹果"));
            mList.add(new AppleAppWidgetModel("苹果"));
            mList.add(new AppleAppWidgetModel("苹果"));
        }

        @Override
        public void onCreate() {

        }

        @Override
        public void onDataSetChanged() {
        }

        @Override
        public void onDestroy() {
            mList.clear();
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public RemoteViews getViewAt(int position) {
            if (position < 0 || position >= mList.size())
                return null;
            AppWidgetModel appWidgetModel = mList.get(position);
            RemoteViews remoteViews = null;
            if (appWidgetModel instanceof FunctionWidgetModel){
                remoteViews = ((FunctionWidgetModel) appWidgetModel).create(MainApplication.getApp());
            }else if (appWidgetModel instanceof AppleAppWidgetModel){
                remoteViews = new RemoteViews(MainApplication.getApp().getPackageName(),R.layout.widget_list_item);
                remoteViews.setTextViewText(R.id.tv_app_widget_item,((AppleAppWidgetModel) appWidgetModel).title);
            }else if (appWidgetModel instanceof BackUpAppWidgetModel){
//                remoteViews = ((BackUpAppWidgetModel) appWidgetModel).create(MainApplication.getApp());
            }
            return remoteViews;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 10;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }
    }



}
