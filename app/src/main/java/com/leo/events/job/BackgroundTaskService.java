package com.leo.events.job;

import android.annotation.TargetApi;
import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.PersistableBundle;
import android.util.Log;


/**
 * Created by spf on 2018/11/7.
 */
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class BackgroundTaskService extends JobService {

    public static final String TAG = BackgroundTaskService.class.getName();

    public static void startWork(Context context) {
        PersistableBundle persistableBundle = new PersistableBundle();
        persistableBundle.putString("task", "我是一个任务，我叫宋鹏飞");
        JobScheduler jobScheduler = (JobScheduler) context.getSystemService(JOB_SCHEDULER_SERVICE);


        JobInfo jobInfo = new JobInfo.Builder(5556, new ComponentName(context, BackgroundTaskService.class))
                .setExtras(persistableBundle)
//                .setOverrideDeadline(60 * 1000)
//                .setBackoffCriteria(2 * 1000, JobInfo.BACKOFF_POLICY_LINEAR)
                .setMinimumLatency(5 * 1000)
                .setOverrideDeadline(6 * 1000)
//                .setPeriodic(1000)
                .build();
        int schedule = jobScheduler.schedule(jobInfo);
        Log.e(TAG, "schedult:" + schedule);

    }

    public static void cancel(Context context) {
        JobScheduler jobScheduler = (JobScheduler) context.getSystemService(JOB_SCHEDULER_SERVICE);
        jobScheduler.cancel(5556);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG, "onCreate");
    }

    @Override
    public void onStart(Intent intent, int startId) {
        Log.e(TAG, "onStart");
        super.onStart(intent, startId);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent,flags,startId);
//        Log.e(TAG, "onStartCommand");
//        if (Build.VERSION.SDK_INT>=26) {
//            Notification aaa = new Notification.Builder(BackgroundTaskService.this, "aaa")
//                    .setContentText("aaaaaa")
//                    .setContentTitle("bbbbb")
//                    .build();
//            startForeground(1, aaa);
//        }
//        return START_STICKY;
    }

    @Override
    public boolean onStartJob(final JobParameters params) {
        Log.e(TAG, "onStartJob");
        PersistableBundle extras = params.getExtras();
        if (extras.containsKey("task")) {
            Log.e(TAG, "task:" + extras.getString("task"));
        }


        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        Log.e(TAG, "模拟5秒耗时工作");
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
//                        jobFinished(params, true);
                        Log.e(TAG, "模拟5秒耗时工作 - 完成");
                    }
                }
        ).start();
        startWork(BackgroundTaskService.this);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.e(TAG, "onStopJob");
        return false;
    }
}
