package com.leo.events.accessibility;

import android.accessibilityservice.AccessibilityService;
import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.leo.events.accessibility.event.AccessibilityEventManaer;
import com.leo.events.accessibility.utils.AccessibilityLog;

import java.util.ArrayList;
import java.util.List;

import static android.view.accessibility.AccessibilityNodeInfo.FOCUS_INPUT;

/**
 * Created by popfisher on 2017/7/11.
 */

@TargetApi(16)
public class AccessibilityOperator {

    private Context mContext;
    private static AccessibilityOperator mInstance = new AccessibilityOperator();
    private AccessibilityEvent mAccessibilityEvent;
    private AccessibilityService mAccessibilityService;

    private AccessibilityOperator() {
    }

    public static AccessibilityOperator getInstance() {
        return mInstance;
    }

    public AccessibilityEvent getAccessibilityEvent() {
        return mAccessibilityEvent;
    }

    public AccessibilityService getAccessibilityService() {
        return mAccessibilityService;
    }

    public void init(Context context) {
        mContext = context;
    }

    public void updateEvent(AccessibilityService service, AccessibilityEvent event) {
        if (service != null && mAccessibilityService == null) {
            mAccessibilityService = service;
        }
        if (event != null) {
            mAccessibilityEvent = event;
        }
        AccessibilityEventManaer.get().excute();
    }

    public boolean isServiceRunning() {
        ActivityManager am = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> services = am.getRunningServices(Short.MAX_VALUE);
        for (ActivityManager.RunningServiceInfo info : services) {
            if (info.service.getClassName().equals(mContext.getPackageName() + ".AccessibilitySampleService")) {
                return true;
            }
        }
        return false;
    }

    public AccessibilityNodeInfo getRootNodeInfo() {
        try {
            AccessibilityEvent curEvent = mAccessibilityEvent;
            AccessibilityNodeInfo nodeInfo = null;
            if (Build.VERSION.SDK_INT >= 16) {
                // 建议使用getRootInActiveWindow，这样不依赖当前的事件类型
                if (mAccessibilityService != null) {
                    nodeInfo = mAccessibilityService.getRootInActiveWindow();
                    AccessibilityLog.printLog("nodeInfo: " + nodeInfo);
                }
                if (nodeInfo == null && curEvent != null) {
                    // 下面这个必须依赖当前的AccessibilityEvent
                    nodeInfo = curEvent.getSource();
                }
            } else {
                nodeInfo = curEvent.getSource();
            }
            return nodeInfo;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据Text搜索所有符合条件的节点, 模糊搜索方式
     */
    public List<AccessibilityNodeInfo> findNodesByText(String text) {
        AccessibilityNodeInfo nodeInfo = getRootNodeInfo();
        if (nodeInfo != null) {
            return nodeInfo.findAccessibilityNodeInfosByText(text);
        }
        return null;
    }

    /**
     * 根据View的ID搜索符合条件的节点,精确搜索方式;
     * 这个只适用于自己写的界面，因为ID可能重复
     * api要求18及以上
     *
     * @param viewId
     */
    public List<AccessibilityNodeInfo> findNodesById(String viewId) {
        AccessibilityNodeInfo nodeInfo = getRootNodeInfo();
        if (nodeInfo != null) {
            if (Build.VERSION.SDK_INT >= 18) {
                return nodeInfo.findAccessibilityNodeInfosByViewId(viewId);
            }
        }
        return null;
    }

    public boolean clickByText(String text) {
        return performClick(findNodesByText(text));
    }

    /**
     * 根据View的ID搜索符合条件的节点,精确搜索方式;
     * 这个只适用于自己写的界面，因为ID可能重复
     * api要求18及以上
     *
     * @param viewId
     * @return 是否点击成功
     */
    public boolean clickById(String viewId) {
        return performClick(findNodesById(viewId));
    }

    private boolean performClick(List<AccessibilityNodeInfo> nodeInfos) {
        if (nodeInfos != null && !nodeInfos.isEmpty()) {
            AccessibilityNodeInfo node;
            for (int i = 0; i < nodeInfos.size(); i++) {
                node = nodeInfos.get(i);
                // 获得点击View的类型
                AccessibilityLog.printLog("View类型：" + node.getClassName());
                // 进行模拟点击
                if (node.isEnabled()) {
                    return node.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                }
            }
        }
        return false;
    }

    /**
     * 后退
     *
     * @return
     */
    public boolean clickBackKey() {
        return performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK);
    }

    public boolean performGlobalAction(int action) {
        return mAccessibilityService.performGlobalAction(action);
    }


    public List<AccessibilityNodeInfo> findNodesEdittext(AccessibilityNodeInfo nodeInfo) {
        List<AccessibilityNodeInfo> list = new ArrayList<>();
        int nodeCount = nodeInfo.getChildCount();
        if (nodeCount > 0) {
            for (int i = 0; i < nodeCount; i++) {
                AccessibilityNodeInfo child = nodeInfo.getChild(i);
                if (child.getClassName().toString().contains("EditText")) {
                    list.add(child);
                }
                List<AccessibilityNodeInfo> nodesEdittext = findNodesEdittext(child);
                if (nodesEdittext != null && !nodesEdittext.isEmpty()) {
                    list.addAll(nodesEdittext);
                }
            }
        }
        return list;
    }


    /**
     * 找到焦点
     */
    public List<AccessibilityNodeInfo> findNodesByFocus() {
        AccessibilityNodeInfo nodeInfo = getRootNodeInfo();
        if (nodeInfo != null) {
            return findNodesEdittext(nodeInfo);
        }
        return null;
    }

    public boolean clipText() {
        return performText(findNodesByFocus());
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    private boolean performText(List<AccessibilityNodeInfo> nodes) {
//        if (node != null) {
//            // 获得点击View的类型
//            AccessibilityLog.printLog("View类型：" + node.getClassName());
//            // 模拟粘贴
//            if (node.performAction(AccessibilityNodeInfo.ACTION_PASTE)) {
//                return true;
//            }
//        }


        if (nodes != null && !nodes.isEmpty()) {
            for (AccessibilityNodeInfo node : nodes) {
                node.performAction(AccessibilityNodeInfo.ACTION_FOCUS);
                if (node.performAction(AccessibilityNodeInfo.ACTION_PASTE)) {
                    return true;
                }
            }
        }
        return false;
    }


    public AccessibilityNodeInfo findFocusNode() {
        AccessibilityNodeInfo nodeInfo = getRootNodeInfo();
        if (nodeInfo != null) {
            return nodeInfo.findFocus(FOCUS_INPUT);
        }
        ;
        return null;
    }


    public boolean clipPhone(String text) {
        AccessibilityNodeInfo focusNode = findFocusNode();
        if (focusNode == null) {
            return false;
        }
        //模拟粘贴
        if (focusNode.performAction(AccessibilityNodeInfo.ACTION_PASTE)) {
            return true;
        }
        return false;
    }


}
