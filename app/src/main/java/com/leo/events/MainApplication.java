package com.leo.events;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.leo.events.abtest.ABTest;
import com.leo.events.accessibility.event.AccessibilityEventManaer;
import com.leo.events.shareutil.ShareConfig;
import com.leo.events.shareutil.ShareManager;

/**
 * Created by spf on 2018/10/22.
 */

public class MainApplication extends MultiDexApplication {
    @SuppressLint("StaticFieldLeak")
    private static Context app;

    @Override
    public void onCreate() {
        super.onCreate();
        // init
        ShareConfig config = ShareConfig.instance()
                .wxId(BuildConfig.WX_APP_ID);
        ShareManager.init(config);

        AccessibilityEventManaer.get().init();

        ABTest.initConfig();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        app = base;
        MultiDex.install(this);
    }


    public static Context getApp(){
        return app;
    }
}
