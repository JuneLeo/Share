package com.leo.events.notification;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.os.Build;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

/**
 * Created by spf on 2018/11/8.
 */
@SuppressLint("OverrideAbstract")
@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
public class NotificationMonitor extends NotificationListenerService {
    private static NotificationMonitor notificationMonitor;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
        notificationMonitor = NotificationMonitor.this;
    }

    public static final String TAG = NotificationMonitor.class.getName();

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        super.onNotificationPosted(sbn);
        Log.d(TAG, "add:" + sbn.toString());
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        super.onNotificationRemoved(sbn);
        Log.d(TAG, "del:" + sbn.toString());
    }

    @Override
    public StatusBarNotification[] getActiveNotifications() {
        StatusBarNotification[] activeNotifications = super.getActiveNotifications();
        if (activeNotifications != null && activeNotifications.length > 0) {
            for (StatusBarNotification activeNotification : activeNotifications) {
                Log.d(TAG, activeNotification.toString());
            }
        }
        return activeNotifications;
    }

    public static NotificationMonitor getNotificationMonitor() {
        return notificationMonitor;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        notificationMonitor = null;
    }
}
