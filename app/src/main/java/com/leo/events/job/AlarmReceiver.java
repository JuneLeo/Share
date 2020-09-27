package com.leo.events.job;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by spf on 2018/11/7.
 */
public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e(BackgroundTaskService.TAG,"收到了广播");
        BackgroundTaskService.startWork(context);
        PendingResult pendingResult = goAsync();
    }
}
