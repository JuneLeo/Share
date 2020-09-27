package com.leo.events;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Build;
import android.provider.Settings;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by spf on 2018/10/23.
 */

public class ShareWindow {

    private static ShareWindow window = new ShareWindow();
    private final View mBgView;
    public boolean isAttach;

    private WindowManager mWindowManager;
    private WindowManager.LayoutParams mMwLayoutParams;

    public static ShareWindow get() {
        return window;
    }

    private ShareWindow() {
        mWindowManager = (WindowManager) MainApplication.getApp().getSystemService(Context.WINDOW_SERVICE);
        mMwLayoutParams = new WindowManager.LayoutParams();
        mMwLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL |
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
        mBgView = LayoutInflater.from(MainApplication.getApp()).inflate(R.layout.window_share, null);
        type2(mMwLayoutParams);
        init();
    }

    public static void toStartWindow() {
        try {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
            MainApplication.getApp().startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    void init() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Point p = new Point();
            mWindowManager.getDefaultDisplay().getRealSize(p);
            mMwLayoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
            mMwLayoutParams.height = (p.y > p.x ? p.y : p.x);
            mMwLayoutParams.flags |= WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS |
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION;
            mMwLayoutParams.gravity = Gravity.CENTER;
        } else {
            mMwLayoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
            mMwLayoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        }
    }

    boolean type2(WindowManager.LayoutParams layoutParams) {
        //android targetSdkVersion >= 26 && sdk >=25   这种情况无法使用TYPE_TOAST
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {  //sdk >=25
            if (Settings.canDrawOverlays(MainApplication.getApp())) { // 有悬浮权限
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
                } else {
                    layoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
                }
                return true;
            } else {  //没有悬浮权限
                if (MainApplication.getApp().getApplicationInfo().targetSdkVersion >= Build.VERSION_CODES.O) {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {  //8.0
                        layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY - 1;
                        return true;
                    } else {
                        //7.1
                        return false;
                    }

                }
            }
        }
        if (Build.VERSION.SDK_INT >= 23) {
            if (!Settings.canDrawOverlays(MainApplication.getApp())) {
                layoutParams.type = WindowManager.LayoutParams.TYPE_TOAST;
                return true;
            }
        }
        layoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        return true;
    }


    public void show() {
        if (isAttach) {
            return;
        }
        isAttach = true;
        try {
            mWindowManager.addView(mBgView, mMwLayoutParams);
        } catch (Exception e) {
            //
            e.printStackTrace();
        }
    }

    public void dismiss() {
        if (!isAttach) {
            return;
        }
        isAttach = false;
        try {
            mWindowManager.removeView(mBgView);
        } catch (Exception e) {
            //
            e.printStackTrace();
        }
    }

    public static boolean isShowWindow() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return Settings.canDrawOverlays(MainApplication.getApp());
        }
        return true;
    }

}
