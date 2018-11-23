package com.leo.events.transform;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import com.leo.events.MainApplication;

import java.util.List;

public class TransformServiceManager {


    public static ComponentName startService(Context context, Intent intent) {
        isBackground();
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Intent transformIntent = new Intent(context, TransformService.class);
                transformIntent.putExtra(TransformService.EXTRA_TRANSFORM_INTENT, intent);
                return context.startForegroundService(transformIntent);
            } else {
                return context.startService(intent);
            }
        }catch (Exception e){
            e.printStackTrace();
            String message = "type = 1,后台服务启动前台服务异常" + e.getMessage();
            Log.e(TransformServiceManager.class.getSimpleName(),message);
        }
        return null;
    }

    public static Intent getIntent(Context context,Intent intent){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Intent transformIntent = new Intent(context, TransformService.class);
            transformIntent.putExtra(TransformService.EXTRA_TRANSFORM_INTENT, intent);
            return transformIntent;
        }
        return intent;
    }


    /**
     * 获取当前进程是否是后台进程
     *
     * @param context
     * @return
     */
    public static boolean isBackground(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (activityManager != null) {
            List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = activityManager.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo runningAppProcess : runningAppProcesses) {
                if (runningAppProcess.pid != pid) {
                    continue;
                }
                /*
                    100UI，300服务进程，400后台进程，125前台服务
                 */
                int importance = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ? ActivityManager.RunningAppProcessInfo.IMPORTANCE_CACHED : ActivityManager.RunningAppProcessInfo.IMPORTANCE_BACKGROUND;
                Log.e(TransformServiceManager.class.getSimpleName(),"process name :" +runningAppProcess.processName +"  , process importance : " +runningAppProcess.importance);
                if (runningAppProcess.importance >= importance) {
                    return true;
                }

            }

        }
        return false;
    }

    /**
     * @return
     */
    public static boolean isBackground() {
        return isBackground(MainApplication.getApp());
    }

}
