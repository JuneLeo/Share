package com.leo.events.transform;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * 过渡service，兼容8.0
 */
public class TransformService extends Service {

    public static final String EXTRA_TRANSFORM_INTENT = "extra_transform_intent";

    @Override
    public void onCreate() {
        super.onCreate();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForeground(56546, new Notification.Builder(TransformService.this, "default_channelId").build());
        }
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TransformService.class.getSimpleName(),"创建");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Intent intentExtra = intent.getParcelableExtra(EXTRA_TRANSFORM_INTENT);
            if (intentExtra != null) {
//                Log.d(TransformService.class.getSimpleName(), "android O input intent : " + intentExtra.toString());
                try {
                    startService(intentExtra);
                } catch (Exception e) {
                    // moto 奔溃，目前没法处理
                    String message = "type = 2,前台服务启动后台服务异常" + e.getMessage();
                    Log.e(TransformServiceManager.class.getSimpleName(),message);
                }
            }
//            stopForeground(true); //不可以设置，否则5s后anr，选择直接stopself（）
        }

        stopSelf();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TransformService.class.getSimpleName(),"xiaohui");
    }
}
