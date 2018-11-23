package com.leo.events.accessibility.event.alipay;

import android.view.accessibility.AccessibilityNodeInfo;

import com.leo.events.accessibility.AccessibilityOperator;
import com.leo.events.accessibility.event.AccessibilityEvent;
import com.leo.events.accessibility.utils.AccessibilityLog;

import java.util.List;

/**
 * Created by spf on 2018/10/27.
 */
public class AccessibilityAlipayLaunchEvent extends AccessibilityEvent {

    public AccessibilityAlipayLaunchEvent() {
//        this.context = context;
    }

    @Override
    public boolean operator() {
        List<AccessibilityNodeInfo> lists = findNodesByText("首页");
        if (lists == null || lists.isEmpty()) {
            return false;
        }
        return performClick(lists);
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
