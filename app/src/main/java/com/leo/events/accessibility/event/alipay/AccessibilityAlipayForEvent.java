package com.leo.events.accessibility.event.alipay;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

import com.leo.events.accessibility.AccessibilityOperator;
import com.leo.events.accessibility.event.AccessibilityEvent;

/**
 * Created by spf on 2018/10/25.
 */
public class AccessibilityAlipayForEvent extends AccessibilityEvent {
    ClipboardManager clip;
    public AccessibilityAlipayForEvent(Context context) {
        clip = (ClipboardManager)context.getSystemService(Context.CLIPBOARD_SERVICE);
    }

    @Override
    public boolean operator() {
        ClipData clipData = ClipData.newPlainText("text", "0.01");
        clip.setPrimaryClip(clipData);
        AccessibilityOperator operator = AccessibilityOperator.getInstance();
        if (operator.clipPhone("")){
            return operator.clickByText("确认转账");
        }
        return false;
    }
}
