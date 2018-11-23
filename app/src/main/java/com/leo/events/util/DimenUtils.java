package com.leo.events.util;

import android.util.DisplayMetrics;
import android.util.TypedValue;
import com.leo.events.MainApplication;

import java.lang.reflect.Field;


public class DimenUtils {
    private static DisplayMetrics mMetrics = MainApplication.getApp().getResources().getDisplayMetrics();

    private static final int DP_TO_PX = TypedValue.COMPLEX_UNIT_DIP;
    private static final int SP_TO_PX = TypedValue.COMPLEX_UNIT_SP;
    private static final int PX_TO_DP = TypedValue.COMPLEX_UNIT_MM + 1;
    private static final int PX_TO_SP = TypedValue.COMPLEX_UNIT_MM + 2;

    // -- dimens convert

    private static float applyDimension(int unit, float value, DisplayMetrics metrics) {
        switch (unit) {
            case DP_TO_PX:
            case SP_TO_PX:
                return TypedValue.applyDimension(unit, value, metrics);
            case PX_TO_DP:
                return value / metrics.density;
            case PX_TO_SP:
                return value / metrics.scaledDensity;
        }
        return 0;
    }

    public static int dp2px(float value) {
        return (int) applyDimension(DP_TO_PX, value, mMetrics);
    }

    public static int px2sp(float value) {
        return (int) applyDimension(PX_TO_SP, value, mMetrics);
    }

    public static int getStatusBarHeight2() {
        int statusBarHeight = 0;
        try {
            Class<?> cl = Class.forName("com.android.internal.R$dimen");
            Object obj = cl.newInstance();
            Field field = cl.getField("status_bar_height");

            int x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = MainApplication.getApp().getResources()
                    .getDimensionPixelSize(x);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusBarHeight;
    }

    public static int getScreenWidth() {
        if (!isInited()) {
            return 720;
        }
        return mMetrics.widthPixels;
    }

    public static int getScreenHeight() {
        if (!isInited()) {
            return 1280;
        }
        return mMetrics.heightPixels;
    }

    public static int getWindowWidth() {
        if(!isInited()){
            return 1280;
        }
        return mMetrics.widthPixels;
    }

    public static int getWindowHeight() {
        if(!isInited()){
            return 720;
        }
        return mMetrics.heightPixels;
    }

    public static boolean isInited() {
        return (null != mMetrics);
    }

}
