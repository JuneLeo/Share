package com.leo.events.accessibility.event.alipay;

import android.content.Context;
import android.view.accessibility.AccessibilityNodeInfo;

import com.leo.events.PayWindow;
import com.leo.events.accessibility.AccessibilityOperator;
import com.leo.events.accessibility.event.AccessibilityEvent;
import com.leo.events.accessibility.event.AccessibilityEventManaer;
import com.leo.events.accessibility.utils.AccessibilityLog;

import java.util.List;

/**
 * Created by spf on 2018/10/27.
 */
public class AccessibilityAlipayPayEvent extends AccessibilityEvent {
    public Context context;

    public AccessibilityAlipayPayEvent(Context context) {
        this.context = context;
    }

    @Override
    public boolean operator() {
//        "com.alipay.mobile.framework.app.ui.DialogHelper$APGenericProgressDialog";
        android.view.accessibility.AccessibilityEvent accessibilityEvent = AccessibilityOperator.getInstance().getAccessibilityEvent();
        if (accessibilityEvent != null && accessibilityEvent.getClassName().toString().contains("MiniProgressDialog")) {
            PayWindow.get().show();
            AccessibilityEventManaer.get().putEvent(new AccessiblilityAlipayBackEvent());
            AccessibilityOperator.getInstance().clickBackKey();
            return true;
        }
        return false;
    }

    @Override
    protected void dispose() {

    }

    public List<AccessibilityNodeInfo> findNodesByText(String text) {
        AccessibilityNodeInfo nodeInfo = AccessibilityOperator.getInstance().getRootNodeInfo();
        if (nodeInfo != null) {
            return nodeInfo.findAccessibilityNodeInfosByText(text);
        }
        return null;
    }

    public boolean clickByText(String text) {
        return performClick(findNodesByText(text));
    }

    private boolean performClick(List<AccessibilityNodeInfo> nodeInfos) {
        if (nodeInfos != null && !nodeInfos.isEmpty()) {
            AccessibilityNodeInfo node;
            for (int i = 0; i < nodeInfos.size(); i++) {
                node = nodeInfos.get(i);
                // 获得点击View的类型
                AccessibilityLog.printLog("View类型：" + node.getClassName());
                // 进行模拟点击
                if (node.getParent().isEnabled()) {
                    return node.getParent().performAction(AccessibilityNodeInfo.ACTION_CLICK);
                }
            }
        }
        return false;
    }
}
