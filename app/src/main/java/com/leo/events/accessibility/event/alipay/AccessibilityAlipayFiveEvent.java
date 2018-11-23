package com.leo.events.accessibility.event.alipay;

import android.view.accessibility.AccessibilityNodeInfo;

import com.leo.events.PayWindow;
import com.leo.events.accessibility.AccessibilityOperator;
import com.leo.events.accessibility.event.AccessibilityEvent;
import com.leo.events.accessibility.utils.AccessibilityLog;

import java.util.List;

/**
 * Created by spf on 2018/10/24.
 */
public class AccessibilityAlipayFiveEvent extends AccessibilityEvent {
    private String phone;

    public AccessibilityAlipayFiveEvent(int type) {
        super(type);
    }

    public AccessibilityEvent setText(String phone){
        this.phone = phone;
        return this;
    }

    public AccessibilityAlipayFiveEvent() {
    }

    @Override
    public boolean operator() {
        clickByText("立即付款");
//        PayWindow.get().dismiss();
        return true;

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

    @Override
    protected void dispose() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        PayWindow.get().dismiss();
    }
}
