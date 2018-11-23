package com.leo.events.accessibility.event;

import com.leo.events.accessibility.AccessibilityOperator;

/**
 * Created by spf on 2018/10/23.
 */

public class AccessibilityShareImageEvent extends AccessibilityEvent {

    private String txt = "";

    public AccessibilityShareImageEvent(int type) {
        super(type);
    }

    @Override
    public boolean operator() {
        if (AccessibilityOperator.getInstance().clipText()){
            return AccessibilityOperator.getInstance().clickByText("发表");
        }
        return false;
    }
}
