package com.leo.events;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.leo.events.accessibility.OpenAccessibilitySettingHelper;
import com.leo.events.accessibility.utils.AccessibilityUtil;
import com.leo.events.job.AlarmReceiver;
import com.leo.events.job.BackgroundTaskService;
import com.leo.events.model.BaseModel;
import com.leo.events.model.CustomModel;
import com.leo.events.model.GoodsList;
import com.leo.events.model.TaskModel;
import com.leo.events.net.AppEngine;
import com.leo.events.notification.NotificationMonitor;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


/**
 * Created by spf on 2018/11/5.
 */
public class ProductActivity extends AppCompatActivity {

    public boolean isFirst = true;
    private String TAG = ProductActivity.class.getName();

    private List<BaseModel> tasks = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_activity);
        Log.e(TAG, "onCreate");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        // 主标题
        toolbar.setTitle("我的产品");

        //设置toolbar
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(ProductActivity.this));
        final ProductAdapter adapter = new ProductAdapter(ProductActivity.this);
        recyclerView.setAdapter(adapter);
//        AppEngine.getInstance().getAppService().getSimpleInfos()
//                .map(new Func1<JsonObject, GoodsList>() {
//                    @Override
//                    public GoodsList call(JsonObject jsonObject) {
//                        String s = jsonObject.toString();
//                        Gson gson = new Gson();
//                        return gson.fromJson(s, GoodsList.class);
//                    }
//                })
//                .subscribeOn(Schedulers.io())
//                .unsubscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<GoodsList>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.d("listinfo", e.getMessage());
//                    }
//
//                    @Override
//                    public void onNext(GoodsList s) {
//
//                        list.addAll(s.entity.entityData);
//                        adapter.setData(list);
//                    }
//                });
        initData(adapter);

    }

    private void initData(ProductAdapter adapter) {

//        tasks.add(new CustomModel());
        tasks.add(new TaskModel("mainactivity").setTask(new Runnable() {
            @Override
            public void run() {
//                Intent intent = new Intent(ProductActivity.this, PayWindowActivity.class);
//                startActivity(intent);

            }
        }));
        tasks.add(new TaskModel("job").setTask(new Runnable() {
            @Override
            public void run() {
                BackgroundTaskService.startWork(ProductActivity.this);
            }
        }));
        tasks.add(new TaskModel("cancel").setTask(new Runnable() {
            @Override
            public void run() {
                BackgroundTaskService.cancel(ProductActivity.this);
            }
        }));
        tasks.add(new TaskModel("startService").setTask(new Runnable() {
            @Override
            public void run() {
                if (Build.VERSION.SDK_INT >= 26) {
                    Intent intent = new Intent(ProductActivity.this, BackgroundTaskService.class);
                    startForegroundService(intent);
                }
            }
        }));

        tasks.add(new TaskModel("alarm").setTask(new Runnable() {
            @Override
            public void run() {
                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                Intent intent = new Intent(MainApplication.getApp(), AlarmReceiver.class);
//                Intent intent = new Intent("com.leo.events.BACKGROUND");
                PendingIntent pendingIntent = PendingIntent.getBroadcast(MainApplication.getApp(), 786, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                alarmManager.set(AlarmManager.RTC, System.currentTimeMillis() + 5 * 1000, pendingIntent);
                Log.e(BackgroundTaskService.TAG, "alarm启动");
            }
        }));
        tasks.add(new TaskModel("asynctask").setTask(new Runnable() {
            @Override
            public void run() {
                new AsyncTask<String, Integer, String>() {
                    @Override
                    protected String doInBackground(String... strings) {
                        String s = null;
                        s.substring(1, 3);
                        return s;
                    }

                    @Override
                    protected void onPostExecute(String s) {
                        super.onPostExecute(s);
                    }
                }.execute();
            }
        }));

        tasks.add(new TaskModel("cacheDir").setTask(new Runnable() {
            @Override
            public void run() {
                File cacheDir = getCacheDir();
                File file = new File(cacheDir, "leo");
                Log.d(TAG, file.isDirectory() + "");
            }
        }));

        tasks.add(new TaskModel("开启通知栏权限").setTask(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS);
                startActivity(intent);
            }
        }));

        tasks.add(new TaskModel("打印获取所有通知").setTask(new Runnable() {
            @Override
            public void run() {
                NotificationMonitor notificationMonitor = NotificationMonitor.getNotificationMonitor();
                if (notificationMonitor != null) {
                    notificationMonitor.getActiveNotifications();
                }
            }
        }));

        tasks.add(new TaskModel("location").setTask(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);

            }
        }));
        tasks.add(new TaskModel("push消息 控制台查看").setTask(new Runnable() {
            @Override
            public void run() {
//                PushClient pushClient = new PushClient();
//                pushClient.connect(6180,ProductActivity.this);
            }
        }));

        tasks.add(new TaskModel("Card").setTask(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(ProductActivity.this, CardActivity.class);
                startActivity(intent);
            }
        }));
        tasks.add(new TaskModel("聊天室").setTask(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(ProductActivity.this, ChatActivity.class);
                startActivity(intent);
            }
        }));


        tasks.add(new TaskModel(getName()).setTask(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(ProductActivity.this, getInteger() + "", Toast.LENGTH_SHORT).show();
            }
        }));


        adapter.setData(tasks);
    }




    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG, "onResume");
        if (!isFirst) {
            return;
        }
        isFirst = false;
        if (!AccessibilityUtil.isAccessibilitySettingsOn(ProductActivity.this)) {
            AlertDialog dialog = new AlertDialog.Builder(ProductActivity.this).setMessage("开启辅助模式，一键分享!").setPositiveButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).setNegativeButton("去开启", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    OpenAccessibilitySettingHelper.jumpToSettingPage(ProductActivity.this);
                    dialog.dismiss();

                }
            }).create();
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();


        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(TAG, "onStart");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e(TAG, "onRestart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(TAG, "onStop");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.e(TAG, "onSaveInstanceState");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.e(TAG, "onRestoreInstanceState");
    }

    public String getName() {
        return "原始名字";
    }


    public Integer getInteger() {
        return 1;
    }


}
