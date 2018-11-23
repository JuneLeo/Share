package com.leo.events.accessibility.event.alipay;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.accessibility.AccessibilityNodeInfo;

import com.leo.events.accessibility.AccessibilityOperator;
import com.leo.events.accessibility.event.AccessibilityEvent;

/**
 * Created by spf on 2018/10/24.
 */
public class AccessibilityAlipayThreeEvent extends AccessibilityEvent {



    ClipboardManager clip;
    public AccessibilityAlipayThreeEvent(Context context) {
        clip = (ClipboardManager)context.getSystemService(Context.CLIPBOARD_SERVICE);
    }

    public AccessibilityAlipayThreeEvent() {
    }

    @Override
    public boolean operator() {
        ClipData clipData = ClipData.newPlainText("text", "15035341198");
        clip.setPrimaryClip(clipData);
        AccessibilityOperator operator = AccessibilityOperator.getInstance();
        if (operator.clipPhone("")){
            boolean isPass = operator.clickByText("下一步");
            AccessibilityNodeInfo rootNodeInfo = AccessibilityOperator.getInstance().findFocusNode();
            rootNodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLEAR_FOCUS);
            return isPass;
        }
        return false;
    }
}
