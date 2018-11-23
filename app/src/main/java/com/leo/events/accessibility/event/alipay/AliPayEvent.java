package com.leo.events.accessibility.event.alipay;

import android.content.Context;
import com.leo.events.accessibility.event.AccessibilityEventManaer;

/**
 * Created by spf on 2018/10/27.
 */
public class AliPayEvent {

    public static AliPayEvent aliPayEvent = new AliPayEvent();
    public static AliPayEvent get(){
        return aliPayEvent;
    }

    public void task(Context context){
//        PayWindow.get().show();
//        try {
//            Thread.sleep(500);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        ComponentName componentName = new ComponentName("com.eg.android.AlipayGphone","com.eg.android.AlipayGphone.AlipayLogin");
//        Intent intent = new Intent();
//        intent.setAction(Intent.ACTION_MAIN);
//        intent.addCategory(Intent.CATEGORY_LAUNCHER);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        intent.setComponent(componentName);
//        context.startActivity(intent);
        AccessibilityEventManaer.get().addEvent(new AccessibilityAlipayLaunchEvent());
        AccessibilityEventManaer.get().addEvent(new AccessibilityAlipayOneEvent());
        AccessibilityEventManaer.get().addEvent(new AccessibilityAlipayTwoEvent());
        AccessibilityEventManaer.get().addEvent(new AccessibilityAlipayThreeEvent(context));
        AccessibilityEventManaer.get().addEvent(new AccessibilityAlipayForEvent(context));
        AccessibilityEventManaer.get().addEvent(new AccessibilityAlipayFiveEvent());
    }

}
