package com.leo.events.appwidget.model;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import com.leo.events.R;
import com.leo.events.appwidget.AppWidgetConfig;
import com.leo.events.util.DimenUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by spf on 2018/10/29.
 */
public class FunctionWidgetModel extends AppWidgetModel {
    public List<AppWidgetItemModel> lists;
    public int type;

    public FunctionWidgetModel(List<AppWidgetItemModel> lists) {
        this.lists = lists;
    }

    public FunctionWidgetModel() {
    }

    public RemoteViews build(Context context){
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(),R.layout.app_widget_function_container_horizontal);
        remoteViews.removeAllViews(R.id.ll_container);
//        RemoteViews origin = new RemoteViews(context.getPackageName(),R.layout.app_widget_function_item_default);
        for (AppWidgetItemModel model : lists) {
            RemoteViews child = new RemoteViews(context.getPackageName(),R.layout.app_widget_function_item_default);

            Log.e(TAG," start"+child.getLayoutId()+","+R.id.tv_app_widget_item_default);
            remoteViews.setInt(R.id.tv_app_widget_item_default,"setId",2131296293);
            Log.e(TAG,child.getLayoutId()+","+R.id.tv_app_widget_item_default);
            remoteViews.setTextViewText(child.getLayoutId(), model.title);
            remoteViews.setInt(child.getLayoutId(), "setBackgroundColor", model.color);

            Intent intent = new Intent();
            intent.putExtra(AppWidgetConfig.KEY_APPWIDGET_URI, model.uri);
            remoteViews.setOnClickFillInIntent(child.getLayoutId(), intent);
            remoteViews.addView(R.id.ll_container,child);
            remoteViews.addView(R.id.ll_container,new RemoteViews(context.getPackageName(),R.layout.app_widget_function_item_default_divider));
        }
        return remoteViews;

    }

    @Override
    public RemoteViews create(Context context) {
        if (type == 2){
            return build(context);
        }
        int num = lists.size();
        RemoteViews remoteViews = null;
        switch (num) {
            case 1: {
                remoteViews = new RemoteViews(context.getPackageName(), R.layout.app_widget_function_item_1);
                fillData(context, remoteViews, R.id.tv_func_1, lists.get(0));
                break;
            }
            case 2: {
                remoteViews = new RemoteViews(context.getPackageName(), R.layout.app_widget_function_item_2);
                fillData(context, remoteViews, R.id.tv_func_1, R.id.ll_func_1, lists.get(0));
                fillData(context, remoteViews, R.id.tv_func_2, R.id.ll_func_2, lists.get(1));
                break;

            }
            case 3: {
                remoteViews = new RemoteViews(context.getPackageName(), R.layout.app_widget_function_item_3);
                fillData(context, remoteViews, R.id.tv_func_1, lists.get(0));
                fillData(context, remoteViews, R.id.tv_func_2, lists.get(1));
                fillData(context, remoteViews, R.id.tv_func_3, lists.get(2));
                break;
            }
            case 4: {
                remoteViews = new RemoteViews(context.getPackageName(), R.layout.app_widget_function_item_4);
                fillData(context, remoteViews, R.id.tv_func_1, lists.get(0));
                fillData(context, remoteViews, R.id.tv_func_2, lists.get(1));
                fillData(context, remoteViews, R.id.tv_func_3, lists.get(2));
                fillData(context, remoteViews, R.id.tv_func_4, lists.get(3));
                break;
            }

            default:
                remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_list_item);
        }
        return remoteViews;
    }


    private void fillData(Context context, RemoteViews remoteViews, int viewId, AppWidgetItemModel model) {
        remoteViews.setTextViewText(viewId, model.title);
        remoteViews.setInt(viewId, "setBackgroundColor", model.color);
        Intent intent = new Intent();
        intent.putExtra(AppWidgetConfig.KEY_APPWIDGET_URI, model.uri);
        remoteViews.setOnClickFillInIntent(viewId, intent);
    }

    private void fillData(Context context, RemoteViews remoteViews, int viewId, int parentId, AppWidgetItemModel model) {
        remoteViews.setTextViewText(viewId, model.title);
        remoteViews.setInt(viewId, "setBackgroundColor", model.color);
        Intent intent = new Intent();
        intent.putExtra(AppWidgetConfig.KEY_APPWIDGET_URI, model.uri);
        remoteViews.setOnClickFillInIntent(parentId, intent);
    }

    private int getWidth() {
        return DimenUtils.dp2px(320 / lists.size());
    }


    public FunctionWidgetModel createTwo() {
        lists = new ArrayList<>();
        AppWidgetItemModel junk = new AppWidgetItemModel(0x44ff0000, "垃圾", "", "ishare://cmcm.share:80/share/appwidget/config");
        lists.add(junk);
        AppWidgetItemModel junk2 = new AppWidgetItemModel(0x4400ff00, "内存", "", "");
        lists.add(junk2);
        return this;
    }

    public FunctionWidgetModel createThree() {
        lists = new ArrayList<>();
        AppWidgetItemModel junk = new AppWidgetItemModel(0x440000ff, "杀毒", "", "");
        lists.add(junk);
        AppWidgetItemModel junk2 = new AppWidgetItemModel(0x4400ffff, "通知栏管理", "", "");
        lists.add(junk2);
        AppWidgetItemModel junk3 = new AppWidgetItemModel(0x44ff00ff, "护眼模式", "", "");
        lists.add(junk3);
        return this;
    }

    public FunctionWidgetModel createOne() {
        lists = new ArrayList<>();
        AppWidgetItemModel junk = new AppWidgetItemModel(0x44ffff00, "省电", "", "");
        lists.add(junk);
        return this;
    }

    public FunctionWidgetModel createFour() {
        lists = new ArrayList<>();
        AppWidgetItemModel junk = new AppWidgetItemModel(0x44AACCDD, "推荐", "", "");
        lists.add(junk);
        AppWidgetItemModel junk2 = new AppWidgetItemModel(0x44223345, "CPU降温", "", "");
        lists.add(junk2);
        AppWidgetItemModel junk3 = new AppWidgetItemModel(0x44336688, "消息安全", "", "");
        lists.add(junk3);
        AppWidgetItemModel junk4 = new AppWidgetItemModel(0x44123456, "应用锁", "", "");
        lists.add(junk4);
        return this;
    }

//    public FunctionWidgetModel createDefault(){
//        lists = new ArrayList<>();
//        type = 2;
//        AppWidgetItemModel junk = new AppWidgetItemModel(0x440000ff, "杀毒", "", "");
//        lists.add(junk);
//        AppWidgetItemModel junk2 = new AppWidgetItemModel(0x4400ffff, "通知栏管理", "", "");
//        lists.add(junk2);
//        AppWidgetItemModel junk3 = new AppWidgetItemModel(0x44ff00ff, "护眼模式", "", "");
//        lists.add(junk3);
//        return this;
//    }

}
